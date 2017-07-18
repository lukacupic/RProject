package rproject;

import java.util.Map;

public class Board {

	private Map<String, Territory> territoryNames;

	private NeighbourhoodMatrix nMatrix;

	public Board(String name) {
		nMatrix = new NeighbourhoodMatrix(name);
	}
}