package rproject.gui;

import rproject.Territory;

public class GUIAccess {

	private static volatile boolean available = false;

	private static BoardMap boardMap;

	// API methods

	public static void selectTerritory(Territory t) {
		//boardMap.getMarkersMap().get(t.getName()).setSelected(true);
		boardMap.getMarkersMap().get("Canada").setSelected(true);
	}

	public static void centerMap() {
		boardMap.centerMap();
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
