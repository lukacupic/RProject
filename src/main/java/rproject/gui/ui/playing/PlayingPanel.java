package rproject.gui.ui.playing;

import rproject.engine.Game;
import rproject.engine.GameProvider;
import rproject.engine.IGameChangesListener;
import rproject.engine.Territory;
import rproject.gui.CGBridge;
import rproject.utils.GUIUtil;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Map;

public class PlayingPanel extends JPanel implements IGameChangesListener {

	private final JLabel noTerritory = new JLabel();

	private JPanel mainPanel;

	private JLabel currentPlayerAndPhase;

	private JLabel territoryTitle;

	private JPanel territoryPanel;

	private JLabel playersTitle;
	private JPanel playersPanel;

	private JPanel nextPhasePanel;
	private JPanel territoryInfoPanel;
	private JPanel noTerritoryPanel;

	private Game game;

	public PlayingPanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		territoryInfoPanel.setLayout(new SpringLayout());

		noTerritoryPanel.add(noTerritory);
		add(mainPanel, BorderLayout.CENTER);
	}

	private void createUIComponents() {
	}

	@Override
	public void gameChanged() {
		if (game == null) game = GameProvider.getGame();
		updateInfo();
	}

	private void updateInfo() {
		String player = game.getCurrentPlayer().getName();
		String phase = game.getCurrentPhase();
		currentPlayerAndPhase.setText(player + " - " + phase);
		noTerritory.setText("No Territory Selected");

		updateTerritoryInfo();
	}

	private void updateTerritoryInfo() {
		Territory selected = CGBridge.getSelectedTerritory();

		String cardName;
		if (selected != null) {
			territoryInfoPanel.removeAll();
			Map<String, String> pMap = GUIUtil.getTerritoryProperties(selected);
			JPanel pPanel = GUIUtil.createTablePanel(pMap);
			territoryInfoPanel.add(pPanel);
			cardName = "territoryInfoCard";
		} else {
			cardName = "noTerritoryCard";
		}
		((CardLayout) territoryPanel.getLayout()).show(territoryPanel, cardName);
	}
}
