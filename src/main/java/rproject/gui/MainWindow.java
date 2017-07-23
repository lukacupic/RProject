package rproject.gui;

import rproject.Game;
import rproject.board.BoardProvider;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

	public static final int DEFAULT_WIDTH = 800;

	public static final int DEFAULT_HEIGHT = 500;

	private WorldMap worldMap;

	public MainWindow() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setVisible(true);
		setTitle("RProject Risk Simulator v1.0");

		initSettings();
		initGUI();
	}

	private void initSettings() {
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		worldMap = new WorldMap();
		add(worldMap, BorderLayout.CENTER);
		worldMap.init();

		Game game = new Game("map1", new String[]{"A", "B", "C"});

		new Thread(() -> {
			while (!GUIAccess.isAvailable()) ;
			GUIAccess.selectTerritory(BoardProvider.getBoard().getTerritory("na"));
		}).start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
	}
}
