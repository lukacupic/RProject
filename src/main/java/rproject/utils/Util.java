package rproject.utils;

import rproject.engine.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

	public static String getLongestString(List<String> strings) {
		return Collections.max(strings, Comparator.comparing(String::length));
	}

	public static List<String> getPlayerNames(List<Player> players) {
		List<String> playerNames = new ArrayList<>();
		for (Player player : players) {
			playerNames.add(player.getName());
		}
		return playerNames;
	}
}
