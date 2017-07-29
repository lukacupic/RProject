package rproject.gui;

import rproject.gui.panels.BoardMapPanel;
import rproject.gui.panels.SettingsPanel;
import rproject.gui.panels.WelcomePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.CardLayout;

/**
 * The main window of the program; starts the program.
 */
public class MainWindow extends JFrame {

	/**
	 * The default width of the window.
	 */
	public static final int DEFAULT_WIDTH = 1000;

	/**
	 * The default height of the window.
	 */
	public static final int DEFAULT_HEIGHT = 650;

	/**
	 * An instance of the main window.
	 * This is setup in the constructor.
	 */
	private static MainWindow mainWindow;
	/**
	 * The 'welcome screen' panel - first panel the user sees.
	 */
	public WelcomePanel welcomePanel = new WelcomePanel();
	/**
	 * The 'settings' panel - for choosing the game settings
	 */
	public SettingsPanel gameSettingsPanel = new SettingsPanel();
	/**
	 * The 'map' panel - for playing the game
	 */
	public BoardMapPanel mapPanel = new BoardMapPanel();
	/**
	 * The layout for displaying the panels.
	 */
	private CardLayout cl = new CardLayout();
	/**
	 * The container for holding the panels.
	 */
	private JPanel panelCont = new JPanel();

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
	 * Returns an instance of the main window of the program.
	 *
	 * @return the program's main window
	 */
	public static MainWindow getMainWindow() {
		return mainWindow;
	}

	/**
	 * Starts the RProject program.
	 *
	 * @param args command line arguments; not used in this program
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
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
}
