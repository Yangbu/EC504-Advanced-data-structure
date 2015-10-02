import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The Query class
 *
 */
public class Query {
	// index
	InvertedIndex index;

	/**
	 * Constructor
	 * 
	 * @param index
	 *            the index
	 */
	public Query(InvertedIndex index) {
		this.index = index;
	}

	/**
	 * Do querying
	 * 
	 * @param line
	 *            the input key words
	 * @param details
	 *            show details or not
	 * @return the top 10 search results
	 */
	public List<String> query(String line, boolean details) {
		Map<Integer, Result> map = new HashMap<Integer, Result>();
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(" |\\?|,|\\.|/|@|\\$|-|¡ª|\\&|!|\\[|\\]|\n");
		HashSet<String> words = new HashSet<String>();
		while (lineScanner.hasNext()) {
			String word = lineScanner.next();
			word = word.trim();
			if (word.isEmpty()) {
				continue;
			}
			word = word.toLowerCase().trim();
			words.add(word);
		}
		for (String word : words) {
			// find from index
			Map<Integer, Integer> find = index.find(word);
			if (find == null) {
				// no found
				continue;
			}
			// find=[line : count]
			for (Integer key : find.keySet()) {
				Result result = map.get(key);
				if (result != null) {
					// add to other find-result
					result.add(word, find.get(key));
				} else {
					// add new find-result
					result = new Result();
					result.line = key;
					result.add(word, find.get(key));
					map.put(key, result);
				}
			}
		}
		lineScanner.close();

		// sort
		List<Result> results = new ArrayList<Result>();
		for (Result result : map.values()) {
			results.add(result);
		}
		Collections.sort(results);

		// return string list
		List<String> lines = new ArrayList<String>();
		int i=0;
		for (Result result : results) {
			i++;
			String s = i+". "+index.getLine(result.getLine());
			if (details) {
				s = "<html>" + s + "<br>" + result + "</html>";
			}
			lines.add(s);
			if (lines.size() >= 10) {
				break;
			}
		}
		return lines;
	}

	// /**
	// * for testing
	// * @param args
	// * @throws FileNotFoundException
	// */
	// public static void main(String[] args) throws FileNotFoundException {
	//
	// String i = "i.txt";
	// String q = "the moment font changes";
	// InvertedIndex index = new InvertedIndex();
	// new Parser(index, i, null);
	// Query query = new Query(index);
	// List<String> results = query.query(q, true);
	// for (String line : results) {
	// System.out.println(line);
	// }
	// }
}
