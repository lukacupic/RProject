package rproject.io;

import java.util.Scanner;

public class Input {
	static Scanner sc = new Scanner(System.in);

	public static String readString() {
		String input = sc.next();
		return input;
	}

	public static int readInt() {
		int input = sc.nextInt();
		return input;
	}

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
