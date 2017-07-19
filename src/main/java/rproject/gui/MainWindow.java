package rproject.gui;

import javax.swing.*;

public class MainWindow extends JFrame {

	public MainWindow() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(800, 500);
		setVisible(true);
		setTitle("RProject Risk Simulator v1.0");

		initSettings();
		initGUI();
	}

	private void initSettings() {

	}

	private void initGUI() {

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainWindow::new);
	}
}
