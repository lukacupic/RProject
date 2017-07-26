package rproject.engine;

/**
 * Provides access to the instance of the currently active game.
 * A game must be set by the game initializer prior to accessing
 * the game.
 */
public class GameProvider {

	/**
	 * Currently active game.
	 */
	private static Game game;

	/**
	 * Gets the current game.
	 *
	 * @return the current game
	 */
	public static Game getGame() {
		return game;
	}

	/**
	 * Sets the given game as the active game.
	 *
	 * @param game the game
	 */
	public static void setGame(Game game) {
		GameProvider.game = game;
	}
}
