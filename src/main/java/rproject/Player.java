package rproject;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Territory> territories;
	private boolean alive;
	private int cntUnits;
	private int cntBonus;

	public Player(String name) {
		this.name = name;
		territories = new ArrayList<Territory>();
	}

	public boolean isAlive() {
		return alive;
	}

	public String getName() {
		return name;
	}

	public int getCntUnit() {
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
		addUnits(T.getUnits());
	}

	public void removeTerritory(Territory T) {
		territories.remove(T);
		addUnits(-T.getUnits());
	}

	public List<Territory> getTeritories() {
		return territories;
	}
}
