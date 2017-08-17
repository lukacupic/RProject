package rproject.gui.panels;

import rproject.gui.MainWindow;
import rproject.gui.map.BoardMap;
import rproject.gui.ui.BoardMapPanel;
import rproject.gui.ui.PlayingPanel;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MapPanel extends JPanel {

	private BoardMapPanel boardMapPanel = new BoardMapPanel();

	private PlayingPanel playingPanel = new PlayingPanel();

	/**
	 * The unique identifier of this panel. Used to select
	 * currently active panel from the {@link MainWindow}.
	 */
	public static final String ID = "mapPanel";

	public MapPanel() {
		setLayout(new BorderLayout());

		add(boardMapPanel, BorderLayout.CENTER);
		add(playingPanel, BorderLayout.LINE_END);
	}

	public BoardMapPanel getBoardMapPanel() {
		return boardMapPanel;
	}

	public PlayingPanel getPlayingPanel() {
		return playingPanel;
	}
}
