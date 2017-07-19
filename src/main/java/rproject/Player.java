package rproject;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Territory> territories;
	private int cntUnits;
	private int cntBonus;
	private int cntExtraUnits;

	public int getCntExtraUnits() {
		return cntExtraUnits;
	}

	public void addExtraUnits(int n) {
		cntExtraUnits += n;
	}

	public Player(String name) {
		this.name = name;
		territories = new ArrayList<Territory>();
	}

	public boolean isAlive() {
		return this.getCntUnits() > 0;
	}

	public String getName() {
		return name;
	}

	public int getCntUnits() {
		return cntUnits;
	}

	public int getCntBonus() {
		return cntBonus;
	}

	public void addUnits(int n) {
		cntUnits += n;
	}

	public void addBonus(int n) {
		cntBonus += n;
	}

	public void addTerritory(Territory T) {
		territories.add(T);
		T.setOwner(this);
		addUnits(T.getCntUnits());
	}

	public void removeTerritory(Territory T) {
		territories.remove(T);
		addUnits(-T.getCntUnits());
	}

	public List<Territory> getTerritories() {
		return territories;
	}

}
