package rproject.engine;

import de.fhpotsdam.unfolding.marker.Marker;
import rproject.board.BoardProvider;

import java.awt.*;

public class GameAccess {

	/**
	 * Converts the given {@link Marker} object into it's
	 * corresponding {@link Territory} object.
	 *
	 * @param marker the marker object to convert
	 * @return a {@link Territory} object representing the given
	 * marker in the Game
	 */
	public static Territory getTerritory(Marker marker) {
		return BoardProvider.getBoard().getTerritory((String) marker.getProperty("name"));
	}
}
