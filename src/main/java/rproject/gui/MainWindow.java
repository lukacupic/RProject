package rproject.gui;

import rproject.gui.panels.BoardMapPanel;
import rproject.gui.panels.SettingsPanel;
import rproject.gui.panels.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class MainWindow extends JFrame {

	private static MainWindow mainWindow;

	// General program settings

	public static final int DEFAULT_WIDTH = 1000;

	public static final int DEFAULT_HEIGHT = 650;

	public static final Font DEFAULT_FONT =
			new Font("Verdana", Font.BOLD, 12);

	// Panels (screens)

	private CardLayout cl = new CardLayout();

	private JPanel panelCont = new JPanel();

	private JPanel welcomePanel = new WelcomePanel();

	private JPanel gameSettingsPanel = new SettingsPanel();

	private JPanel mapPanel = new BoardMapPanel();

	public static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * The constructor.
	 */
	public MainWindow() {
		mainWindow = this;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		setVisible(true);
		setResizable(false);
		setTitle("RProject Risk Simulator v1.0");

		initGUI();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		panelCont.setLayout(cl);

		panelCont.add(welcomePanel, WelcomePanel.ID);
		panelCont.add(gameSettingsPanel, SettingsPanel.ID);
		panelCont.add(mapPanel, BoardMapPanel.ID);
		add(panelCont);

		cl.show(panelCont, WelcomePanel.ID); // default (first) panel
	}

	/**
	 * Shows a panel specified by the given ID.
	 *
	 * @param id the identifier string of a panel to display
	 */
	public void showPanel(String id) {
		cl.show(panelCont, id);
	}

	/**
	 * Returns an instance of the main window of the program.
	 *
	 * @return the program's main window
	 */
	public static MainWindow getMainWindow() {
		return mainWindow;
	}

	/**
	 * Starts the RProject simulator.
	 *
	 * @param args command line arguments; not used in this program
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
	}
}
