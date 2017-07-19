package rproject.units;

public class Fighter extends Unit {

	public Fighter() {
		hp = 100;
		damage = 10;
	}

	@Override
	public void attack(Unit unit) {
		unit.removeHp(damage);
	}
}
