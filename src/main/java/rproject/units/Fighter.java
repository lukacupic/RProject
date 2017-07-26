package rproject.units;

public class Fighter extends Unit {

	private static final int DEFAULT_HP = 100;
	private static final int DEFAULT_DMG = 15;
	private static final int DEFAULT_PRICE = 1;
	private static final int DEFAULT_ARMOR = 2;
	private static final int DEFAULT_TARGETED_CHANCE_COEF = 100;

	public Fighter() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
		targetChanceCoef = DEFAULT_TARGETED_CHANCE_COEF;
		armor = DEFAULT_ARMOR;
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
