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

	public boolean attackPhase() {
		boolean getBonus = false;
		System.out.println("attack? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.println("attack from a to b using n units, write using format: a b n");
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
			System.out.println("attack again? y/n");
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

	public void movePhase(){
		System.out.println("move units? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.println("move n units from a to b, write using format: a b n");
			String startingTerritoryName = sc.next();
			Territory starting = board.getTerritory(startingTerritoryName);
			String endingTerritoryName = sc.next();
			Territory ending = board.getTerritory(endingTerritoryName);
			int cntMovingUnits = sc.nextInt();
			if (checkValidMoving(starting, ending, cntMovingUnits)){
				starting.moveUnits(ending, cntMovingUnits);
			}
			System.out.println("move again? y/n");
			response = sc.next().charAt(0);
		}
	}

	private int getSpawnCount(){
		return 3; // todo
	}

	private boolean checkValidSpawning(Territory spawn, int cntSpawnUnits){
		if (spawn == null){
			System.out.println("invalid name of the territory");
			return false;
		}
		if (!spawn.getOwner().getName().equals(this.getName())){
			System.out.println("territory doesn't belong to you");
			return false;
		}
		if (cntSpawnUnits > cntExtraUnits) {
			System.out.println("too many units");
			return false;
		}
		return true;
	}

	public void spawnPhase(){
		cntExtraUnits += getSpawnCount();
		System.out.println("spawn units? y/n");
		char response = sc.next().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			System.out.println("number of available units: " + cntExtraUnits);
			System.out.println("spawn n units on territory a, write using format: a n");
			String spawnTerritoryName = sc.next();
			Territory spawnTerritory = board.getTerritory(spawnTerritoryName);
			int cntSpawningUnits = sc.nextInt();
			if (checkValidSpawning(spawnTerritory, cntSpawningUnits)){
				spawnTerritory.addUnits(cntSpawningUnits);
			}
			System.out.println("spawn again? y/n");
			response = sc.next().charAt(0);
		}
	}

	public void bonusPhase(){
		cntBonus++;
	}
}
