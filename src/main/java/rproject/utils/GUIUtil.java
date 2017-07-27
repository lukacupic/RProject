package rproject.utils;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import rproject.gui.CGBridge;

import java.awt.*;

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
		// todo: try using Color#HSBtoRGB instead
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
}
