package rproject;

import rproject.io.Input;
import rproject.io.Output;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Territory> territories;
	private boolean alive;
	private int cntUnits;
	private int cntBonus;
	private int cntExtraUnits;

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
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (defTerritory == null){
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!attTerritory.getOwner().getName().equals(this.getName())){
			Output.writeln("you can attack only from your territories");
			return false;
		}
		Board board = BoardProvider.getBoard();
		if (!board.getMatrix().checkNeighbours(attTerritory.getIndex(),defTerritory.getIndex())){
			Output.writeln("territories aren't connected");
			return false;
		}
		if (attTerritory.getOwner().getName().equals(defTerritory.getOwner().getName())){
			Output.writeln("you cant attack yourself");
			return false;
		}
		if (attTerritory.getUnits() <= cntAttUnits) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	private boolean attack(Territory attTerritory, Territory defTerritory, int cntAttUnits){
		return cntAttUnits > defTerritory.getUnits();
	}

	public boolean attackPhase() {
		boolean getBonus = false;
		Output.writeln("attack? y/n");
		char response = Input.readString().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Output.writeln("attack from a to b using n units, write using format: a b n");
			String attTerritoryName = Input.readString();
			Territory attTerritory = board.getTerritory(attTerritoryName);
			String defTerritoryName = Input.readString();
			Territory defTerritory = board.getTerritory(defTerritoryName);
			int cntAttUnits = Input.readInt();
			if (checkValidAttack(attTerritory, defTerritory, cntAttUnits))
				if(attack(attTerritory, defTerritory, cntAttUnits)){
					getBonus = true;
					defTerritory.changeOwner(this);
				}
			do {
				Output.writeln("attack again? y/n");
				response = Input.readString().charAt(0);
			} while(response != 'y' && response != 'n');
		}
		return getBonus;
	}

	private boolean checkValidMoving(Territory startingTerritory, Territory endingTerritory, int cntMovingUnits){
		if (startingTerritory == null){
			Output.writeln("invalid name of att territory");
			return false;
		}
		if (endingTerritory == null){
			Output.writeln("invalid name of def territory");
			return false;
		}
		if (!startingTerritory.getOwner().getName().equals(this.getName())){
			Output.writeln("starting territory doesn't belong to you");
			return false;
		}
		if (!endingTerritory.getOwner().getName().equals(this.getName())){
			Output.writeln("ending territory doesn't belong to you");
			return false;
		}
		if (startingTerritory.getUnits() <= cntMovingUnits) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	public void movePhase(){
		Output.writeln("move units? y/n");
		char response = Input.readString().charAt(0);
		while(response == 'y'){
			Board board = BoardProvider.getBoard();
			board.getMatrix().drawMatrixCUI();
			Output.writeln("move n units from a to b, write using format: a b n");
			String startingTerritoryName = Input.readString();
			Territory starting = board.getTerritory(startingTerritoryName);
			String endingTerritoryName = Input.readString();
			Territory ending = board.getTerritory(endingTerritoryName);
			int cntMovingUnits = Input.readInt();
			if (checkValidMoving(starting, ending, cntMovingUnits)){
				starting.moveUnits(ending, cntMovingUnits);
			}
			do {
				Output.writeln("move again? y/n");
				response = Input.readString().charAt(0);
			} while(response != 'y' && response != 'n');
		}
	}

	private int getSpawnCount(){
		return 3; // todo
	}

	private boolean checkValidSpawning(Territory spawn, int cntSpawnUnits){
		if (spawn == null){
			Output.writeln("invalid name of the territory");
			return false;
		}
		if (!spawn.getOwner().getName().equals(this.getName())){
			Output.writeln("territory doesn't belong to you");
			return false;
		}
		if (cntSpawnUnits > cntExtraUnits) {
			Output.writeln("too many units");
			return false;
		}
		return true;
	}

	public void spawnPhase(){
		cntExtraUnits += getSpawnCount();
		Output.writeln("spawn units? y/n");
		char response = Input.readString().charAt(0);
		Board board = BoardProvider.getBoard();
		board.getMatrix().drawMatrixCUI();
		while(response == 'y'){
			Output.writeln("number of available units: " + cntExtraUnits);
			Output.writeln("spawn n units on territory a, write using format: a n");
			String spawnTerritoryName = Input.readString();
			Territory spawnTerritory = board.getTerritory(spawnTerritoryName);
			int cntSpawningUnits = Input.readInt();
			if (checkValidSpawning(spawnTerritory, cntSpawningUnits)){
				spawnTerritory.addUnits(cntSpawningUnits);
			}
			do {
				Output.writeln("spawn again? y/n");
				response = Input.readString().charAt(0);
			} while(response != 'y' && response != 'n');
		}
	}

	public void bonusPhase(){
		cntBonus++;
	}
}
