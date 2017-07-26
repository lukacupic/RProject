package rproject.board;

import rproject.Territory;
import rproject.utils.FileUtil;
import rproject.gui.GUIAccess;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Board {

	private List<Territory> territories = new ArrayList<>();

	private NeighbourhoodMatrix matrix;

	public Board(String name) {
		matrix = new NeighbourhoodMatrix(name);
		initTerritories(name);
	}

	private void initTerritories(String name) {
		// Try accessing the GUI; if not yet initialized, read from the 'names'
		// Reading the 'names' should be removed after testing
		List<String> lines = GUIAccess.isAvailable() ?
				GUIAccess.getBoardMap().getTerritoryNames() : FileUtil.readNames(name);

		for (String line : lines) {
			territories.add(new Territory(line));
		}
	}

	public List<Territory> getTerritories() {
		return territories;
	}

	public Territory getTerritory(String name) {
		for (Territory t : territories) {
			if (name.equals(t.getName())) return t;
		}
		return null;
	}

	public Territory getTerritory(int index) {
		return BoardProvider.getBoard().getTerritories().get(index);
	}

	public NeighbourhoodMatrix getMatrix() {
		return matrix;
	}
}