package rproject.units;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class Unit {

	protected int hp;

	protected int damage;

	protected int price;

	private static final List < Unit > allUnits = initAllUnits();

	private static List < Unit > initAllUnits(){
		List < Unit > allUnits;
		allUnits = new ArrayList<>();
		allUnits.add(new Fighter());
		allUnits.add(new Knight());
		return allUnits;
	}

	public Unit() {
	}

	public Unit(int hp, int damage) {
		this.hp = hp;
		this.damage = damage;
	}

	public static List < Unit > getAllUnits(){
		return allUnits;
	}

	public void addHp(int hp) {
		this.hp += hp;
	}

	public void removeHp(int hp) {
		this.hp -= hp;
		if (this.hp < 0) this.hp = 0;
	}

	public boolean isAlive() {
		return this.hp > 0;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int damage) {
		this.price = price;
	}

	public abstract boolean attack(Unit unit);

	public abstract Unit clone();

	public abstract void resetHp();

	public abstract String getName();
}
