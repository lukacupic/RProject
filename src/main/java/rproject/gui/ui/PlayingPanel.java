package rproject.gui.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PlayingPanel extends JPanel {

	private JPanel currentPlayerPanel;
	private JPanel nextPhasePanel;
	private JPanel territoryTitlePanel;
	private JPanel territoryPanel;
	private JPanel playersTitlePanel;
	private JPanel playersPanel;
	private JLabel territoryTitle;
	private JLabel playersTitle;
	private JButton button1;
	private JButton button2;
	private JPanel mainPanel;

	public PlayingPanel() {
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
	}

	private void createUIComponents() {
		// TODO: custom components
	}
}
