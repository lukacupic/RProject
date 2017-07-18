package rproject.io;

import java.util.Scanner;

public class Input {
	private static Scanner sc = new Scanner(System.in);

	public static String readString() { return sc.next(); }

	public static int readInt() { return sc.nextInt(); }

	/**
	 * Returns a character token read from the user input. Each
	 * read character is returned in the lower-case format thus
	 * making user input case insensitive for easier processing.
	 *
	 * @return the next character from the user input
	 */
	public static char readChar() {
		return readString().charAt(0);
	}
}
