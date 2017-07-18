package rproject;

import rproject.io.Output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NeighbourhoodMatrix {
	private int size;
	private boolean[][] neighbourhoodMatrix;

	public NeighbourhoodMatrix(String FileName) {
		List<String> lines;
		try {
			// todo: a bug where 'FileName' represents a full name of the map (e.g. "map1.txt")
			// todo: should be resolved in FileUtil#readMaps. This method returns full names of
			// todo: maps but should be returning only name without the extension. This will be
			// todo: fixed wendesday morning :P
			lines = Files.readAllLines(Paths.get("maps/matrix/" + FileName + ".txt"));
		} catch (IOException ex) {
			throw new RuntimeException();
		}

		size = Integer.parseInt(lines.get(0));
		neighbourhoodMatrix = new boolean[size][size];
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				neighbourhoodMatrix[i][j] = (lines.get(i + 1).charAt(j) == '1');
	}

	public boolean checkNeighbours(int from, int to) {
		Output.write(from);
		Output.write(" -> ");
		Output.write(to);
		Output.writeln(" ???");
		return neighbourhoodMatrix[from][to];
	}

	public void drawMatrixCUI() {
		for (int i = 0; i < size; ++i) {
			String playerIName = BoardProvider.getBoard().getTerritory(i).getOwner().getName();
			String TerritoryIName = BoardProvider.getBoard().getTerritory(i).getName();
			int cntIUnits = BoardProvider.getBoard().getTerritory(i).getUnits();
			Output.write(TerritoryIName);
			Output.write(" (P: ");
			Output.write(playerIName);
			Output.write(", U: ");
			Output.write(cntIUnits);
			Output.write(") -> ");
			//todo: should be (player p, x units)
			for (int j = 0; j < size; ++j) {
				if (neighbourhoodMatrix[i][j]) {
					String playerJName = BoardProvider.getBoard().getTerritory(j).getOwner().getName();
					String TerritoryJName = BoardProvider.getBoard().getTerritory(j).getName();
					int cntJUnits = BoardProvider.getBoard().getTerritory(j).getUnits();
					Output.write(TerritoryJName);
					Output.write(" (P: ");
					Output.write(playerJName);
					Output.write(", U: ");
					Output.write(cntJUnits);
					Output.write(") -> ");
					//todo: should be (player p, x units)
				}
			}
			Output.writeln("");
		}
	}
};