package rproject.gui.panels;

import rproject.gui.MainWindow;
import rproject.utils.FileUtil;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Todo: map thumbnails, analogous to Players' list

public class SettingsPanel extends JPanel {

	/**
	 * The unique identifier of this panel. Used to select
	 * currently active panel from the {@link MainWindow}.
	 */
	public static final String ID = "settingsPanel";

	/**
	 * Holds the value of the currently selected board.
	 */
	private String selectedBoard;

	/**
	 * The combo box for players' names.
	 */
	private JComboBox<String> playersComboBox;

	/**
	 * Represents a panel which holds all settings.
	 */
	private JPanel settings = new JPanel(new GridBagLayout());

	/**
	 * The constrains object for the GridBagLayout of this panel.
	 */
	private GridBagConstraints c = new GridBagConstraints();


	/**
	 * A panel holding text fields of the player names.
	 */
	// a reference is held so that it can be replaced if the user is indecisive
	private PlayerNamesPanel playerNames;

	/**
	 * The constructor.
	 */
	public SettingsPanel() {
		setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;

		// map label
		JLabel boardsLabel = initLabel("Choose your map:", 0, 0,
				new Insets(0, 0, 0, 10));
		settings.add(boardsLabel, c);

		// map combo box
		JComboBox<String> boardsComboBox = initComboBox(FileUtil.getMapsArray(), 1, 0,
				new Insets(0, 0, 0, 0));
		boardsComboBox.addActionListener(e -> selectedBoard = (String) boardsComboBox.getSelectedItem());
		settings.add(boardsComboBox, c);
		boardsComboBox.setSelectedIndex(0);

		// players label
		JLabel playersLabel = initLabel("How many players?", 0, 1,
				new Insets(30, 0, 0, 10));
		settings.add(playersLabel, c);

		// players combo box
		playersComboBox = initComboBox(new String[]{"2", "3", "4", "5", "6"},
				1, 1, new Insets(30, 0, 0, 0));
		playersComboBox.addActionListener(new PlayersComboBoxListener());
		settings.add(playersComboBox, c);
		playersComboBox.setSelectedIndex(0);

		// play button
		JButton playButton = initPlayButton();
		settings.add(playButton, c);

		add(settings);
	}


	/**
	 * Creates a new combo box filled with the given values.
	 *
	 * @param strings the values to go into the combo box' selection
	 * @param gridx   the column of the {@link GridBagLayout}
	 *                in which to put this component into
	 * @param gridy   the row of the {@link GridBagLayout}
	 *                in which to put this component into
	 * @param insets  the external padding of the component
	 * @return a new combo box with the given values
	 * @see GridBagConstraints
	 */
	private JComboBox<String> initComboBox(String[] strings, int gridx, int gridy, Insets insets) {
		JComboBox<String> playersComboBox = new JComboBox<>(strings);

		c.insets = insets;
		c.gridx = gridx;
		c.gridy = gridy;

		return playersComboBox;
	}

	/**
	 * Creates a new label with the given values.
	 *
	 * @param text   the text to go inside the component
	 * @param gridx  the column of the {@link GridBagLayout}
	 *               in which to put this component into
	 * @param gridy  the row of the {@link GridBagLayout}
	 *               in which to put this component into
	 * @param insets the external padding of the component
	 * @return a new label with the given values
	 * @see GridBagConstraints
	 */
	private JLabel initLabel(String text, int gridx, int gridy, Insets insets) {
		JLabel boardsLabel = new JLabel(text);

		c.insets = insets;
		c.gridx = gridx;
		c.gridy = gridy;

		return boardsLabel;
	}

	/**
	 * Initializes the "Play" button.
	 *
	 * @return the button which starts the actual engine with the user specified
	 * settings
	 */
	private JButton initPlayButton() {
		JButton playButton = new JButton("Play!");

		playButton.addActionListener(e -> {
			// TODO: here should the loading process be implemented or called!

			MainWindow.getMainWindow().mapPanel.init(selectedBoard, playerNames.getNames());
			MainWindow.getMainWindow().showPanel(BoardMapPanel.ID);
		});

		c.insets = new Insets(40, 0, 0, 0);
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;

		return playButton;
	}

	/**
	 * A listener for the players' names combo box.
	 */
	private class PlayersComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (playerNames != null) {
				settings.remove(playerNames);
			}

			c.insets = new Insets(10, 0, 50, 0);
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 2;

			int playersCount = Integer.parseInt((String) playersComboBox.getSelectedItem());

			playerNames = new PlayerNamesPanel(new GridLayout(playersCount, 1));

			for (int i = 1; i <= playersCount; i++) {
				playerNames.putName("Player " + i);
			}
			settings.add(playerNames, c);
			settings.revalidate();
		}
	}

	/**
	 * A panel which holds text fields with the player names.
	 */
	private class PlayerNamesPanel extends JPanel {

		/**
		 * An array of player names.
		 */
		private List<String> names = new ArrayList<>();

		/**
		 * The constructor.
		 *
		 * @param layout the layout manger for this panel
		 */
		public PlayerNamesPanel(LayoutManager layout) {
			super(layout);
		}

		/**
		 * Adds a new player's name onto the panel.
		 *
		 * @param name the name of the player
		 */
		public void putName(String name) {
			names.add(name);
			add(new JTextField(name));
		}

		/**
		 * Gets an array of all player names.
		 *
		 * @return an array of all player names.
		 */
		public String[] getNames() {
			names.clear();
			for (Component c : this.getComponents()) {
				JTextField f = (JTextField) c;
				names.add(f.getText());
			}
			return names.toArray(new String[0]);
		}
	}
}