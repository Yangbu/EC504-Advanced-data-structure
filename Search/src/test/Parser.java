import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * parse a text file
 *
 */
public class Parser {

	/**
	 * Constructor add words to the index
	 * 
	 * @param index
	 *            the index
	 * @param file
	 *            the text file
	 * @param ui
	 *            the GUI window
	 * @throws FileNotFoundException
	 */
	public Parser(InvertedIndex index, String file, UI ui)
			throws FileNotFoundException {
//		ui.init();
		Scanner fileScanner = new Scanner(new File(file));
		index.clear();
		while (fileScanner.hasNext()) {
			String line = fileScanner.nextLine().trim();
			if (line.isEmpty()) {
				continue;
			}
			int idx = index.addLine(line);
			Scanner lineScanner = new Scanner(line);
			lineScanner
					.useDelimiter(" |\\?|,|\\.|/|@|\\$|-|¡ª|\\&|!|\\[|\\]|\n");
			while (lineScanner.hasNext()) {
				String word = lineScanner.next();
				word = word.trim();
				if (word.isEmpty()) {
					continue;
				}
				word = word.toLowerCase();
				// System.out.println(word);
				// add to index
				index.add(word, idx);
			}
			lineScanner.close();
		}
		fileScanner.close();
//		ui.query();
	}

}
