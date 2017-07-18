package rproject;

import java.util.Map;

public class Board {

	private Map<String, Territory> territoryNames;

	private NeighbourhoodMatrix Matrix;

	public Board(String name) {
		Matrix = new NeighbourhoodMatrix(name);
		BoardProvider.setBoard(this);
	}

	public NeighbourhoodMatrix getMatrix() {
		return Matrix;
	}
}