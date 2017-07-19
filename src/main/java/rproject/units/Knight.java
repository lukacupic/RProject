package rproject.units;

public class Knight extends Unit {

	private static final int DEFAULT_HP = 150;

	private static final int DEFAULT_DMG = 30;

	public Knight() {
		hp = DEFAULT_HP;
		damage = DEFAULT_DMG;
	}

	public Knight(int hp, int damage) {
		super(hp, damage);
	}

	@Override
	public boolean attack(Unit unit) {
		unit.removeHp(damage);
		return unit.hp == 0;
	}

	@Override
	public void resetHp(){
		this.setHp(DEFAULT_HP);
	}

	@Override
	public Unit clone() {
		return new Knight(hp, damage);
	}

	@Override
	public String getName() {
		return "Knight";
	}
}
