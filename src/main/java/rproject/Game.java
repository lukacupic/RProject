package rproject;

import rproject.board.Board;
import rproject.board.BoardProvider;
import rproject.io.Input;
import rproject.io.Output;
import rproject.units.Unit;
import rproject.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

	private Board board;

	private List<Player> players = new ArrayList<>();

	private final Color colors[] = new Color[]{
			new Color (255,0,0),
			new Color (0,255,0),
			new Color (0,0,255),
			new Color (255,255,0),
			new Color (255,0,255),
			new Color (0,255,255),
	};

	public Game(String boardName, String[] playerNames) {
		board = new Board(boardName);
		BoardProvider.setBoard(board);

		createPlayers(new ArrayList<>(Arrays.asList(playerNames)));
	}

	public void start() {
		shuffle();
		runGame();
	}

	private void createPlayers(List<String> playerNames) {
		for (int i = 0; i < playerNames.size(); i++) {
			String name = playerNames.get(i);
			Color color = colors[i];
			players.add(new Player(name, color));
		}
	}

	private void runInitSpawnPhase(){
		Output.writeln("*** init phase, place your units! ***");
		for (Player player : players) {
			player.addGold(2);
			spawnPhase(player);
		}
	}

	private void runGame() {
		runInitSpawnPhase();
		while (numberOfPlayers() > 1)
			for (Player player : players)
				if (player.isAlive()) runPlayer(player);
		for (Player player : players)
			if (player.isAlive()) Output.writeln("congratz, " + player.getName() + " is winner!");
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
		if (numberOfPlayers() == 1) return;
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

	private static List<Unit> battle(Territory attTerritory, Territory defTerritory, List<Unit> attArmy) {
//		List < Unit > attArmy = new ArrayList<>();
		List<Unit> defArmy = defTerritory.getUnits();
/*		for (int i = 0; i < cntAttUnits; ++i) {
			Unit U = new Fighter();
			attArmy.add(U);
		}
		for (int i = 0; i < defTerritory.getUnits(); ++i) {
			Unit U = new Fighter();
			defArmy.add(U);
		}*/
		Collections.shuffle(attArmy);
		Collections.shuffle(defArmy);
		while (!attArmy.isEmpty() && !defArmy.isEmpty()) {
			for (Unit unit : defArmy) {
				if (attArmy.size() == 0) break;
				int targetIndex = Util.getRandInt(attArmy.size());
				if (unit.attack(attArmy.get(targetIndex))) attArmy.remove(targetIndex);
			}
			for (Unit unit : attArmy) {
				if (defArmy.size() == 0) break;
				int targetIndex = Util.getRandInt(defArmy.size());
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

	private boolean getYNAnswer(String message) {
		String response;
		do {
			Output.writeln(message + "? y/n");
			response = Input.readString();
		} while (!response.equals("y") && !response.equals("n"));
		return response.equals("y");
	}

	private List<Unit> getUnits(Territory T) {
		Output.writeln("list of available units:");
		List < Unit > allUnits = Unit.getAllUnits();
		for (Unit unit : allUnits) {
			if (T.getNumberOfUnits(unit.getName()) == 0) continue;
			Output.writeln(unit.getName() + " (dmg: " + unit.getDamage() + ", hp: " + unit.getHp()
					+ ", quantity: " + T.getNumberOfUnits(unit.getName()) + "x)");
		}
		List<Unit> selectedUnits = new ArrayList<>();
		boolean isAllSelected = true;
		for (Unit unit : allUnits) {
			if (T.getNumberOfUnits(unit.getName()) == 0) continue;
			Output.writeln("how many " + unit.getName() + "?");
			int cntSelectedUnits = Input.readInt();
			while (cntSelectedUnits > T.getNumberOfUnits(unit.getName()) || cntSelectedUnits < 0) {
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

	private Territory getTerritory(String message) {
		Output.writeln(message);
		String attTerritoryName = Input.readString();
		return BoardProvider.getBoard().getTerritory(attTerritoryName);
	}

	private boolean attackPhase(Player player) {
		boolean giveBonus = false;
		while (getYNAnswer("attack")) {
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Territory attTerritory = getTerritory("att from?");
			Territory defTerritory = getTerritory("att what?");
			if (!checkValidAttack(attTerritory, defTerritory, player)) continue;
			List<Unit> attUnits = getUnits(attTerritory);
			if (attUnits.size() == attTerritory.getCntUnits()){
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

	private void movePhase(Player player) {
		while (getYNAnswer("move units")) {
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Territory starting = getTerritory("move from?");
			Territory ending = getTerritory("move to?");
			if (!checkValidMoving(starting, ending, player)) continue;
			List<Unit> movingUnits = getUnits(starting);
			if (movingUnits.size() == starting.getCntUnits()){
				Output.writeln("you cant attack with all units");
				continue;
			}
			starting.moveUnits(ending, movingUnits);
		}
	}

	private int getSpawnCount(Player player) {
		return 3 + player.getCntBonus(); // todo
	}

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
/*		int cntSpawnUnits = Input.readInt();
		if (gold > cntSpawnUnits) return units;
		player.removeGold(cntSpawnUnits);
		for (int i = 0; i < cntSpawnUnits; ++i) {
			Unit U;
			if (Util.getRandInt(100) < 75) U = new Fighter();
			else U = new Knight();
			units.add(U);
		}*/
	}

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

	private void bonusPhase(Player player) {
		player.addBonus(1);
	}
}
