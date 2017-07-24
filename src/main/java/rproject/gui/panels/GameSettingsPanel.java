package rproject.gui.panels;

import rproject.Game;
import rproject.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class GameSettingsPanel extends JPanel {

	public static final String ID = "gameSettingsPanel";

	private String selectedBoard;

	// a reference is needed so that it can be replaced
	// if the user is indecisive
	private JPanel playerNames;

	public GameSettingsPanel() {
		setLayout(new GridBagLayout());

		JPanel settings = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// map label
		JLabel boardsLabel = new JLabel("Choose your map:");
		c.insets = new Insets(0, 0, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		settings.add(boardsLabel, c);

		// map combo box
		JComboBox<String> boardsComboBox = new JComboBox<>(new String[]{"map1"});
		boardsComboBox.addActionListener(e -> selectedBoard = (String) boardsComboBox.getSelectedItem());
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 1;
		c.gridy = 0;
		settings.add(boardsComboBox, c);

		// players label
		JLabel playersLabel = new JLabel("How many players?");
		c.insets = new Insets(30, 0, 0, 10);
		c.gridx = 0;
		c.gridy = 1;
		settings.add(playersLabel, c);

		// players combo box
		JComboBox<String> playersComboBox = new JComboBox<>(new String[]{"2", "3", "4", "5", "6"});
		playersComboBox.addActionListener(e -> {
			if (playerNames != null) {
				settings.remove(playerNames);
			}

			//For player names
			c.insets = new Insets(5, 0, 30, 0);
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 2;

			int playersCount = Integer.parseInt((String) playersComboBox.getSelectedItem());

			playerNames = new JPanel(new GridLayout(playersCount, 1));

			for (int i = 1; i <= playersCount; i++) {
				playerNames.add(new JTextField("Player " + i));
			}
			settings.add(playerNames, c);
			settings.revalidate();
		});
		c.insets = new Insets(30, 0, 0, 0);
		c.gridx = 1;
		c.gridy = 1;
		settings.add(playersComboBox, c);

		// play button
		JButton playButton = new JButton("Play!");
		playButton.addActionListener(e -> {
			// TODO: here should the loading process be implemented or called!

			Game game = new Game(selectedBoard, new String[]{"A", "B", "C"});
			game.start();

			MainWindow.getMainWindow().showPanel(BoardMapPanel.ID);
		});
		c.insets = new Insets(40, 0, 0, 0);
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		settings.add(playButton, c);

		add(settings);
	}
}
