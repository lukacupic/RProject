package rproject.engine;

import rproject.board.Board;
import rproject.board.BoardProvider;
import rproject.io.Input;
import rproject.io.Output;
import rproject.units.Unit;
import rproject.utils.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

	/**
	 * The board on which the game is played
	 */
	private Board board;

	/**
	 * The list of all players in this game
	 */
	private List<Player> players = new ArrayList<>();

	/**
	 * Colors of the players, if there are n players, first n colors are used
	 */
	private final Color colors[] = new Color[]{
			new Color (255,0,0),
			new Color (0,255,0),
			new Color (0,0,255),
			new Color (255,255,0),
			new Color (255,0,255),
			new Color (0,255,255),
	};

	/**
	 * Returns the list of all players
	 *
	 * @return the list of all players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Initializes the board
	 *
	 * @param 	boardName
	 * 			name of the board
	 *
	 * @param 	playerNames
	 *			List of names of players
	 */
	public Game(String boardName, String[] playerNames) {
		board = new Board(boardName);
		BoardProvider.setBoard(board);

		createPlayers(new ArrayList<>(Arrays.asList(playerNames)));
	}

	/**
	 * Assigns territories to players and starts the game
	 */
	public void start() {
		shuffle();
		runGame();
	}

	/**
	 * Adds players to the list of players, and assigns colors to them
	 *
	 * @param 	playerNames
	 * 			List of names of players
	 */
	private void createPlayers(List<String> playerNames) {
		for (int i = 0; i < playerNames.size(); i++) {
			String name = playerNames.get(i);
			Color color = colors[i];
			players.add(new Player(name, color));
		}
	}

	/**
	 * Runs spawn phase which is at the beginning of the game,
	 * every player is given few gold to place first few units.
	 */
	private void runInitSpawnPhase(){
		Output.writeln("*** init phase, place your units! ***");
		for (Player player : players) {
			player.addGold(2);
			spawnPhase(player);
		}
	}

	/**
	 * Runs the game while there are at least 2 players, and congratz the winner
	 */
	private void runGame() {
		runInitSpawnPhase();
		while (numberOfPlayers() > 1)
			for (Player player : players)
				if (player.isAlive()) runPlayer(player);
		for (Player player : players)
			if (player.isAlive()) Output.writeln("congratz, " + player.getName() + " is winner!");
	}

	/**
	 * Returns the number of players
	 *
	 * @return the number of players
	 */
	private int numberOfPlayers() {
		int n = 0;
		for (Player player : players) {
			if (player.isAlive()) n++;
		}
		return n;
	}

	/**
	 * Runs the turn for player, calls all phases
	 *
	 * @param 	player
	 * 			current player
	 */
	private void runPlayer(Player player) {
		Output.write("***** ");
		Output.write(player.getName());
		Output.writeln(" ******");
		spawnPhase(player);
		boolean getBonus = attackPhase(player);
		if (numberOfPlayers() == 1) return;
		movePhase(player);
		if (getBonus) bonusPhase(player);
	}

	/**
	 * Assign territories to players
	 */
	private void shuffle() {
		List<Territory> freeTerritories = new ArrayList<>(board.getTerritories());

		for (int playerIndex = 0; freeTerritories.size() > 0; playerIndex++) {
			playerIndex %= players.size();
			Player player = players.get(playerIndex);

			int randIndex = Util.getRandInt(freeTerritories.size());
			Territory t = freeTerritories.get(randIndex);

			player.addTerritory(t);
			freeTerritories.remove(t);
		}
	}

	/**
	 * Checks if attack is valid
	 *
	 * @param 	attTerritory
	 * 			attacking territory
	 *
	 * @param 	defTerritory
	 * 			defending territory
	 *
	 * @param 	player
	 * 			current player
	 *
	 * @return true if attack is valid, false if it's not
	 */
	private boolean checkValidAttack(Territory attTerritory, Territory defTerritory, Player player) {
		if (attTerritory == null) {
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (defTerritory == null) {
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!attTerritory.getOwner().getName().equals(player.getName())) {
			Output.writeln("you can attack only from your territories");
			return false;
		}
		Board board = BoardProvider.getBoard();
		if (!board.getMatrix().checkNeighbours(attTerritory.getIndex(), defTerritory.getIndex())) {
			Output.writeln("territories aren't connected");
			return false;
		}
		if (attTerritory.getOwner().getName().equals(defTerritory.getOwner().getName())) {
			Output.writeln("you cant attack yourself");
			return false;
		}
		return true;
	}

	/**
	 * Returns (weighted) random index of some unit from the army. Each unit has weighted
	 * coefficient (or simply "coef", and chance of being selected equals:
	 * chance = (coef of unit) / (sum of coefs of all units in army)
	 *
	 * for example, if army consist of units A and B, with weighted coefficients 300 and 100 respectively,
	 * chance of A being attacked equals 300 / (300 + 100) = 75%
	 * chance of B being attacked equals 100 / (300 + 100) = 25%
	 *
	 * @param 	army
	 * 			army from which weighted random index is requested
	 *
	 * @return weighted random index
	 */
	private static int getRandomIndex(List < Unit > army){
		int sumOfCoefs = 0;
		for (Unit unit : army) sumOfCoefs += unit.getTargetChanceCoef();
		int randInt = Util.getRandInt(sumOfCoefs);
		for (int i = 0; i < army.size(); i++) {
			Unit unit = army.get(i);
			randInt -= unit.getTargetChanceCoef();
			if (randInt < 0) return i;
		}
		return 0;
	}

	/**
	 * Simulates the battle, returns surviving army (empty if the attacker lost)
	 *
	 * @param 	attTerritory
	 * 			attacking territory
	 *
	 * @param 	defTerritory
	 * 			defending territory
	 *
	 * @param 	attArmy
	 * 			attacking army
	 *
	 * @return surviving army (empty if the attacker lost)
	 */
	private static List<Unit> battle(Territory attTerritory, Territory defTerritory, List<Unit> attArmy) {
		List<Unit> defArmy = defTerritory.getUnits();
		Collections.shuffle(attArmy);
		Collections.shuffle(defArmy);
		while (!attArmy.isEmpty() && !defArmy.isEmpty()) {
			for (Unit unit : defArmy) {
				if (attArmy.size() == 0) break;
				int targetIndex = getRandomIndex(attArmy);
				if (unit.attack(attArmy.get(targetIndex))) attArmy.remove(targetIndex);
			}
			for (Unit unit : attArmy) {
				if (defArmy.size() == 0) break;
				int targetIndex = getRandomIndex(defArmy);
				if (unit.attack(defArmy.get(targetIndex))) defArmy.remove(targetIndex);
			}
		}
		Output.writeln("att army size: " + attArmy.size());
		Output.writeln("def army size: " + defArmy.size());
		for (Unit unit : attArmy)
			unit.resetHp();
		for (Unit unit : defArmy)
			unit.resetHp();
		defTerritory.setUnits(defArmy);
		return attArmy;
	}

	/**
	 * reads until y/n answer is given, returns that answer
	 *
	 * @param 	message
	 * 			printed message
	 *
	 * @return true if y is given, false if n
	 */
	private boolean getYNAnswer(String message) {
		String response;
		do {
			Output.writeln(message + "? y/n");
			response = Input.readString();
		} while (!response.equals("y") && !response.equals("n"));
		return response.equals("y");
	}

	/**
	 * asks player to select units from given territory
	 *
	 * @param T
	 * 			territory for which the list of selected units is requested
	 *
	 * @return the list of the selected units
	 */
	private List<Unit> getUnits(Territory T) {
		Output.writeln("list of units:");
		List < Unit > allUnits = Unit.getAllUnits();
		for (Unit unit : allUnits) {
			if (T.countSpecificUnits(unit.getName()) == 0) continue;
			Output.writeln(unit.getName() + " (dmg: " + unit.getDamage() + ", hp: " + unit.getHp()
					+ ", quantity: " + T.countSpecificUnits(unit.getName()) + "x)");
		}
		List<Unit> selectedUnits = new ArrayList<>();
		boolean isAllSelected = true;
		for (Unit unit : allUnits) {
			if (T.countSpecificUnits(unit.getName()) == 0) continue;
			if (!unit.isMovable()) continue;
			Output.writeln("how many " + unit.getName() + "?");
			int cntSelectedUnits = Input.readInt();
			while (cntSelectedUnits > T.countSpecificUnits(unit.getName()) || cntSelectedUnits < 0) {
				Output.writeln("input number not valid");
				if (getYNAnswer("input again")) {
					Output.writeln("how many " + unit.getName() + "?");
					cntSelectedUnits = Input.readInt();
				} else
					cntSelectedUnits = 0;
			}
			for(int i = 0; i < cntSelectedUnits; ++i)
				selectedUnits.add(unit.clone());
		}
		return selectedUnits;
	}

	/**
	 * Asks player to select some territory
	 *
	 * @param 	message
	 * 			message which is printed
	 *
	 * @return selected territory
	 */
	private Territory getTerritory(String message) {
		Output.writeln(message);
		String attTerritoryName = Input.readString();
		return BoardProvider.getBoard().getTerritory(attTerritoryName);
	}

	/**
	 * simulates attack phase
	 *
	 * @param 	player
	 * 			current player
	 * @return
	 */
	private boolean attackPhase(Player player) {
		boolean giveBonus = false;
		while (getYNAnswer("attack")) {
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Territory attTerritory = getTerritory("att from?");
			Territory defTerritory = getTerritory("att what?");
			if (!checkValidAttack(attTerritory, defTerritory, player)) continue;
			List<Unit> attUnits = getUnits(attTerritory);
			if (attUnits.size() == attTerritory.countAllUnits()){
				Output.writeln("you cant attack with all units");
				continue;
			}
			attTerritory.removeUnits(attUnits);
			List<Unit> survivors = battle(attTerritory, defTerritory, attUnits);
			if (!survivors.isEmpty()) {
				giveBonus = true;
				defTerritory.changeOwner(player);
				defTerritory.setUnits(survivors);
				Output.writeln("attacker wins");
				if (numberOfPlayers() == 1) return true;
			} else {
				Output.writeln("defender wins");
			}
		}
		return giveBonus;
	}

	/**
	 * Checks if moving is valid
	 *
	 * @param 	startingTerritory
	 * 			starting territory
	 *
	 * @param 	endingTerritory
	 * 			ending territory
	 *
	 * @param 	player
	 * 			current player
	 *
	 * @return true if moving is valid, false if it's not
	 */
	private boolean checkValidMoving(Territory startingTerritory, Territory endingTerritory, Player player) {
		if (startingTerritory == null) {
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (endingTerritory == null) {
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!startingTerritory.getOwner().getName().equals(player.getName())) {
			Output.writeln("starting territory doesn't belong to you");
			return false;
		}
		if (!endingTerritory.getOwner().getName().equals(player.getName())) {
			Output.writeln("ending territory doesn't belong to you");
			return false;
		}
		return true;
	}
	/**
	 * simulates moving phase
	 *
	 * @param 	player
	 * 			current player
	 * @return
	 */

	private void movePhase(Player player) {
		while (getYNAnswer("move units")) {
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Territory starting = getTerritory("move from?");
			Territory ending = getTerritory("move to?");
			if (!checkValidMoving(starting, ending, player)) continue;
			List<Unit> movingUnits = getUnits(starting);
			if (movingUnits.size() == starting.countAllUnits()){
				Output.writeln("you cant attack with all units");
				continue;
			}
			starting.moveUnits(ending, movingUnits);
		}
	}

	/**
	 * Return how many gold should player get for spawning
	 *
	 * @param 	player
	 * 			current player
	 * @return how many gold should player get for spawning
	 */
	private int getSpawnCount(Player player) {
		return 3 + player.getCntBonus(); // todo
	}

	/**
	 * Checks if spawning is valid
	 *
	 * @param 	spawn
	 * 			spawning territory
	 *
	 * @param 	player
	 * 			current player
	 *
	 * @return true if spawning is valid, false if it's not
	 */
	private boolean checkValidSpawning(Territory spawn, Player player) {
		if (spawn == null) {
			Output.writeln("invalid name of the territory");
			return false;
		}
		if (!spawn.getOwner().getName().equals(player.getName())) {
			Output.writeln("territory doesn't belong to you");
			return false;
		}
		return true;
	}

	/**
	 * asks player to select spawning units
	 *
	 * @param player
	 * 			current player
	 *
	 * @return the list of the selected units
	 */
	private List<Unit> getSpawnUnits(Player player) {
		List<Unit> units = new ArrayList<>();
		Output.writeln("current gold: " + player.getGold());
		List < Unit > allUnits = Unit.getAllUnits();
		Output.writeln("all units:");
		for (Unit unit : allUnits) {
			Output.writeln(unit.getName() + " (dmg: " + unit.getDamage() + ", hp: " + unit.getHp()
					+ ", price: " + unit.getPrice() + ")");
		}
		for (Unit unit : allUnits) {
			Output.writeln("how many " + unit.getName() + "?");
			int cntSpawnUnits = Input.readInt();
			while(cntSpawnUnits * unit.getPrice() > player.getGold() || cntSpawnUnits < 0){
				Output.writeln("input number not valid");
				if(getYNAnswer("input again")) {
					Output.writeln("how many " + unit.getName() + "?");
					cntSpawnUnits = Input.readInt();
				}
				else
					cntSpawnUnits = 0;
			}
			player.removeGold(cntSpawnUnits * unit.getPrice());
			for(int i = 0; i < cntSpawnUnits; ++i)
				units.add(unit.clone());
		}
		return units;
	}

	/**
	 * simulates spawn phase
	 *
	 * @param 	player
	 * 			current player
	 * @return
	 */
	private void spawnPhase(Player player) {
		player.addGold(getSpawnCount(player));
		Board board = BoardProvider.getBoard();
		board.getMatrix().drawMatrixCUI();
		while (getYNAnswer("spawn units")) {
			Output.writeln("number of available gold: " + player.getGold());
			Territory spawnTerritoryName = getTerritory("spawn where?");
			if (!checkValidSpawning(spawnTerritoryName, player)) continue;
			List<Unit> spawnUnits = getSpawnUnits(player);
			spawnTerritoryName.addUnits(spawnUnits);
		}
	}

	/**
	 * gives the bonus to the player
	 *
	 * @param 	player
	 * 			current player
	 */
	private void bonusPhase(Player player) {
		player.addBonus(1);
	}
}
