package rproject.io;

import java.util.Arrays;

public class Output {

	public static char PROMPT_SYMBOL = '>';

	public static boolean write(String str) { System.out.print(str); return true; }
	public static void writeln(String str) { System.out.println(str); }
	public static void write(int num) { System.out.print(num); }
	public static void writeln(int num) { System.out.println(num); }

	/**
	 * Writes a prompt symbol onto the output, indicating that a user
	 * interaction input is requested.
	 */
	public static void prompt() {
		write(PROMPT_SYMBOL + " ");
	}

	public static void newLine() { writeln(""); }
}
