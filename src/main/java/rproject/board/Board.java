package rproject.board;

import rproject.Territory;
import rproject.files.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Territory> territories = new ArrayList<>();

	private NeighbourhoodMatrix matrix;

	public Board(String name) {
		matrix = new NeighbourhoodMatrix(name);
		initTerritories(name);
	}

	private void initTerritories(String name) {
		List<String> lines = FileUtil.readNames(name);
		for (String line : lines) {
			territories.add(new Territory(line));
		}
	}

	private List<String> readLines(String path) {
		try {
			return Files.readAllLines(Paths.get(path));
		} catch (IOException ex) {
			throw new RuntimeException();
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