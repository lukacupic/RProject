package rproject;

import java.util.List;

public class Player {

	private String name;

	private List<Territory> territories;

	private int noOfUnits;

	private int bonus;

	public Player(String name) {

	}

	public void setTerritories(List<Territory> territories) {
		this.territories = territories;
	}
}
