package rproject.units;

public class Fighter extends Unit {

	private static final int DEFAULT_HP = 100;

	private static final int DEFAULT_DMG = 10;

	public Fighter() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
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
	public Unit clone() {
		return new Fighter(hp, damage);
	}

	@Override
	public String getName() {
		return "Fighter";
	}
}
