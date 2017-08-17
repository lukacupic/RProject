package rproject.gui.ui;

import rproject.engine.Game;
import rproject.engine.GameProvider;
import rproject.engine.IGameChangesListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PlayingPanel extends JPanel implements IGameChangesListener {

	private JPanel nextPhasePanel;
	private JPanel territoryTitlePanel;
	private JPanel territoryPanel;
	private JPanel playersPanel;
	private JLabel territoryTitle;
	private JLabel playersTitle;
	private JPanel mainPanel;
	private JLabel currentPlayer;

	private Game game;

	public PlayingPanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
	}

	private void createUIComponents() {
		// TODO: custom components
	}

	@Override
	public void gameChanged() {
		if (game == null) game = GameProvider.getGame();
		String player = game.getCurrentPlayer().getName();
		String phase = game.getCurrentPhase();
		currentPlayer.setText(player + " - " + phase);
	}
}
