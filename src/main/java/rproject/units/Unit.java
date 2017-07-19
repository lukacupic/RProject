package rproject.units;

public class Unit {

	private int hp = 100;

	private int damage = 100;

	public void addHp(int hp) {
		this.hp += hp;
	}

	public void removeHp(int hp) {
		addHp(-hp);
	}

	public boolean isAlive() {
		return this.hp > 0;
	}

	public int getHp() {
		return hp;
	}

	public int getDamage() {
		return damage;
	}
}
