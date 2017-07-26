package rproject.units;

public class Archer extends Unit {

	/**
	 * Default stats for every archer
	 */
 	private static final int DEFAULT_HP = 60;
	private static final int DEFAULT_DMG = 35;
	private static final int DEFAULT_PRICE = 2;
	private static final int DEFAULT_ARMOR = 0;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 35;

	/**
	 * Constructor
	 */
	public Archer() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
		name = "Archer";
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
