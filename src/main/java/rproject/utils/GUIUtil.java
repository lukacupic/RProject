package rproject.utils;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import de.fhpotsdam.unfolding.utils.GeneralizationUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PVector;
import rproject.engine.Territory;
import rproject.gui.CGBridge;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

	/**
	 * Simplifies the given markers by simplifying locations of the underlying
	 * polygons for each of the given marker.
	 *
	 * @param markers the markers to simplify
	 * @param factor  the simplification factor; see {@link GeneralizationUtils#simplify}
	 * @param map     the map containing the polygon
	 */
	public static void simplifyMarkers(List<Marker> markers, double factor, UnfoldingMap map) {
		for (Marker m : markers) {
			simplifyRecursively(m, factor, map);
		}
	}

	/**
	 * Simplifies the given marker by simplifying locations of the underlying
	 * polygon of the given marker.
	 *
	 * @param m      the marker to simplify
	 * @param factor the simplification factor
	 * @param map    the map containing the polygon
	 */
	public static void simplifyRecursively(Marker m, double factor, UnfoldingMap map) {
		if (m instanceof MultiMarker) {
			for (Marker subm : ((MultiMarker) m).getMarkers()) {
				simplifyRecursively(subm, factor, map);
			}
		} else if (m instanceof SimplePolygonMarker) {
			SimplePolygonMarker sm = (SimplePolygonMarker) m;
			simplify(sm, factor, map);
		}
	}

	/**
	 * Simplifies the given polygon marker, i.e. simplifies it's location
	 * vectors.
	 *
	 * @param marker the marker to simplify
	 * @param factor the simplification factor
	 * @param map    the map containing the polygon
	 */
	public static void simplify(SimplePolygonMarker marker, double factor, UnfoldingMap map) {
		List<PVector> vectors = new ArrayList<>();
		for (Location l : marker.getLocations()) {
			vectors.add(map.getScreenPosition(l));
		}
		vectors = GeneralizationUtils.simplify(vectors, (float) factor, true);

		List<Location> locations = new ArrayList<>();
		for (PVector v : vectors) {
			locations.add(map.getLocation((ScreenPosition) v));
		}
		marker.setLocations(locations);
	}

	// TODO: clean up
	public static JPanel createTablePanel(Map<String, String> table) {
		int numPairs = table.size();

		JPanel p = new JPanel(new SpringLayout());
		for (Map.Entry<String, String> entry : table.entrySet()) {
			JLabel l = new JLabel(entry.getKey(), JLabel.TRAILING);
			p.add(l);

			JLabel label = new JLabel(entry.getValue());
			l.setLabelFor(label);
			p.add(label);
		}

		SpringUtilities.makeCompactGrid(p,
				numPairs, 2,     // rows, cols
				6, 6,   // initX, initY
				6, 6       // xPad, yPad
		);
		return p;
	}
}
