package rproject.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

	/**
	 * The relative path of the folder containing neighbourhood matrix
	 * for each map.
	 */
	public static String NBHOOD_MATRIX_PATH = "maps/matrix/";

	/**
	 * The relative path of the folder containing territory names for
	 * each map.
	 */
	public static String MAP_NAMES_PATH = "maps/names/";

	/**
	 * A list of all available maps.
	 */
	private static List<String> maps;

	/**
	 * Returns a list of names all the maps available for playing.
	 *
	 * @return a list of names all the maps available for playing
	 */
	public static List<String> getMaps() {
		if (maps != null) return maps;
		readMaps();
		return maps;
	}

	/**
	 * Reads the available maps by scanning the {@code maps/names}
	 * directory.
	 */
	private static void readMaps() {
		File dir = new File(MAP_NAMES_PATH);

		List<String> maps = new ArrayList<>();
		for (String file : dir.list()) {
			maps.add(file.substring(0, file.lastIndexOf(".")));
		}
		FileUtil.maps = maps;
	}

	/**
	 * Reads all lines from the specified file.
	 *
	 * @param path the string representation of the file to read
	 * @return a list of all the lines read in the given file
	 * @throws FileNotFoundException if the file cannot be found
	 */
	private static List<String> readLines(String path) throws IOException {
		return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
	}
}
