package rproject;

import rproject.board.Board;
import rproject.board.BoardProvider;
import rproject.io.Input;
import rproject.io.Output;
import rproject.units.Fighter;
import rproject.units.Knight;
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
	private boolean checkValidAttack(Territory attTerritory, Territory defTerritory, Player player){
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
		return true;
	}

	private static List < Unit > battle(Territory attTerritory, Territory defTerritory, List<Unit> attArmy){
//		List < Unit > attArmy = new ArrayList<>();
		List < Unit > defArmy = defTerritory.getUnits();
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
		while(!attArmy.isEmpty() && !defArmy.isEmpty()){
			for (Unit unit : defArmy){
				if (attArmy.size() == 0) break;
				int targetIndex = Util.getRandInt(attArmy.size());
				if (unit.attack(attArmy.get(targetIndex))) attArmy.remove(targetIndex);
			}
			for (Unit unit : attArmy){
				if (defArmy.size() == 0) break;
				int targetIndex = Util.getRandInt(defArmy.size());
				if (unit.attack(defArmy.get(targetIndex))) defArmy.remove(targetIndex);
			}
		}
		Output.writeln("att army size: " + attArmy.size());
		Output.writeln("def army size: " + defArmy.size());
		defTerritory.setUnits(defArmy);
		return attArmy;
	}

	private boolean getYNAnswer(String message){
		String response;
		do {
			Output.writeln(message + "? y/n");
			response = Input.readString();
		} while(!response.equals("y") && !response.equals("n"));
		return response.equals("y");
	}

	private List < Unit > getUnits(Territory T){
		Output.writeln("list of available units:");
		List < Unit > Units = T.getUnits();
		List < Unit > availUnits = new ArrayList<>();
		for (Unit unit : Units){
			Output.writeln("dmg: " + unit.getDamage() + ", hp: " + unit.getHp());
			if (getYNAnswer("add"))
				availUnits.add(unit);
		}
		return availUnits;
	}

	private Territory getTerritory(String message){
		Output.writeln(message);
		String attTerritoryName = Input.readString();
		return BoardProvider.getBoard().getTerritory(attTerritoryName);
	}

	private boolean attackPhase(Player player) {
		boolean getBonus = false;
		while(getYNAnswer("attack")){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Territory attTerritory = getTerritory("att from?");
			Territory defTerritory = getTerritory("att what?");
			if (!checkValidAttack(attTerritory, defTerritory, player)) continue;
			List < Unit > attUnits = getUnits(attTerritory);
			attTerritory.removeUnits(attUnits);
			List < Unit > survivors = battle(attTerritory, defTerritory, attUnits);
			if (!survivors.isEmpty()){
				getBonus = true;
				defTerritory.changeOwner(player);
				defTerritory.setUnits(survivors);
				Output.writeln("attacker wins");
			}
			else{
				Output.writeln("defender wins");
			}
		}
		return getBonus;
	}

	private boolean checkValidMoving(Territory startingTerritory, Territory endingTerritory, Player player){
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
		return true;
	}

	private void movePhase(Player player){
		while(getYNAnswer("move units")){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Output.writeln("move n units from a to b, write using format: a b n");
			Territory starting = getTerritory("move from?");
			Territory ending = getTerritory("move to?");
			if (!checkValidMoving(starting, ending, player)) continue;
			List < Unit > movingUnits = getUnits(starting);
			starting.moveUnits(ending, movingUnits);
		}
	}

	private int getSpawnCount(Player player){
		return 3 + player.getCntBonus(); // todo
	}

	private boolean checkValidSpawning(Territory spawn, Player player){
		if (spawn == null){
			Output.writeln("invalid name of the territory");
			return false;
		}
		if (!spawn.getOwner().getName().equals(player.getName())){
			Output.writeln("territory doesn't belong to you");
			return false;
		}
		return true;
	}

	private List < Unit > getSpawnUnits(Player player){
		List < Unit > units = new ArrayList<>();
		int money = player.getCntExtraUnits();
		Output.writeln("max units: " + money);
		int cntSpawnUnits = Input.readInt();
		if (money > cntSpawnUnits) return units;
		player.addExtraUnits(-cntSpawnUnits);
		for (int i = 0; i < cntSpawnUnits; ++i){
			Unit U;
			if (Util.getRandInt(100)<75) U = new Fighter();
			else U = new Knight();
			units.add(U);
		}
		return units;
	}

	private void spawnPhase(Player player){
		player.addExtraUnits(getSpawnCount(player));
		Board board = BoardProvider.getBoard();
		board.getMatrix().drawMatrixCUI();
		while(getYNAnswer("spawn units")){
			Output.writeln("number of available money: " + player.getCntExtraUnits());
			Output.writeln("spawn n units on territory a, write using format: a n");
			Territory spawnTerritoryName = getTerritory("spawn where?");
			if (!checkValidSpawning(spawnTerritoryName, player)) continue;
			List < Unit > spawnUnits = getSpawnUnits(player);
			spawnTerritoryName.addUnits(spawnUnits);
		}
	}

	private void bonusPhase(Player player){
		player.addBonus(1);
	}
}
