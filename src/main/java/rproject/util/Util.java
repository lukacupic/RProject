package rproject.util;

import java.util.Random;

public class Util {

	private final static Random rand = new Random();

	public int getRandInt(int low, int high) {
		return rand.nextInt(high - low) + low;
	}

	public int getRandInt(int high) {
		return getRandInt(0, high);
	}
}
