package rproject.engine;

import rproject.units.Fighter;
import rproject.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class Territory {

	private String name;

	private int index;

	private Player owner;

	private List<Unit> units;

	private static int territoryCount;

	public Territory(String name) {
		units = new ArrayList<>();
		this.name = name;
		this.index = territoryCount++;
		this.addUnit(new Fighter());
	}

	public void changeOwner(Player newOwner) {
		owner.removeTerritory(this);
		newOwner.addTerritory(this);
		setOwner(newOwner);
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		// todo: color territory, smt like
		// setTerritoryColor(this.name, owner.getColor());
	}

	public void addUnit(Unit U) {
		this.units.add(U);
	}

	public void addUnits(List<Unit> units) {
		for (Unit unit : units)
			this.addUnit(unit);
	}

	/* todo:
	public void addMultipleUnits(Unit U, int n){
		for (int i = 0; i < n; ++i) {
			this.addUnit(U.clone());
		}
	}*/

	public boolean removeUnit(Unit U) {
		return this.units.remove(U);
	}

	public void removeUnits(List<Unit> units) {
		for (Unit unit : units)
			this.removeUnit(unit);
	}

	public void moveUnits(Territory endingTerritory, List<Unit> movingUnits) {
		for (Unit unit : movingUnits)
			if (this.removeUnit(unit))
				endingTerritory.addUnit(unit);
	}

	public List<Unit> getUnits() {
		return units;
	}

	public int getNumberOfUnits(String name) {
		int count = 0;
		for (Unit u : units) {
			if (u.getName().equals(name)) {
				count++;
			}
		}
		return count;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public int getCntUnits() {
		return units.size();
	}

}
