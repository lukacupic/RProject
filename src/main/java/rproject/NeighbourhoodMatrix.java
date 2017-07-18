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
			int cntIUnits = BoardProvider.getBoard().getTerritory(i).getUnits();
			Output.write(i);
			Output.write(" (player: ");
			Output.write(playerIName);
			Output.write(", x ");
			Output.write(cntIUnits);
			Output.write(") -> ");
			//todo: should be (player p, x units)
			for (int j = 0; j < size; ++j) {
				if (neighbourhoodMatrix[i][j]) {
					String playerJName = BoardProvider.getBoard().getTerritory(j).getOwner().getName();
					int cntJUnits = BoardProvider.getBoard().getTerritory(j).getUnits();
					Output.write(j);
					Output.write(" (player: ");
					Output.write(playerJName);
					Output.write(", x ");
					Output.write(cntJUnits);
					Output.write(") -> ");
					//todo: should be (player p, x units)
				}
			}
			Output.writeln("");
		}
	}
};