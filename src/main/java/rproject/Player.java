package rproject;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Territory> territories;
	private int cntUnits;
	private int cntBonus;
	private int cntGold;

	public int getGold() {
		return cntGold;
	}

	public void addGold(int n) {
		cntGold += n;
	}

	public void removeGold(int n) {
		addGold(-n);
	}

	public Player(String name) {
		this.name = name;
		territories = new ArrayList<>();
	}

	public boolean isAlive() {
		return this.territories.size() > 0;
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
