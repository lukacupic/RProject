package rproject.utils;

import rproject.engine.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Provides methods for some general common tasks.
 */
public class Util {

	/**
	 * An object for generating pseudo-random numbers.
	 */
	private static final Random rand = new Random();

	/**
	 * Returns a pseudo-random, uniformly distributed integer
	 * from the interval [low, high].
	 *
	 * @param low  the lower bound of the interval
	 * @param high the upper bound of the interval
	 * @return a pseudo-random integer from the interval [low, high]
	 */
	public static int getRandInt(int low, int high) {
		return rand.nextInt(high - low) + low;
	}

	/**
	 * Returns a pseudo-random, uniformly distributed integer
	 * from the interval [0, bound].
	 *
	 * @param bound the upper bound of the interval
	 * @return a pseudo-random integer from the interval [0, bound]
	 */
	public static int getRandInt(int bound) {
		return getRandInt(0, bound);
	}

	/**
	 * Returns the longest string from the given list of strings.
	 *
	 * @param strings the list from which to return the longest string
	 * @return the longest string from the given list
	 */
	public static String getLongestString(List<String> strings) {
		return Collections.max(strings, Comparator.comparing(String::length));
	}

	/**
	 * Tests if event P with given probability successChance happens.
	 *
	 * For example, if successChance equals 70, there is 70% chance that the method will
	 * return true, and 30% chance that it will return false.
	 *
	 * @param 	successChance
	 * 			chance of success, in percentage
	 * @return	(successChance)% of returning true, (100 - successChance)% of returning false
	 */
	public static boolean testChance(int successChance){
		return Util.getRandInt(100) >= successChance;
	}

	/**
	 * Extracts and returns the names of the given players.
	 *
	 * @param players the players whose names to return
	 * @return a list of names of the given players
	 */
	public static List<String> getPlayerNames(List<Player> players) {
		List<String> playerNames = new ArrayList<>();
		for (Player player : players) {
			playerNames.add(player.getName());
		}
		return playerNames;
	}
}
