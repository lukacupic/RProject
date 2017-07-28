package rproject.gui;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import de.fhpotsdam.unfolding.utils.GeneralizationUtils;
import de.fhpotsdam.unfolding.utils.GeoUtils;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;
import processing.core.PVector;
import rproject.engine.Territory;
import rproject.utils.FileUtil;
import rproject.utils.GUIUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardMap extends PApplet {

	/**
	 * Default sea color.
	 */
	private static final Color SEA_COLOR = new Color(54, 111, 250, 230);

	/**
	 * The 'real' center of the map. For some reason, the default center
	 * is inaccurate, hence the map is centered to this location in the
	 * setup phase via {@link #centerMap()} method.
	 */
	private static final Location REAL_CENTER = new Location(35, 12);

	/**
	 * The main map used to display territories.
	 */
	private UnfoldingMap map;

	/**
	 * The legend for displaying currently active players.
	 */
	private Legend legend;

	/**
	 * The name of the board.
	 */
	private String name;

	/**
	 * Holds all (top-level) markers contained in the {@link #map},
	 * where each marker is mapped to by it's name.
	 * For example:
	 * String "Zimbabwe" will be mapped to the marker which represents
	 * the Zimbabwe country and holds all vertices of that polygon.
	 */
	private Map<String, Marker> markersMap;

	/**
	 * Holds all (top-level) markers contained in the {@link #map}.
	 */
	private List<Marker> markersList;

	/**
	 * A list of territory names. Extracted from the {@link #markersMap}.
	 */
	private List<String> territoryNames;

	/**
	 * Creates a new {@link BoardMap} with the given name.
	 *
	 * @param name the name of the board
	 */
	public BoardMap(String name) {
		this.name = name;
	}

	@Override
	public void setup() {
		Component applet = MainWindow.getMainWindow().mapPanel.getComponent(0);
		int appletWidth = applet.getWidth();
		int appletHeight = applet.getHeight();
		size(appletWidth, appletHeight, OPENGL);

		smooth();

		map = new UnfoldingMap(this);

		map.setBackgroundColor(GUIUtil.colorToInt(SEA_COLOR));
		map.setTweening(true);

		legend = new Legend(this);

		MapUtils.createDefaultEventDispatcher(this, map);

		List<Feature> features = GeoJSONReader.loadData(this, FileUtil.MAP_COORDS_PATH + name + ".json");

		markersList = MapUtils.createSimpleMarkers(features);

		// simplify polygons for some specific maps
		// todo: automate verification process: e.g. vertex count as a threshold
		if (name.equals("continents")) {
			simplifyMarkers(markersList, 1.5);
		}

		map.addMarkers(markersList);
		mapMarkers(markersList);

		territoryNames = new ArrayList<>(markersMap.keySet());

		centerMap();

		enableGUIAccess();
	}

	@Override
	public void draw() {
		background(GUIUtil.colorToInt(SEA_COLOR));
		map.setBackgroundColor(GUIUtil.colorToInt(SEA_COLOR));
		map.draw();
		legend.draw();

		// set each marker's color and outline properties
		for (Marker m : map.getMarkers()) {
			m.setColor(GUIUtil.colorToInt(GUIUtil.getMarkerColor(m)));
			m.setStrokeColor(GUIUtil.colorToInt(Color.BLACK));
			m.setStrokeWeight(1);
		}

		Marker marker = CGBridge.getSelectedMarker();
		if (marker != null) {
			selectMarker(marker);
		}
	}

	@Override
	public void mouseClicked() {
		Territory t = CGBridge.getSelectedTerritory();
		Map<String, String> p = GUIUtil.getTerritoryProperties(t);

		System.out.println("==================================");
		for (Map.Entry<String, String> entry : p.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("==================================");
	}

	/**
	 * Darkens the color of the given marker.
	 *
	 * @param marker the marker
	 */
	private void selectMarker(Marker marker) {
		Color c = GUIUtil.getMarkerColor(marker);
		marker.setColor(GUIUtil.colorToInt(c.darker()));
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
			simplify(sm, factor);
		}
	}

	/**
	 * Simplifies the given polygon marker, i.e. simplifies it's location
	 * vectors.
	 *
	 * @param marker the marker to simplify
	 * @param factor the simplification factor
	 */
	private void simplify(SimplePolygonMarker marker, double factor) {
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

	/**
	 * Places the map into the center of the component.
	 */
	public void centerMap() {
		map.panTo(REAL_CENTER); // pan to the 'real' center
		map.zoomAndPanToFit(GeoUtils.getLocationsFromMarkers(markersList));
	}

	/**
	 * Enables access to the GUI component of the program; the GUI
	 * access is performed trough the {@link CGBridge} class.
	 */
	private void enableGUIAccess() {
		CGBridge.setBoardMap(this);
		CGBridge.LATCH.countDown();
		CGBridge.setConnected(true);
	}

	/**
	 * Creates a map from the given list of markers. The map maps the
	 * marker's name to the marker itself.
	 *
	 * @param markers the list of markers to create a map from
	 */
	private void mapMarkers(List<Marker> markers) {
		this.markersMap = new LinkedHashMap<>();

		for (Marker marker : markers) {
			this.markersMap.put((String) marker.getProperty("name"), marker);
		}
	}

	// Getters and setters


	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public UnfoldingMap getMap() {
		return map;
	}

	/**
	 * Gets the map of all the markers.
	 *
	 * @return the map which stores all markers
	 */
	public Map<String, Marker> getMarkersMap() {
		return markersMap;
	}

	/**
	 * Gets a list of territory names.
	 *
	 * @return a list of territory names
	 */
	public List<String> getTerritoryNames() {
		return territoryNames;
	}
}
