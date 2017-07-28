package rproject.units;

public class Tower extends Unit {

	/**
	 * Default stats for every tower
	 */
	private static final int DEFAULT_HP = 1000;
	private static final int DEFAULT_DMG = 50;
	private static final int DEFAULT_PRICE = 15;
	private static final int DEFAULT_HIT_CHANCE = 75;
	private static final int DEFAULT_ARMOR = 30;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 130;

	/**
	 * Tower isn't movable. Once placed, it stays there until destroyed
	 */
	private static final boolean DEFAULT_MOVABLE = false;

	/**
	 * Constructor
	 */
	public Tower() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		hitChance = DEFAULT_HIT_CHANCE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
		movable = DEFAULT_MOVABLE;
		name = "Tower";
	}

	@Override
	public void resetHp() {
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Archer();
	}
}
