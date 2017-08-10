package rproject.units;

public class Catapult extends Unit {

	/**
	 * Default stats for every catapult
	 */
	private static final int DEFAULT_HP = 1000;
	private static final int DEFAULT_DMG = 100;
	private static final int DEFAULT_PRICE = 15;
	private static final int DEFAULT_HIT_CHANCE = 40;
	private static final int DEFAULT_ARMOR = 50;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 125;
	private static final boolean DEFAULT_MOVABLE = true;

	/**
	 * Constructor
	 */
	public Catapult() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		hitChance = DEFAULT_HIT_CHANCE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
		movable = DEFAULT_MOVABLE;
		name = "Catapult";
	}

	@Override
	public void resetHp() {
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Catapult();
	}
}
