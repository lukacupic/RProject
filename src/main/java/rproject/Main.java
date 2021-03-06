package rproject;

import rproject.engine.Game;
import rproject.io.Input;
import rproject.io.Output;
import rproject.utils.FileUtil;

import java.util.List;

/**
 *
 */
public class Main {

	private static final int MIN_PLAYERS = 2;

	private static final int MAX_PLAYERS = 6;

	public static void main(String[] args) {
		Output.writeln("RProject v1.0 - Risk engine simulator");
		Output.writeln("**********************************");
		Output.newLine();

/*		Output.writeln("Enter the number of the map you wish to play:");
		String map = getSelectedMap();
		Output.newLine();

		Output.writeln("Enter the number of players [" + MIN_PLAYERS + ", " + MAX_PLAYERS + "]:");
		int playersCount = getPlayersCount();
		Output.newLine();

		String[] playerNames = getPlayerNames(playersCount);

		Game engine = new Game(map, playerNames);
		engine.start();*/

		Game game = new Game("continentsShort", new String[]{"asd", "qwe"});
		game.start();
	}

	private static String[] getPlayerNames(int playersCount) {
		String[] names = new String[playersCount];
		for (int i = 0; i < playersCount; i++) {
			Output.writeln("Enter the name of the " + (i + 1) + ". player:");
			Output.prompt();
			names[i] = Input.readString();
		}
		return names;
	}

	public static int getPlayersCount() {
		int input;
		do {
			Output.prompt();
			input = Input.readInt();
		} while ((input < MIN_PLAYERS || input > MAX_PLAYERS));
		return input;
	}

	private static String getSelectedMap() {
		List<String> maps = FileUtil.getMapsList();
		showMaps(maps);

		int input;
		do {
			Output.prompt();
			input = Input.readInt();
		} while (input < 1 || input > maps.size());
		return maps.get(input - 1);
	}

	private static void showMaps(List<String> maps) {
		for (int i = 0; i < maps.size(); i++) {
			Output.writeln((i + 1) + ". " + maps.get(i));
		}
	}
}