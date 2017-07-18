package rproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

	private String name;
	private List<Territory> territories;
	private boolean alive;
	private int cntUnits;
	private int cntBonus;

	Scanner sc = new Scanner(System.in);

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

	public List<Territory> getTerritories() {
		return territories;
	}

	public boolean attackPhase(){
		boolean getBonus = false;
		System.out.print("attack? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.
			// show map



			System.out.print("attack again? y/n");
			response = sc.next().charAt(0);
		}
		return getBonus;
	}

/*	players.get(i).ressPhase();
	boolean getBonus = players.get(i).attackPhase();
	players.get(i).movePhase();
	if (getBonus) players.get(i).getBonus();*/

}
