package rproject;

import rproject.board.Board;
import rproject.board.BoardProvider;
import rproject.io.Input;
import rproject.io.Output;
import rproject.units.Fighter;
import rproject.units.Unit;
import rproject.util.Util;

import java.util.*;

public class Game {

	private Board board;

	private List<Player> players = new ArrayList<>();

	public Game(String boardName, String[] playerNames) {
		board = new Board(boardName);
		BoardProvider.setBoard(board);

		createPlayers(new ArrayList<>(Arrays.asList(playerNames)));

		shuffle();
		runGame();
	}

	public void start() {
		shuffle();
		runGame();
	}

	private void createPlayers(List<String> playerNames) {
		for (String name : playerNames) {
			players.add(new Player(name));
		}
	}

	private void runGame() {
		while (numberOfPlayers() > 1) {
			for(Player player : players)
				if (player.isAlive()) runPlayer(player);
		}
	}

	private int numberOfPlayers() {
		int n = 0;
		for (Player player : players) {
			if (player.isAlive()) n++;
		}
		return n;
	}

	private void runPlayer(Player player) {
		Output.write("***** ");
		Output.write(player.getName());
		Output.writeln(" ******");
		spawnPhase(player);
		boolean getBonus = attackPhase(player);
		movePhase(player);
		if (getBonus) bonusPhase(player);
	}

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
	private boolean checkValidAttack(Territory attTerritory, Territory defTerritory, int cntAttUnits, Player player){
		if (attTerritory == null){
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (defTerritory == null){
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!attTerritory.getOwner().getName().equals(player.getName())){
			Output.writeln("you can attack only from your territories");
			return false;
		}
		Board board = BoardProvider.getBoard();
		if (!board.getMatrix().checkNeighbours(attTerritory.getIndex(),defTerritory.getIndex())){
			Output.writeln("territories aren't connected");
			return false;
		}
		if (attTerritory.getOwner().getName().equals(defTerritory.getOwner().getName())){
			Output.writeln("you cant attack yourself");
			return false;
		}
		if (attTerritory.getUnits() <= cntAttUnits) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	public static List < Unit > battle(Territory attTerritory, Territory defTerritory, int cntAttUnits){
		List < Unit > attArmy = new ArrayList<>();
		List < Unit > defArmy = new ArrayList<>();
		for (int i = 0; i < cntAttUnits; ++i) {
			Unit U = new Fighter();
			attArmy.add(U);
		}
		for (int i = 0; i < defTerritory.getUnits(); ++i) {
			Unit U = new Fighter();
			defArmy.add(U);
		}
		Collections.shuffle(attArmy);
		Collections.shuffle(defArmy);
		while(!attArmy.isEmpty() && !defArmy.isEmpty()){
			for (Unit unit : defArmy){
				if (attArmy.size() == 0) break;
				int targetIndex = Util.getRandInt(attArmy.size());
				if (unit.attack(attArmy.get(targetIndex))) attArmy.remove(targetIndex);
			}
			for (Unit unit : attArmy){
				if (defArmy.size() == 0) break;
				int targetIndex = Util.getRandInt(defArmy.size());
				Unit other = defArmy.get(targetIndex);
				Output.write(unit.getDamage() + " " + unit.getHp() + " -> ");
				Output.writeln(other.getDamage() + " " + other.getHp());
				if (unit.attack(defArmy.get(targetIndex))) defArmy.remove(targetIndex);
				Output.write(unit.getDamage() + " " + unit.getHp() + " -> ");
				Output.writeln(other.getDamage() + " " + other.getHp());
			}
			Output.writeln("** att **");
			for (Unit unit: attArmy)
				Output.write(unit.getHp());
			Output.writeln("");
			Output.writeln("** def **");
			for (Unit unit: defArmy)
				Output.write(unit.getHp());
			Output.writeln("");
		}
		Output.writeln(attArmy.size());
		Output.writeln(defArmy.size());
		return attArmy;
	}

	public boolean attackPhase(Player player) {
		boolean getBonus = false;
		Output.writeln("attack? y/n");
		String response = Input.readString();
		while(response.equals("y")){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Output.writeln("attack from a to b using n units, write using format: a b n");
			String attTerritoryName = Input.readString();
			Territory attTerritory = board.getTerritory(attTerritoryName);
			String defTerritoryName = Input.readString();
			Territory defTerritory = board.getTerritory(defTerritoryName);
			int cntAttUnits = Input.readInt();
			if (checkValidAttack(attTerritory, defTerritory, cntAttUnits, player)) {
				attTerritory.addUnits(-cntAttUnits);
				List < Unit > survivors = battle(attTerritory, defTerritory, cntAttUnits);
				if (!survivors.isEmpty()){
					getBonus = true;
					defTerritory.changeOwner(player);
					defTerritory.setUnits(survivors.size());
					Output.writeln("attacker wins");
				}
				else{
					Output.writeln("defender wins");
				}
			}
			do {
				Output.writeln("attack again? y/n");
				response = Input.readString();
			} while(!response.equals("y") && !response.equals("n"));
		}
		return getBonus;
	}

	private boolean checkValidMoving(Territory startingTerritory, Territory endingTerritory, int cntMovingUnits, Player player){
		if (startingTerritory == null){
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (endingTerritory == null){
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!startingTerritory.getOwner().getName().equals(player.getName())){
			Output.writeln("starting territory doesn't belong to you");
			return false;
		}
		if (!endingTerritory.getOwner().getName().equals(player.getName())){
			Output.writeln("ending territory doesn't belong to you");
			return false;
		}
		if (startingTerritory.getUnits() <= cntMovingUnits) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	public void movePhase(Player player){
		Output.writeln("move units? y/n");
		String response = Input.readString();
		while(response.equals("y")){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Output.writeln("move n units from a to b, write using format: a b n");
			String startingTerritoryName = Input.readString();
			Territory starting = board.getTerritory(startingTerritoryName);
			String endingTerritoryName = Input.readString();
			Territory ending = board.getTerritory(endingTerritoryName);
			int cntMovingUnits = Input.readInt();
			if (checkValidMoving(starting, ending, cntMovingUnits, player)){
				starting.moveUnits(ending, cntMovingUnits);
			}
			do {
				Output.writeln("move again? y/n");
				response = Input.readString();
			} while(!response.equals("n") && !response.equals("y"));
		}
	}

	private int getSpawnCount(Player player){
		return 3 + player.getCntBonus(); // todo
	}

	private boolean checkValidSpawning(Territory spawn, int cntSpawnUnits, Player player){
		if (spawn == null){
			Output.writeln("invalid name of the territory");
			return false;
		}
		if (!spawn.getOwner().getName().equals(player.getName())){
			Output.writeln("territory doesn't belong to you");
			return false;
		}
		if (cntSpawnUnits > player.getCntExtraUnits()) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	public void spawnPhase(Player player){
		player.addExtraUnits(getSpawnCount(player));
		Output.writeln("spawn units? y/n");
		String response = Input.readString();
		Board board = BoardProvider.getBoard();
		board.getMatrix().drawMatrixCUI();
		while(response.equals("y")){
			Output.writeln("number of available units: " + player.getCntExtraUnits());
			Output.writeln("spawn n units on territory a, write using format: a n");
			String spawnTerritoryName = Input.readString();
			Territory spawnTerritory = board.getTerritory(spawnTerritoryName);
			int cntSpawningUnits = Input.readInt();
			if (checkValidSpawning(spawnTerritory, cntSpawningUnits, player)){
				spawnTerritory.addUnits(cntSpawningUnits);
				player.addExtraUnits(-cntSpawningUnits);
			}
			do {
				Output.writeln("spawn again? y/n");
				response = Input.readString();
			} while(!response.equals("y") && !response.equals("n"));
		}
	}

	public void bonusPhase(Player player){
		player.addBonus(1);
	}
}
