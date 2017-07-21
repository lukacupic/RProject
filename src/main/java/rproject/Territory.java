package rproject;

import rproject.units.Fighter;
import rproject.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class Territory {

	private String name;

	private int index;

	private Player owner;

	private List<Unit> Units;

	private static int territoryCount;

	public Territory(String name) {
		Units = new ArrayList<>();
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
	}

	public void addUnit(Unit U) {
		this.Units.add(U);
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
		return this.Units.remove(U);
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
		return Units;
	}

	public void setUnits(List<Unit> units) {
		Units = units;
	}

	public int getCntUnits() {
		return Units.size();
	}

}
