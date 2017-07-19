package rproject.util;

import java.util.Random;

public class Util {

	private final static Random rand = new Random();

	public int getRandInt(int start, int end) {
		return (Math.abs(rand.nextInt(end - start) + start));
	}

	public int getRandInt(int end) {
		return getRandInt(0, end);
	}
}
