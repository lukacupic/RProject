package rproject.engine;

import rproject.gui.CGBridge;
import rproject.units.Fighter;
import rproject.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Territory {

	/**
	 * Holds the total number of territories on the current
	 * board.
	 */
	private static int territoryCount;
	/**
	 * The name of the territory.
	 */
	private String name;
	/**
	 * Represents the ordinal number of this territory in
	 * respect to all other territories.
	 */
	private int index;
	/**
	 * The owner of the territory.
	 */
	private Player owner;
	/**
	 * A list of all units currently on the territory.
	 */
	private List<Unit> units;

	/**
	 * Creates a new territory of the given name.
	 *
	 * @param name the name of the territory
	 */
	public Territory(String name) {
		units = new ArrayList<>();
		this.name = name;
		this.index = territoryCount++;
		this.addUnit(new Fighter());
	}

	/**
	 * Sets the given player as the new owner of this territory.
	 *
	 * @param newOwner the new owner of the territory
	 */
	public void changeOwner(Player newOwner) {
		owner.removeTerritory(this);
		newOwner.addTerritory(this);
		setOwner(newOwner);
	}

	/**
	 * Gets the name of the territory.
	 *
	 * @return the name of the territory
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the index (i.e. theordinal number) of this territory.
	 *
	 * @return the index of this territory
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the owner of this territory.
	 *
	 * @return the owner of this territory.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Sets the given player as the owner of this territory.
	 *
	 * @param owner the player
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
		CGBridge.colorTerritory(this, owner.getColor());
	}

	/**
	 * Adds the given unit to this territory.
	 *
	 * @param u the unit to add to this territory
	 */
	public void addUnit(Unit u) {
		this.units.add(u);
	}

	/**
	 * Adds the given units to this territory.
	 *
	 * @param units the units to add to this territory
	 */
	public void addUnits(List<Unit> units) {
		for (Unit unit : units)
			this.addUnit(unit);
	}

	/**
	 * Adds {@code n} units of the given type.
	 *
	 * @param u the type of unit to add
	 * @param n the number of units to add
	 */
	public void addMultipleUnits(Unit u, int n) {
		for (int i = 0; i < n; ++i)
			this.addUnit(u.clone());
	}

	/**
	 * Removes the given unit from this territory.
	 *
	 * @param u the unit to remove
	 * @return if the territory contained the given unit
	 */
	public boolean removeUnit(Unit u) {
		return this.units.remove(u);
	}

	/**
	 * Removes the given units from this territory.
	 *
	 * @param units the units to remove
	 */
	public void removeUnits(List<Unit> units) {
		for (Unit unit : units)
			this.removeUnit(unit);
	}

	/**
	 * Moves the specified units from this territory to another one.
	 *
	 * @param endingTerritory the territory to move the units to
	 * @param movingUnits     the units to move
	 */
	public void moveUnits(Territory endingTerritory, List<Unit> movingUnits) {
		for (Unit unit : movingUnits)
			if (this.removeUnit(unit))
				endingTerritory.addUnit(unit);
	}

	/**
	 * Returns the number of units on this territory, specified by
	 * the given name.
	 * <p>
	 * For example, if one is to get the number of all "Knight" units
	 * on this territory, one would make the following method call:
	 * {@code countSpecificUnits("Knight");}
	 *
	 * @param name the name of the units to count
	 * @return the number of units specified by the given name
	 */
	public int countSpecificUnits(String name) {
		return getUnitsCount().get(name);
	}

	/**
	 * Returns a map which stores the number of occurrences of each
	 * type of unit. The keys are all types of units (e.g. "Knight",
	 * "Archer" etc.) and values are occurrences of each of the unit
	 * types.
	 *
	 * @return a map which stores the number of occurrences of each
	 * type of unit
	 */
	public Map<String, Integer> getUnitsCount() {
		Map<String, Integer> unitsCount = new HashMap<>();

		for (Unit u : Unit.getAllUnits()) unitsCount.put(u.getName(), 0);
		for (Unit unit : units)
			for (Unit u : Unit.getAllUnits())
				if (unit.getClass().equals(u.getClass()))
					unitsCount.merge(unit.getName(), 1, (a, b) -> a + b);
		return unitsCount;
	}

	/**
	 * Returns units which are currently on this territory.
	 *
	 * @return a list of units currently on this territory
	 */
	public List<Unit> getUnits() {
		return units;
	}

	/**
	 * Places the given units on this territory. If the territory
	 * is not empty, the existing units will be replaced.
	 *
	 * @param units a list of units to place on this territory
	 */
	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	/**
	 * Gets the number of all units on this territory.
	 *
	 * @return the number of all units on this territory
	 */
	public int countAllUnits() {
		return units.size();
	}
}
