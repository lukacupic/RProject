package rproject.utils;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

import java.awt.*;

public class GUIUtil {

	public static Location pointToLocation(UnfoldingMap map, Point point) {
		return map.getLocation(point.x, point.y);
	}

	public static Point locationToPoint(UnfoldingMap map, Location location) {
		ScreenPosition sp = map.getScreenPosition(location);
		return new Point((int) sp.x, (int) sp.y);
	}
}
