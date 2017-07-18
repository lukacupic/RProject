package rproject;

public class Territory {

	private String name;

	private Player owner;

	private int cntUnits;

	public Territory(String name) {
		cntUnits = 1;
		this.name = name;
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
