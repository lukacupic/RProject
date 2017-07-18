package rproject;

import java.io.IOException;
import java.util.Map;

public class Board {

	private Map<String, Territory> territoryNames;

	private NeighbourhoodMatrix nMatrix;

	public Board(String name) throws IOException {
		nMatrix = new NeighbourhoodMatrix(name);

	}
}