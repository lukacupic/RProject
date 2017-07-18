package rproject;

import java.util.List;

public class Game {

	private List<Player> players;

	private void runGame() {
		while (numberOfPlayers() > 1)
			for (int i = 0; i < numberOfPlayers(); ++i)
				if (players.get(i).isAlive()) runPlayer(i);
	}

	private int numberOfPlayers() {
		int n = 0;
		for (Player player : players) {
			if (player.isAlive()) n++;
		}
		return n;
	}

	private void runPlayer(int i) {
		/*
		players.get(i).getRessPhase();
		boolean getBonus = players.get(i).attackPhase();
		players.get(i).movePhase();
		if (getBonus) players.get(i).getBonus();
		*/
	}

	public Game(Board board, List<String> playerNames) {

	}
}
