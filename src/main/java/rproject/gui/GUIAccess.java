package rproject.gui;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import rproject.Territory;

import java.util.Map;

public class GUIAccess {

	private static UnfoldingMap map;

	private static Map<String, Marker> markers;

	public static void selectTerritory(Territory t) {
		// todo:
		// "na" should be replaced with t.getName() after
		// the map visually contains a territory North America
		markers.get("na").setSelected(true);
	}

	public static void setMap(UnfoldingMap map) {
		GUIAccess.map = map;
	}

	public static void setMarkers(Map<String, Marker> markers) {
		GUIAccess.markers = markers;
	}
}
