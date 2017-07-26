package rproject.units;

public class Knight extends Unit {

	/**
	 * Default stats for every knight
	 */
	private static final int DEFAULT_HP = 150;
	private static final int DEFAULT_DMG = 40;
	private static final int DEFAULT_PRICE = 3;
	private static final int DEFAULT_ARMOR = 10;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 200;

	/**
	 * Constructor
	 */
	public Knight() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
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
