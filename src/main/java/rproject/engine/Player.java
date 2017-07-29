package rproject.engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {

	/**
	 * Name of the player
	 */
	private String name;

	/**
	 * The list of territories occupied by the player
	 */
	private List<Territory> territories;

	/**
	 * Total number of units owned by player
	 */
	private int cntUnits;

	/**
	 * Number of bonuses owned by player
	 */
	private int cntBonus;

	/**
	 * Number of gold owned by player
	 */
	private int cntGold;

	/**
	 * Color of the player
	 */
	private Color color;

	/**
	 * Constructor
	 *
	 * @param name  name of the player
	 * @param color color of the player
	 */
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		territories = new ArrayList<>();
	}

	/**
	 * Adds gold to the player
	 *
	 * @param n amount of gold added
	 */
	public void addGold(int n) {
		cntGold += n;
	}

	/**
	 * Removes gold from the player
	 *
	 * @param n amount of gold removed
	 */
	public void removeGold(int n) {
		addGold(-n);
	}

	/**
	 * Checks if the player is alive
	 *
	 * @return true if the player is alive, false othervise
	 */
	public boolean isAlive() {
		return this.territories.size() > 0;
	}

	/**
	 * Increases count of the units
	 *
	 * @param n amount of units added
	 */
	private void addUnits(int n) {
		cntUnits += n;
	}

	/**
	 * Adds bonuses to the player
	 *
	 * @param n amount of bonuses added
	 */
	public void addBonus(int n) {
		cntBonus += n;
	}

	/**
	 * Adds territory to the player
	 *
	 * @param T added territory
	 */
	public void addTerritory(Territory T) {
		territories.add(T);
		T.setOwner(this);
		addUnits(T.countAllUnits());
	}

	/**
	 * Removes territory from the player
	 *
	 * @param T removed territory
	 */
	public void removeTerritory(Territory T) {
		territories.remove(T);
		addUnits(-T.countAllUnits());
	}

	/**
	 * Returns the list of all territories owned by player
	 *
	 * @return the list of all territories owned by player
	 */
	public List<Territory> getTerritories() {
		return territories;
	}

	/**
	 * Returns how many gold the player owns
	 *
	 * @return how many gold the player owns
	 */
	public int getGold() {
		return cntGold;
	}

	/**
	 * Returns player's color
	 *
	 * @return player's color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns number of player's bonuses
	 *
	 * @return number of player's bonuses
	 */
	public int getCntBonus() {
		return cntBonus;
	}

	/**
	 * returns the name of the player
	 *
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns number of player's units
	 *
	 * @return number of player's units
	 */
	public int getCntUnits() {
		return cntUnits;
	}

}
