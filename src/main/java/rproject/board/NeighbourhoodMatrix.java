package rproject.board;

import rproject.files.FileUtil;
import rproject.io.Output;

import java.util.List;

public class NeighbourhoodMatrix {

	private int size;

	private boolean[][] matrix;

	public NeighbourhoodMatrix(String name) {
		List<String> lines = FileUtil.readMatrix(name);

		size = Integer.parseInt(lines.get(0));
		matrix = new boolean[size][size];
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				matrix[i][j] = (lines.get(i + 1).charAt(j) == '1');
	}

	public boolean checkNeighbours(int from, int to) {
		return matrix[from][to];
	}

	public void drawMatrixCUI() {
		for (int i = 0; i < size; ++i) {
			String playerIName = BoardProvider.getBoard().getTerritory(i).getOwner().getName();
			String TerritoryIName = BoardProvider.getBoard().getTerritory(i).getName();
			int cntIUnits = BoardProvider.getBoard().getTerritory(i).getUnits().size();
			Output.write(TerritoryIName + " (P: " + playerIName + ", U: " + cntIUnits + ") -> ");

			for (int j = 0; j < size; ++j) {
				if (matrix[i][j]) {
					String playerJName = BoardProvider.getBoard().getTerritory(j).getOwner().getName();
					String TerritoryJName = BoardProvider.getBoard().getTerritory(j).getName();
					int cntJUnits = BoardProvider.getBoard().getTerritory(j).getUnits().size();
					Output.write(TerritoryJName + " (P: " + playerJName + ", U: " + cntJUnits + "), ");
				}
			}
			Output.writeln("");
		}
	}
};