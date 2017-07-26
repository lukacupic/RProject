package rproject.gui;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import de.fhpotsdam.unfolding.utils.GeneralizationUtils;
import de.fhpotsdam.unfolding.utils.GeoUtils;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;
import processing.core.PVector;
import rproject.files.FileUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardMap extends PApplet {

	private static final Color SELECTION_COLOR = new Color(250, 55, 81, 240);

	private static final Color TERRITORY_COLOR = new Color(0, 185, 89, 244);

	private static final Color SEA_COLOR = new Color(54, 111, 250, 230);

	private static final Location REAL_CENTER = new Location(35, 12);

	private UnfoldingMap map;

	/**
	 * The name of the board.
	 */
	private String name;

	private Map<String, Marker> markersMap;

	private List<Marker> markersList;

	private List<String> territoryNames;

	public BoardMap(String name) {
		this.name = name;
	}

	@Override
	public void setup() {
		MainWindow mw = MainWindow.getMainWindow();
		size(mw.getWidth(), mw.getHeight(), OPENGL);
		smooth();

		map = new UnfoldingMap(this);
		map.setBackgroundColor(colorToInt(SEA_COLOR));
		map.setTweening(true);

		MapUtils.createDefaultEventDispatcher(this, map);

		List<Feature> features = GeoJSONReader.loadData(this, FileUtil.MAP_COORDS_PATH + name + ".json");

		markersList = MapUtils.createSimpleMarkers(features);
		simplifyMarkers(markersList, 1.5);

		map.addMarkers(markersList);
		mapCountryMarkers(markersList);

		territoryNames = new ArrayList<>(markersMap.keySet());

		centerMap();

		setupGUIAccess();
	}

	/**
	 * Simplifies the given markers by simplifying locations of the underlying
	 * polygons for each of the given marker.
	 *
	 * @param markers the markers to simplify
	 * @param factor  the simplification factor; see {@link GeneralizationUtils#simplify}
	 */
	private void simplifyMarkers(List<Marker> markers, double factor) {
		for (Marker m : markers) {
			simplifyRecursively(m, factor);
		}
	}

	/**
	 * Simplifies the given marker by simplifying locations of the underlying
	 * polygon of the given marker.
	 *
	 * @param m      the marker to simplify
	 * @param factor the simplification factor
	 */
	private void simplifyRecursively(Marker m, double factor) {
		if (m instanceof MultiMarker) {
			for (Marker subm : ((MultiMarker) m).getMarkers()) {
				simplifyRecursively(subm, factor);
			}
		} else if (m instanceof SimplePolygonMarker) {
			SimplePolygonMarker sm = (SimplePolygonMarker) m;

			List<PVector> vectors = new ArrayList<>();
			for (Location l : sm.getLocations()) {
				vectors.add(map.getScreenPosition(l));
			}
			vectors = GeneralizationUtils.simplify(vectors, (float) factor, true);

			List<Location> locations = new ArrayList<>();
			for (PVector v : vectors) {
				locations.add(map.getLocation((ScreenPosition) v));
			}
			sm.setLocations(locations);
		}
	}

	public void centerMap() {
		map.panTo(REAL_CENTER); // pan to the 'real' center
		map.zoomAndPanToFit(GeoUtils.getLocationsFromMarkers(markersList));
	}

	private void setupGUIAccess() {
		GUIAccess.setBoardMap(this);
		GUIAccess.LATCH.countDown();
		GUIAccess.setAvailable(true);
	}

	private void mapCountryMarkers(List<Marker> markers) {
		this.markersMap = new LinkedHashMap<>();

		for (Marker marker : markers) {
			this.markersMap.put((String) marker.getProperty("name"), marker);
		}
	}

	private void changeMarkerColor(Marker m, Color c) {
		int color = colorToInt(c);
		((AbstractMarker) m).setHighlightColor(color);
		((AbstractMarker) m).setHighlightStrokeColor(color);
	}

	private int colorToInt(Color c) {
		return color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	@Override
	public void draw() {
		background(colorToInt(SEA_COLOR));
		map.draw();

		for (Marker m : map.getMarkers()) {
			m.setColor(colorToInt(TERRITORY_COLOR));
		}

		Marker marker = map.getFirstHitMarker(mouseX, mouseY);

		if (marker != null) {
			Point p = locationToPoint(marker.getLocation());
			text((String) marker.getProperty("name"), p.x, p.y);
		}
	}

	@Override
	public void mouseMoved() {
		for (Marker marker : map.getMarkers()) {
			marker.setSelected(false);
		}

		Marker marker = map.getFirstHitMarker(mouseX, mouseY);
		if (marker == null) return;

		if (marker instanceof MultiMarker) {
			for (Marker submarker : ((MultiMarker) marker).getMarkers()) {
				changeMarkerColor(submarker, SELECTION_COLOR);
			}
		} else {
			changeMarkerColor(marker, SELECTION_COLOR);
		}

		marker.setSelected(true);
	}

	private Location pointToLocation(Point point) {
		return map.getLocation(point.x, point.y);
	}

	private Point locationToPoint(Location location) {
		ScreenPosition sp = map.getScreenPosition(location);
		return new Point((int) sp.x, (int) sp.y);
	}

	// Getters and setters

	public Map<String, Marker> getMarkersMap() {
		return markersMap;
	}

	public List<String> getTerritoryNames() {
		return territoryNames;
	}
}
