package rproject.utils;

import java.util.Random;

/**
 *
 */
public class Util {

	private static final Random rand = new Random();

	public static int getRandInt(int low, int high) {
		return rand.nextInt(high - low) + low;
	}

	public static int getRandInt(int high) {
		return getRandInt(0, high);
	}
}
