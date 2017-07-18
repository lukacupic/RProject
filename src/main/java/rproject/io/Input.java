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
}
