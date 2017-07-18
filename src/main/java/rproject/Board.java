package rproject;

import java.util.Map;

public class Board {

	private Map<String, Territory> territoryNames;

	private NeighbourhoodMatrix matrix;

	public Board(String name) {
		matrix = new NeighbourhoodMatrix(name);

		BoardProvider.setBoard(this);
	}

	public NeighbourhoodMatrix getMatrix() {
		return matrix;
	}

	public Map<String, Territory> getTerritoryNames() {
		return territoryNames;
	}
}