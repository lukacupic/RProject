package rproject.gui;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.utils.GeoUtils;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardMap extends PApplet {

	private UnfoldingMap map;

	private Map<String, Marker> markersMap;

	private List<Marker> markersList;

	private static final Color SELECTION_COLOR = new Color(250, 55, 81, 240);

	private static final Color TERRITORY_COLOR = new Color(0, 185, 89, 244);

	private static final Color SEA_COLOR = new Color(54, 111, 250, 230);

	private boolean mouseDragged;

	@Override
	public void setup() {
		MainWindow mw = MainWindow.getMainWindow();
		size(mw.getWidth(), mw.getHeight(), OPENGL);
		smooth();

		map = new UnfoldingMap(this);
		map.setBackgroundColor(colorToInt(SEA_COLOR));
		map.setTweening(true);

		MapUtils.createDefaultEventDispatcher(this, map);

		List<Feature> countries = GeoJSONReader.loadData(this, "src/main/resources/countries.geo.json");

		markersList = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(markersList);
		mapCountryMarkers(markersList);

		centerMap();

		setupGUIAccess();
	}

	public void centerMap() {
		map.panTo(new Location(35, 12)); // pan to the 'real' center
		map.zoomAndPanToFit(getAllLocations(markersList));
	}

	private void setupGUIAccess() {
		GUIAccess.setBoardMap(this);
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
	}

	@Override
	public void mouseDragged() {
		mouseDragged = true;
	}

	@Override
	public void mouseReleased() {

		if (mouseDragged) {
			mouseDragged = false;
			return;
		}

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

	private List<Location> getAllLocations(List<Marker> markers) {
		return GeoUtils.getLocationsFromMarkers(markers);
	}

	private Location pointToLocation(Point point) {
		return map.getLocation(point.x, point.y);
	}

	private Point locationToPoint(Location location) {
		ScreenPosition sp = map.getScreenPosition(location);
		return new Point((int) sp.x, (int) sp.y);
	}

	// Getters and setters

	public UnfoldingMap getMap() {
		return map;
	}

	public Map<String, Marker> getMarkersMap() {
		return markersMap;
	}
}
