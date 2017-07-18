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
	private int cntExtraUnits;

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
		T.setOwner(this);
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

	private boolean attackPhase() {
		boolean getBonus = false;
		System.out.print("attack? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.print("attack from a to b using n units, write using format: a b n\n");
			String attTerritoryName = sc.next();
			Territory attTerritory = board.getTerritory(attTerritoryName);
			String defTerritoryName = sc.next();
			Territory defTerritory = board.getTerritory(defTerritoryName);
			int cntAttUnits = sc.nextInt();
			if (checkValidAttack(attTerritory, defTerritory, cntAttUnits))
				if(attack(attTerritory, defTerritory, cntAttUnits)){
					getBonus = true;
					defTerritory.changeOwner(this);
				}
			System.out.print("attack again? y/n");
			response = sc.next().charAt(0);
		}
		return getBonus;
	}

	private boolean checkValidMoving(Territory startingTerritory, Territory endingTerritory, int cntMovingUnits){
		if (startingTerritory == null){
			System.out.println("invalid name of att territory");
			return false;
		}
		if (endingTerritory == null){
			System.out.println("invalid name of def territory");
			return false;
		}
		if (!startingTerritory.getOwner().getName().equals(this.getName())){
			System.out.println("starting territory doesn't belong to you");
			return false;
		}
		if (!endingTerritory.getOwner().getName().equals(this.getName())){
			System.out.println("ending territory doesn't belong to you");
			return false;
		}
		if (startingTerritory.getUnits() <= cntMovingUnits) {
			System.out.println("too many units");
			return false;
		}
		return true;
	}

	private void movePhase(){
		System.out.print("move units? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.print("move n units from a to b, write using format: a b n\n");
			String startingTerritoryName = sc.next();
			Territory startingTerritory = board.getTerritory(startingTerritoryName);
			String endingTerritoryName = sc.next();
			Territory endingTerritory = board.getTerritory(endingTerritoryName);
			int cntMovingUnits = sc.nextInt();
			if (checkValidMoving(startingTerritory, endingTerritory, cntMovingUnits)){
				startingTerritory.moveUnits(endingTerritory, cntMovingUnits);
			}
			System.out.print("move again? y/n");
			response = sc.next().charAt(0);
		}
	}

	private int getSpawnCount(){
		return 3; // todo
	}

	private boolean checkValidSpawning(Territory spawnTerritory, int cntSpawnUnits){
		if (spawnTerritory == null){
			System.out.println("invalid name of the territory");
			return false;
		}
		if (!spawnTerritory.getOwner().getName().equals(this.getName())){
			System.out.println("territory doesn't belong to you");
			return false;
		}
		if (cntSpawnUnits > cntExtraUnits) {
			System.out.println("too many units");
			return false;
		}
		return true;
	}

	private void spawnPhase(){
		cntExtraUnits += getSpawnCount();
		System.out.print("spawn units? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.printf("number of available units: %d\n",cntExtraUnits);
			System.out.print("spawn n units on territory a, write using format: a n\n");
			String spawnTerritoryName = sc.next();
			Territory spawnTerritory = board.getTerritory(spawnTerritoryName);
			int cntSpawningUnits = sc.nextInt();
			if (checkValidSpawning(spawnTerritory, cntSpawningUnits)){
				spawnTerritory.addUnits(cntSpawningUnits);
			}
			System.out.print("spawn again? y/n");
			response = sc.next().charAt(0);
		}
	}

/*	players.get(i).placePhase();
	boolean getBonus = players.get(i).attackPhase();
	players.get(i).movePhase();
	if (getBonus) players.get(i).getBonus();*/

}
