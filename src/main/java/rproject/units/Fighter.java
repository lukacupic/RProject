package rproject.units;

public class Fighter extends Unit {

	private static final int DEFAULT_HP = 100;

	private static final int DEFAULT_DMG = 10;

	private static final int DEFAULT_PRICE = 1;

	public Fighter() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
		price = DEFAULT_PRICE;
	}

	public Fighter(int hp, int damage) {
		super(hp, damage);
	}

	@Override
	public boolean attack(Unit unit) {
		unit.removeHp(damage);
		return unit.hp == 0;
	}

	@Override
	public void resetHp() {
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Fighter(hp, damage);
	}

	@Override
	public String getName() {
		return "Fighter";
	}
}
