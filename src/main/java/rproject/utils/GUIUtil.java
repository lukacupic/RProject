package rproject.utils;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import rproject.engine.Territory;
import rproject.gui.CGBridge;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides methods for some common tasks while working with GUI.
 */
public class GUIUtil {

	/**
	 * Converts the specified point into a geographical location on the given map.
	 *
	 * @param map   the map
	 * @param point the point
	 * @return a location (in terms of latitude and longitude) on the given map
	 */
	public static Location pointToLocation(UnfoldingMap map, Point point) {
		return map.getLocation(point.x, point.y);
	}

	/**
	 * Converts the specified location into a point on the given map.
	 *
	 * @param map      the map
	 * @param location the location
	 * @return a point (in terms of screen coordinate) on the given map
	 */
	public static Point locationToPoint(UnfoldingMap map, Location location) {
		ScreenPosition sp = map.getScreenPosition(location);
		return new Point((int) sp.x, (int) sp.y);
	}

	/**
	 * Converts the given color to an integer-based color.
	 *
	 * @param c the color to convert
	 * @return an integer-based color
	 */
	public static int colorToInt(Color c) {
		return (c.getAlpha() << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue();
		// TODO: Try using Color#HSBtoRGB instead
	}

	/**
	 * Gets the current color of the given marker.
	 *
	 * @param marker the marker
	 * @return the color of the given marker
	 */
	public static Color getMarkerColor(Marker marker) {
		return CGBridge.getTerritory(marker).getOwner().getColor();
	}

	/**
	 * Returns properties of the given territory. These properties include:
	 * TODO: List of properties
	 *
	 * @param t the territory to get the properties of
	 * @return a map containing properties of the given territory
	 */
	public static Map<String, String> getTerritoryProperties(Territory t) {
		Map<String, String> p = new LinkedHashMap<>();

		p.put("Name", t.getName());
		p.put("Owner", t.getOwner().getName());

		for (Map.Entry<String, Integer> entry : t.getUnitsCount().entrySet()) {
			int n = entry.getValue();
			if (n == 0) continue;
			p.put(entry.getKey() + (n > 1 ? "s" : ""), String.valueOf(n));
		}
		return p;
	}
}
