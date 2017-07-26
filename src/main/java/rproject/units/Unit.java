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

	protected int targetChanceCoef;

	protected int armor;

	protected String name;

	private static final List < Unit > allUnits = initAllUnits();

	private static List < Unit > initAllUnits(){
		List < Unit > allUnits;
		allUnits = new ArrayList<>();
		allUnits.add(new Fighter());
		allUnits.add(new Knight());
		allUnits.add(new Archer());
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

	public int getTargetChanceCoef() {
		return targetChanceCoef;
	}

	public int getArmor() {
		return armor;
	}

	public boolean attack(Unit unit){
		int damageDealt = damage - unit.getArmor();
		if (damageDealt < 0) damageDealt = 0;
		unit.removeHp(damageDealt);
		return unit.hp == 0;
	}

	public abstract Unit clone();

	public abstract void resetHp();

	public String getName(){
		return name;
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Unit unit = (Unit) o;

		if (hp != unit.hp) return false;
		if (damage != unit.damage) return false;
		if (price != unit.price) return false;
		if (targetChanceCoef != unit.targetChanceCoef) return false;
		if (armor != unit.armor) return false;
		return name != null ? name.equals(unit.name) : unit.name == null;
	}

	@Override
	public int hashCode() {
		int result = hp;
		result = 31 * result + damage;
		result = 31 * result + price;
		result = 31 * result + targetChanceCoef;
		result = 31 * result + armor;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
