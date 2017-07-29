package rproject.gui.panels;

import rproject.gui.MainWindow;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class WelcomePanel extends JPanel {

	/**
	 * The unique identifier of this panel. Used to select
	 * currently active panel from the {@link MainWindow}.
	 */
	public static final String ID = "welcomePanel";

	/**
	 * The constructor.
	 */
	public WelcomePanel() {
		setLayout(new GridLayout(2, 1));

		JPanel helloPanel = new JPanel(new GridBagLayout());

		JLabel helloLabel = new JLabel("Welcome to the RProject Risk Simulator!");

		helloPanel.add(helloLabel);
		JPanel buttons = new JPanel(new FlowLayout());

		JButton mapPanelButton = new JButton("Select map");
		buttons.add(mapPanelButton);

		JButton exitButton = new JButton("Exit");
		buttons.add(exitButton);

		add(helloPanel);
		add(buttons);

		mapPanelButton.addActionListener(e -> MainWindow.getMainWindow().showPanel(SettingsPanel.ID));
		exitButton.addActionListener(e -> System.exit(0));
	}
}
