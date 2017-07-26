package rproject.gui.panels;

import rproject.engine.Game;
import rproject.gui.BoardMap;
import rproject.gui.GUIAccess;
import rproject.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

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
		setLayout(new BorderLayout());
	}

	/**
	 * Initializes the main map and sets up the entire engine.
	 * The calling thread awaits for the initialization to
	 * be completed before proceeding.
	 *
	 * @param boardName   the name of the board to create
	 * @param playerNames the names of the players
	 */
	public void init(String boardName, String[] playerNames) {
		boardMap = new BoardMap(boardName);
		add(boardMap, BorderLayout.CENTER);

		new Thread(() -> {
			boardMap.init();

			try {
				GUIAccess.LATCH.await();
			} catch (InterruptedException ignorable) {
			}

			Game game = new Game(boardName, playerNames);
			game.start();
		}).start();
	}
}
