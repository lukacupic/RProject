package rproject.gui.panels;

import rproject.gui.WorldMap;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {

	public static final String ID = "mapPanel";

	private WorldMap worldMap = new WorldMap();

	public MapPanel() {
		worldMap.init();

		add(worldMap);
	}
}
