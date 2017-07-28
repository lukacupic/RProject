package rproject.gui.map.components;

import processing.core.PApplet;
import rproject.engine.GameProvider;
import rproject.engine.Player;
import rproject.utils.GUIUtil;
import rproject.utils.Util;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A legend which displays currently active players, along with their
 * corresponding colors, onto the map.
 */
public class Legend {

	/**
	 * The PApplet on which to draw the legend on.
	 */
	private PApplet p;

	/**
	 * The list of all players in the game.
	 */
	private List<Player> players;

	/**
	 * The x coordinate of the top-left corner of the legend.
	 */
	private int x;

	/**
	 * The y coordinate of the top-left corner of the legend.
	 */
	private int y;

	/**
	 * The width of the legend.
	 */
	private int width;

	/**
	 * The height of the legend.
	 */
	private int height;

	/**
	 * The width of the longest name displayed on the legend.
	 */
	private int textWidth;

	/**
	 * The height of the names displayed on the legend.
	 */
	private int textHeight;

	/**
	 * Represents both the width and the height of the bullet.
	 * In other words, it represents the bullet's diameter.
	 */
	private int bulletSize;

	/**
	 * The distance to both the left and the bottom borders of
	 * the PApplet.
	 */
	private int cornerDist = 35;

	/**
	 * The horizontal spacing between each of the items.
	 * Also, salute to Elon Musk.
	 */
	private int spaceX = 7;

	/**
	 * The vertical spacing between each of the items.
	 */
	private int spaceY = 0;


	/**
	 * Creates a new legend to display onto the given PApplet.
	 *
	 * @param p the PApplet on which to draw the legend on
	 */
	public Legend(PApplet p) {
		this.p = p;
		// don't call init() here because the Game has not been setup yet
	}

	/**
	 * Updates the list currently active players and initializes the
	 * graphical properties.
	 */
	public void init() {
		// fetch the players and keep only those who are still alive
		players = GameProvider.getGame().getPlayers().stream()
				.filter(Player::isAlive).collect(Collectors.toList());

		int noOfPlayers = players.size();

		textWidth = (int) p.textWidth(Util.getLongestString(Util.getPlayerNames(players))) + spaceX;
		textHeight = (int) (p.textAscent() + p.textDescent());

		bulletSize = textHeight / 2;

		width = textWidth + 3 * spaceX + bulletSize;
		height = textHeight * noOfPlayers + spaceY * (noOfPlayers - 1);

		x = cornerDist;
		y = p.height - cornerDist - height;
	}

	/**
	 * Draws the list of currently active players onto the PApplet.
	 */
	public void draw() {
		// To optimize performance, the init() method should be triggered
		// by someone to indicate that the list of active players has been
		// changed, and then, the following comments can be removed

		// if (players == null) {
		init();
		// }

		p.fill(GUIUtil.colorToInt(new Color(69, 69, 69)), 200); // set legend color
		p.rect(x - 10, y, width, height); // draw the legend

		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);

			int textX = x + 2 * spaceX + bulletSize;
			int textY = y + (i + 1) * spaceY + i * textHeight;

			int ellipseX = textX - spaceX - bulletSize;
			int ellipseY = textY + textHeight / 2;

			p.fill(GUIUtil.colorToInt(player.getColor()));
			p.ellipse(ellipseX, ellipseY, bulletSize, bulletSize);

			p.fill(GUIUtil.colorToInt(Color.BLACK));
			p.text(player.getName(), textX, textY, textWidth, textHeight);
		}
	}
}
