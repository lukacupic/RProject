package rproject.gui.panels;

import rproject.Game;
import rproject.gui.BoardMap;
import rproject.gui.MainWindow;

import javax.swing.*;

public class BoardMapPanel extends JPanel {

	/**
	 * The unique identifier of this panel. Used to select
	 * currently active panel from the {@link MainWindow}.
	 */
	public static final String ID = "boardMapPanel";

	private BoardMap boardMap;

	/**
	 * The constructor.
	 */
	public BoardMapPanel() {
	}

	/**
	 * Initializes the main map and sets up the entire game.
	 * The calling thread awaits for the initialization to
	 * be completed before proceeding.
	 *
	 * @param boardName   the name of the board to create
	 * @param playerNames the names of the players
	 */
	public void init(String boardName, String[] playerNames) {
		boardMap = new BoardMap(boardName);
		add(boardMap);

		new Thread(() -> {
			boardMap.init();

			try {
				MainWindow.latch.await();
			} catch (InterruptedException ignorable) {
			}

			Game game = new Game(boardName, playerNames);
			game.start();
		}).start();
	}
}
