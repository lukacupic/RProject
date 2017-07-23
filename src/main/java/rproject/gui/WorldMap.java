package rproject.gui;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WorldMap extends PApplet {

	private UnfoldingMap map;

	private Map<String, Marker> markers;

	private static final Color SELECTION_COLOR = new Color(250, 55, 81, 240);

	private static final Color TERRITORY_COLOR = new Color(0, 185, 89, 244);

	private static final Color SEA_COLOR = new Color(54, 111, 250, 230);

	public WorldMap() {
	}

	@Override
	public void setup() {
		size(MainWindow.DEFAULT_WIDTH, MainWindow.DEFAULT_WIDTH, OPENGL);
		smooth();

		map = new UnfoldingMap(this);
		map.setBackgroundColor(colorToInt(SEA_COLOR));
		MapUtils.createDefaultEventDispatcher(this, map);
		map.setTweening(true);

		List<Feature> countries = GeoJSONReader.loadData(this, "src/main/resources/countries.geo.json");

		List<Marker> markers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(markers);
		mapCountryMarkers(markers);

		setupGUIAccess();

		GUIAccess.setAvailable(true);
	}

	private void setupGUIAccess() {
		GUIAccess.setMap(map);
		GUIAccess.setMarkers(markers);
	}

	private void mapCountryMarkers(List<Marker> markers) {
		this.markers = new LinkedHashMap<>();

		for (Marker marker : markers) {
			this.markers.put((String) marker.getProperty("name"), marker);
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

	private Location pointToLocation(Point point) {
		return map.getLocation(point.x, point.y);
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
	public void mouseReleased() {
		System.out.println(pointToLocation(getMousePosition()));

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

	public UnfoldingMap getMap() {
		return map;
	}

	public Map<String, Marker> getMarkers() {
		return markers;
	}
}
