package rproject.board;

/**
 *
 */
public class BoardProvider {

	private static Board board;

	public static Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		BoardProvider.board = board;
	}
}
