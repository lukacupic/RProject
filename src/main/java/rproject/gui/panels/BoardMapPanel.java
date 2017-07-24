package rproject.gui.panels;

import rproject.Game;
import rproject.gui.BoardMap;
import rproject.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class BoardMapPanel extends JPanel {

	/**
	 * The unique identifier of this panel. Used to select
	 * currently active panel from the {@link MainWindow}.
	 */
	public static final String ID = "boardMapPanel";

	private BoardMap boardMap = new BoardMap();

	public BoardMapPanel() {
		add(boardMap);
		initSettings();
	}

	private void initSettings() {
		new Thread(() -> {
			boardMap.init();

			Game game = new Game("map1", new String[]{"A", "B", "C"});
			// game.start();
		}).start();
	}
}
