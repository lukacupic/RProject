package rproject;

public class Territory {

	private String name;

	private int index;

	private Player owner;

	private int cntUnits;

	private int territoryCount;

	public Territory(String name, int index) {
		this.name = name;
		this.index = index;
		cntUnits = 1;
		territoryCount++;
	}

	public void changeOwner(Player newOwner){
		owner.removeTerritory(this);
		newOwner.addTerritory(this);
		setOwner(newOwner);
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public Player getOwner() { return owner; }
	public String getName() { return name; }
	public int getUnits() {
		return cntUnits;
	}
}
