package rproject.board;

import rproject.engine.Game;

/**
 * Provides an instance of the currently active board.
 * A board must be set by an active {@link Game} object
 * prior to obtaining the board.
 */
public class BoardProvider {

	/**
	 * The current board.
	 */
	private static Board board;

	/**
	 * Gets the currently board.
	 *
	 * @return the current board
	 */
	public static Board getBoard() {
		return board;
	}

	/**
	 * Sets the given board as the new board.
	 *
	 * @param board the board
	 */
	public static void setBoard(Board board) {
		BoardProvider.board = board;
	}
}
