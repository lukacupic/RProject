package rproject.gui;

import rproject.Territory;

import java.util.concurrent.CountDownLatch;

public class GUIAccess {

	public static final CountDownLatch LATCH = new CountDownLatch(1);

	private static BoardMap boardMap;

	private static volatile boolean available = false;

	// API methods

	public static void selectTerritory(Territory t) {
		boardMap.getMarkersMap().get(t.getName()).setSelected(true);
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
