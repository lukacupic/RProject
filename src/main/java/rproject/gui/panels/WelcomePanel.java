package rproject.gui.panels;

import rproject.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

	public static final String ID = "welcomePanel";

	public WelcomePanel() {
		setLayout(new GridLayout(2, 1));

		JPanel helloPanel = new JPanel(new GridBagLayout());

		JLabel helloLabel = new JLabel("Welcome to the RProject Risk Simulator!");
		helloLabel.setFont(MainWindow.DEFAULT_FONT);

		helloPanel.add(helloLabel);
		JPanel buttons = new JPanel(new FlowLayout());

		JButton mapPanelButton = new JButton("Select map");
		buttons.add(mapPanelButton);

		JButton exitButton = new JButton("Exit");
		buttons.add(exitButton);

		add(helloPanel);
		add(buttons);

		mapPanelButton.addActionListener(e -> MainWindow.getMainWindow().show(MapPanel.ID));
	}
}
