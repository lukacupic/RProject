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
		this.alive = true;
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

	private boolean checkValidAttack(Territory attTerritory, Territory defTerritory, int cntAttUnits){
		if (attTerritory == null){
			System.out.println("invalid name of att territory");
			return false;
		}
		if (defTerritory == null){
			System.out.println("invalid name of def territory");
			return false;
		}
		if (!attTerritory.getOwner().getName().equals(this.getName())){
			System.out.println("you can attack only from your territories");
			return false;
		}
		Board board = BoardProvider.getBoard();
		if (!board.getMatrix().checkNeighbours(attTerritory.getIndex(),defTerritory.getIndex())){
			System.out.println("territories aren't connected");
			return false;
		}
		if (attTerritory.getOwner().getName().equals(defTerritory.getOwner().getName())){
			System.out.println("you cant attack yourself");
			return false;
		}
		if (attTerritory.getUnits() <= cntAttUnits) {
			System.out.println("too many units");
			return false;
		}
		return true;
	}

	private boolean attack(Territory attTerritory, Territory defTerritory, int cntAttUnits){

		return cntAttUnits > defTerritory.getUnits();
	}

	public boolean attackPhase() {
		boolean getBonus = false;
		System.out.print("attack? y/n");
		char response = sc.next().charAt(0);
		for(;response == 'y';){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.print("attack from a to b using n units, write using format: a b n\n");
			String attTerritoryName = sc.next();
			String defTerritoryName = sc.next();
			Territory attTerritory = board.getTerritory(attTerritoryName);
			Territory defTerritory = board.getTerritory(defTerritoryName);
			int cntAttUnits = sc.nextInt();
			if (checkValidAttack(attTerritory, defTerritory, cntAttUnits))
				if(attack(attTerritory, defTerritory, cntAttUnits)) getBonus = true;
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
