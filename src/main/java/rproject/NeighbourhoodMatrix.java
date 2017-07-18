package rproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NeighbourhoodMatrix {
	private int size;
	private boolean[][] neighbourhoodMatrix;

	public NeighbourhoodMatrix(String FileName) throws IOException {
		List<String> Lines = Files.readAllLines(Paths.get("maps/matrix/" + FileName + ".txt"));
		size = Integer.parseInt(Lines.get(0));
		neighbourhoodMatrix = new boolean[size][size];
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				neighbourhoodMatrix[i][j] = (Lines.get(i + 1).charAt(j) == '1');
	}

	public void drawMatrixCUI() throws Exception {
		for (int i = 0; i < size; ++i) {
			System.out.printf("%d (p?, x?) -> ", i);
			//todo: should be (player p, x units)
			for (int j = 0; j < size; ++j) {
				if (neighbourhoodMatrix[i][j]) {
					System.out.printf("%d (p?, x?), ", j);
					//todo: should be (player p, x units)
				}
				System.out.println();
			}
		}
	}
};