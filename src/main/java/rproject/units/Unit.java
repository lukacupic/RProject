package rproject.units;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

	/**
	 * Hp of the unit
	 */
	protected int hp;

	/**
	 * Damage of the unit
	 */
	protected int damage;

	/**
	 * Price of the unit
	 */

	protected int price;

	/**
	 * Target chance coefficient of unit (or simply "coef").
	 * Chance of being attacked equals coef divided by the sum of
	 * coefs of all units in that army
	 *
	 * for example, if there are units A and B, with coefs 300 and 100 respectively,
	 * chance of A being attacked equals 300 / (300 + 100) = 75%
	 * chance of B being attacked equals 100 / (300 + 100) = 25%
	 */
	protected int targetChanceCoef;

	/**
	 * Armor of unit. Reduces damage taken, damage dealt equals damage of attacker
	 * reduced by armor of defender
	 *
	 * for example, if unit A has damage 20 and unit B has armor 15,
	 * when A attacks B, only (20 - 15) = 5 damage will be dealt
	 */
	protected int armor;

	/**
	 * name of unit ("Knight", "Archer"...)
	 */
	protected String name;

	/**
	 * list of all units
	 */
	private static final List < Unit > allUnits = initAllUnits();

	/**
	 * Initializes the list of all units
	 *
	 * @return the list of all units
	 */
	private static List < Unit > initAllUnits(){
		List < Unit > allUnits;
		allUnits = new ArrayList<>();
		allUnits.add(new Fighter());
		allUnits.add(new Knight());
		allUnits.add(new Archer());
		return allUnits;
	}

	/**
	 * Default constructor
	 */
	public Unit() {
	}

	/**
	 * returns the list of all units
	 */
	public static List < Unit > getAllUnits(){
		return allUnits;
	}

	/**
	 * Adds hp to the unit
	 *
	 * @param 	hp
	 * 			amount of hp added
	 */
	public void addHp(int hp) {
		this.hp += hp;
	}

	/**
	 * Removes hp from unit
	 *
	 * @param 	hp
	 * 			amount of hp removed
	 */
	public void removeHp(int hp) {
		this.hp -= hp;
		if (this.hp < 0) this.hp = 0;
	}

	/**
	 * Returns aliveness of the unit
	 *
	 * @return true if unit is alive, false if it's not alive
	 */
	public boolean isAlive() {
		return this.hp > 0;
	}

	/**
	 * Returns hp of the unit
	 *
	 * @return hp of the unit
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Sets hp of the unit
	 *
	 * @param	hp
	 * 			value to which hp is set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * Returns damage of the unit
	 *
	 * @return damage of the unit
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Sets damage of the unit
	 *
	 * @param	damage
	 * 			value to which damage is set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Price of the unit
	 *
	 * @return price of the unit
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * Sets price of the unit
	 *
	 * @param	price
	 * 			value to which damage is set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Returns target chance coefficient of the unit
	 *
	 * @return target chance coefficient of the unit
	 */
	public int getTargetChanceCoef() {
		return targetChanceCoef;
	}

	/**
	 * Returns armor of the unit
	 *
	 * @return armor of the unit
	 */
	public int getArmor() {
		return armor;
	}

	/**
	 * Attacks other unit
	 *
	 * @param	unit
	 *			unit which is attacked
	 *
	 * @return true if attacked unit is killed in the attack, false otherwise
	 */
	public boolean attack(Unit unit){
		int damageDealt = damage - unit.getArmor();
		if (damageDealt < 0) damageDealt = 0;
		unit.removeHp(damageDealt);
		return unit.hp == 0;
	}

	/**
	 * Returns cloned unit
	 *
	 * @return cloned unit
	 */
	public abstract Unit clone();

	/**
	 * Resets hp
	 */
	public abstract void resetHp();

	/**
	 * Returns name of the unit
	 *
	 * @return name of the unit
	 */
	public String getName(){
		return name;
	};

	/**
	 * Checks if units are equal
	 *
	 * @param	o
	 * 			we check if o equals this unit
	 * @return true if they are equal, false otherwise
	 */
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

	/**
	 * Returns hash of the unit
	 *
	 * @return hash of the unit
	 */
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
