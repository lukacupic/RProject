package rproject.gui.panels;

import rproject.gui.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {

	public static final String ID = "welcomePanel";

	private JButton mapPanelButton = new JButton("Open map");

	public WelcomePanel() {
		add(mapPanelButton);

		mapPanelButton.addActionListener(e -> MainWindow.getMainWindow().show(MapPanel.ID));
	}
}
