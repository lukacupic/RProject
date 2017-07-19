package rproject.board;

import rproject.files.FileUtil;
import rproject.io.Output;

import java.util.List;

public class NeighbourhoodMatrix {

	private int size;

	private boolean[][] neighbourhoodMatrix;

	public NeighbourhoodMatrix(String name) {
		List<String> lines = FileUtil.readMatrix(name);

		size = Integer.parseInt(lines.get(0));
		neighbourhoodMatrix = new boolean[size][size];
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				neighbourhoodMatrix[i][j] = (lines.get(i + 1).charAt(j) == '1');
	}

	public boolean checkNeighbours(int from, int to) {
		return neighbourhoodMatrix[from][to];
	}

	public void drawMatrixCUI() {
		for (int i = 0; i < size; ++i) {
			String playerIName = BoardProvider.getBoard().getTerritory(i).getOwner().getName();
			String TerritoryIName = BoardProvider.getBoard().getTerritory(i).getName();
			int cntIUnits = BoardProvider.getBoard().getTerritory(i).getUnits().size();
			Output.write(TerritoryIName + " (P: " + playerIName + ", U: " + cntIUnits + ") -> ");

			//todo: should be (player p, x units)
			for (int j = 0; j < size; ++j) {
				if (neighbourhoodMatrix[i][j]) {
					String playerJName = BoardProvider.getBoard().getTerritory(j).getOwner().getName();
					String TerritoryJName = BoardProvider.getBoard().getTerritory(j).getName();
					int cntJUnits = BoardProvider.getBoard().getTerritory(j).getUnits().size();
					Output.write(TerritoryJName + " (P: " + playerJName + ", U: " + cntJUnits + "), ");
					//todo: should be (player p, x units)
				}
			}
			Output.writeln("");
		}
	}
};