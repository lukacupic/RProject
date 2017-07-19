package rproject.units;

public class Unit {

	protected int hp;

	protected int damage;

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

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
