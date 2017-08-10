package rproject.units;

public class Fighter extends Unit {

	/**
	 * Default stats for every fighter
	 */
	private static final int DEFAULT_HP = 100;
	private static final int DEFAULT_DMG = 15;
	private static final int DEFAULT_PRICE = 1;
	private static final int DEFAULT_HIT_CHANCE = 65;
	private static final int DEFAULT_ARMOR = 25;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 200;
	private static final boolean DEFAULT_MOVABLE = true;

	/**
	 * Constructor
	 */
	public Fighter() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		hitChance = DEFAULT_HIT_CHANCE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
		movable = DEFAULT_MOVABLE;
		name = "Fighter";
	}

	@Override
	public void resetHp() {
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Fighter();
	}
}
