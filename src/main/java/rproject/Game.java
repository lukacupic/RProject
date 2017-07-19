package rproject;

import rproject.io.Input;
import rproject.io.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

	private Board board;

	private List<Player> players = new ArrayList<>();

	private static Random rand = new Random();

	public Game(String boardName, String[] playerNames) {
		board = new Board(boardName);
		BoardProvider.setBoard(board);

		createPlayers(new ArrayList<>(Arrays.asList(playerNames)));

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
			for (int i = 0; i < numberOfPlayers(); ++i)
				if (players.get(i).isAlive()) runPlayer(i);
		}
	}

	private int numberOfPlayers() {
		int n = 0;
		for (Player player : players) {
			if (player.isAlive()) n++;
		}
		return n;
	}

	private void runPlayer(int i) {
		Output.write("***** ");
		Output.write(players.get(i).getName());
		Output.writeln(" ******");
		spawnPhase(players.get(i));
		boolean getBonus = attackPhase(players.get(i));
		movePhase(players.get(i));
		if (getBonus) bonusPhase(players.get(i));
	}

	private void shuffle() {
		List<Territory> freeTerritories = new ArrayList<>(board.getTerritories());

		for (int playerIndex = 0; freeTerritories.size() > 0; playerIndex++) {
			playerIndex %= players.size();
			Player player = players.get(playerIndex);

			int randIndex = Math.abs(rand.nextInt() % freeTerritories.size());
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

	private boolean attack(Territory attTerritory, Territory defTerritory, int cntAttUnits){
		// todo: dying
		return cntAttUnits > defTerritory.getUnits();
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
			if (checkValidAttack(attTerritory, defTerritory, cntAttUnits)) {
				attTerritory.addUnits(-cntAttUnits);
				if (attack(attTerritory, defTerritory, cntAttUnits)) {
					getBonus = true;
					defTerritory.changeOwner(player);
					defTerritory.setUnits(cntAttUnits);
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

	private boolean checkValidMoving(Territory startingTerritory, Territory endingTerritory, int cntMovingUnits){
		if (startingTerritory == null){
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (endingTerritory == null){
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!startingTerritory.getOwner().getName().equals(this.getName())){
			Output.writeln("starting territory doesn't belong to you");
			return false;
		}
		if (!endingTerritory.getOwner().getName().equals(this.getName())){
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
			if (checkValidMoving(starting, ending, cntMovingUnits)){
				starting.moveUnits(ending, cntMovingUnits);
			}
			do {
				Output.writeln("move again? y/n");
				response = Input.readString();
			} while(!response.equals("n") && !response.equals("y"));
		}
	}

	private int getSpawnCount(){
		return 3; // todo
	}

	private boolean checkValidSpawning(Territory spawn, int cntSpawnUnits){
		if (spawn == null){
			Output.writeln("invalid name of the territory");
			return false;
		}
		if (!spawn.getOwner().getName().equals(this.getName())){
			Output.writeln("territory doesn't belong to you");
			return false;
		}
		if (cntSpawnUnits > cntExtraUnits) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	public void spawnPhase(Player player){
		cntExtraUnits += getSpawnCount();
		Output.writeln("spawn units? y/n");
		String response = Input.readString();
		Board board = BoardProvider.getBoard();
		board.getMatrix().drawMatrixCUI();
		while(response.equals("y")){
			Output.writeln("number of available units: " + cntExtraUnits);
			Output.writeln("spawn n units on territory a, write using format: a n");
			String spawnTerritoryName = Input.readString();
			Territory spawnTerritory = board.getTerritory(spawnTerritoryName);
			int cntSpawningUnits = Input.readInt();
			if (checkValidSpawning(spawnTerritory, cntSpawningUnits)){
				spawnTerritory.addUnits(cntSpawningUnits);
				cntExtraUnits -= cntSpawningUnits;
			}
			do {
				Output.writeln("spawn again? y/n");
				response = Input.readString();
			} while(!response.equals("y") && !response.equals("n"));
		}
	}

	public void bonusPhase(Player player){
		cntBonus++;
	}
}
