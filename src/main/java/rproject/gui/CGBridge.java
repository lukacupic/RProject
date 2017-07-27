package rproject.gui;

import de.fhpotsdam.unfolding.marker.Marker;
import rproject.board.BoardProvider;
import rproject.engine.Territory;
import rproject.utils.GUIUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * This class stands for "Core-GUI Bridge" and represents a communication
 * channel between the Core and the GUI components of the program.
 */
public class CGBridge {

	/**
	 * A latch used for signalizing that the GUI has been initialized.
	 */
	public static final CountDownLatch LATCH = new CountDownLatch(1);

	/**
	 * The current board map on which the game is played.
	 */
	private static BoardMap boardMap;

	/**
	 * Indicates whether the communication has been set.
	 */
	private static volatile boolean connected = false;

	/**
	 * Colors the specified territory with the given color.
	 *
	 * @param t the territory to color
	 * @param c the new color of the territory
	 */
	public static void colorTerritory(Territory t, Color c) {
		getMarker(t).setColor(GUIUtil.colorToInt(c));
	}

	/**
	 * Places the playing map in the center of it's parent component.
	 */
	public static void centerMap() {
		boardMap.centerMap();
	}

	/**
	 * Fetches a list of names for all territories on the board.
	 *
	 * @return a list of names for all territories on the board
	 */
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

	/**
	 * Converts the given {@link Marker} object into it's
	 * corresponding {@link Territory} object.
	 *
	 * @param marker the marker object to convert
	 * @return a {@link Territory} object representing the given
	 * marker in the Game
	 */
	public static Territory getTerritory(Marker marker) {
		return BoardProvider.getBoard().getTerritory((String) marker.getProperty("name"));
	}

	/**
	 * Fetches the current {@link BoardMap} object.
	 *
	 * @return the current {@link BoardMap} object.
	 */
	public static BoardMap getBoardMap() {
		return boardMap;
	}

	/**
	 * Sets the given {@link BoardMap} object as current.
	 *
	 * @param boardMap the new {@link BoardMap} object
	 */
	public static void setBoardMap(BoardMap boardMap) {
		CGBridge.boardMap = boardMap;
	}

	/**
	 * Checks whether the bridge is connected, i.e. if
	 * the communication is available.
	 *
	 * @return true if the bridge is connected; false otherwise
	 */
	public static boolean isConnected() {
		return connected;
	}

	/**
	 * Sets the connection for the bridge.
	 *
	 * @param connected a boolean for indicating the new status
	 *                  of the connection
	 */
	public static void setConnected(boolean connected) {
		CGBridge.connected = connected;
	}
}
