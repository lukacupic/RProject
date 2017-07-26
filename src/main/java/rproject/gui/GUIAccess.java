package rproject.gui;

import de.fhpotsdam.unfolding.marker.Marker;
import rproject.engine.Territory;
import rproject.utils.GUIUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GUIAccess {

	public static final CountDownLatch LATCH = new CountDownLatch(1);

	private static BoardMap boardMap;

	private static volatile boolean available = false;

	// API methods

	public static void selectTerritory(Territory t, Color c) {
		getMarker(t).setColor(GUIUtil.colorToInt(c));
	}

	public static void centerMap() {
		boardMap.centerMap();
	}

	public static List<String> getTerritoryNames() {
		return boardMap.getTerritoryNames();
	}

	/**
	 * Converts the given {@link Territory} object into it's
	 * corresponding {@link Marker} object.
	 *
	 * @param territory the territory object to convert
	 * @return a {@link Marker} object representing the given
	 * territory in the GUI world
	 */
	public static Marker getMarker(Territory territory) {
		return boardMap.getMarkersMap().get(territory.getName());
	}

	// Getters and setters

	public static BoardMap getBoardMap() {
		return boardMap;
	}

	public static void setBoardMap(BoardMap boardMap) {
		GUIAccess.boardMap = boardMap;
	}

	public static boolean isAvailable() {
		return available;
	}

	public static void setAvailable(boolean available) {
		GUIAccess.available = available;
	}
}
