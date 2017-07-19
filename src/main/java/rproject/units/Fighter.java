package rproject.units;

public class Fighter extends Unit {

	public Fighter() {
		hp = 100;
		damage = 10;
	}

	@Override
	public boolean attack(Unit unit) {
		unit.removeHp(damage);
		return unit.hp == 0;
	}
}
