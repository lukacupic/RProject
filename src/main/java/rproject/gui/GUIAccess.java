package rproject.gui;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import rproject.Territory;

import java.util.Map;

public class GUIAccess {


	private static UnfoldingMap map;

	private static Map<String, Marker> markers;

	private static volatile boolean available = false;

	public static void selectTerritory(Territory t) {
		//markers.get(t.getName()).setSelected(true);
		markers.get("Canada").setSelected(true);
	}

	public static void setMap(UnfoldingMap map) {
		GUIAccess.map = map;
	}

	public static void setMarkers(Map<String, Marker> markers) {
		GUIAccess.markers = markers;
	}

	public static boolean isAvailable() {
		return available;
	}

	public static void setAvailable(boolean available) {
		GUIAccess.available = available;
	}
}
