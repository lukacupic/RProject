package rproject.units;

public class Knight extends Unit {

	/**
	 * Default stats for every knight
	 */
	private static final int DEFAULT_HP = 250;
	private static final int DEFAULT_DMG = 45;
	private static final int DEFAULT_PRICE = 5;
	private static final int DEFAULT_HIT_CHANCE = 75;
	private static final int DEFAULT_ARMOR = 40;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 100;
	private static final boolean DEFAULT_MOVABLE = true;

	/**
	 * Constructor
	 */
	public Knight() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		hitChance = DEFAULT_HIT_CHANCE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
		movable = DEFAULT_MOVABLE;
		name = "Knight";
	}

	@Override
	public void resetHp() {
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Knight();
	}
}
