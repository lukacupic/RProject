package rproject;

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
		players.get(i).spawnPhase();
		boolean getBonus = players.get(i).attackPhase();
		players.get(i).movePhase();
		if (getBonus) players.get(i).bonusPhase();
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
}
