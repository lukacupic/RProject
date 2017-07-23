package rproject.gui.panels;

import rproject.gui.BoardMap;

import javax.swing.*;

public class BoardMapPanel extends JPanel {

	public static final String ID = "boardMapPanel";

	private BoardMap boardMap = new BoardMap();

	public BoardMapPanel() {
		boardMap.init();

		add(boardMap);
	}
}
