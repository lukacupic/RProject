package rproject.units;

public class Centurion extends Unit {

	/**
	 * Default stats for every centurion
	 */
	private static final int DEFAULT_HP = 250;
	private static final int DEFAULT_DMG = 55;
	private static final int DEFAULT_PRICE = 5;
	private static final int DEFAULT_HIT_CHANCE = 75;
	private static final int DEFAULT_ARMOR = 10;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 200;
	private static final boolean DEFAULT_MOVABLE = true;

	/**
	 * Constructor
	 */
	public Centurion() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		hitChance = DEFAULT_HIT_CHANCE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
		movable = DEFAULT_MOVABLE;
		name = "Centurion";
	}

	@Override
	public void resetHp() {
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Centurion();
	}
}
