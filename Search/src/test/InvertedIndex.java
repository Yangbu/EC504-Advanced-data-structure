import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The inverted index class
 *
 */
public class InvertedIndex {
	List<String> lines = new ArrayList<String>();
	Map<String, Map<Integer, Integer>> map = new HashMap<String, Map<Integer, Integer>>();

	/**
	 * clear all for restarting
	 */
	public void clear() {
		map.clear();
		lines.clear();
	}

	/**
	 * Add line to cache
	 * 
	 * @param line
	 *            a string line
	 * @return index of the line
	 */
	public int addLine(String line) {
		lines.add(line);
		return lines.size() - 1;
	}

	/**
	 * Get line from cache
	 * 
	 * @param line
	 *            the index of line
	 * @return the string line
	 */
	public String getLine(int line) {
		return lines.get(line);
	}

	/**
	 * Add word to index
	 * 
	 * @param word
	 *            the word
	 * @param line
	 *            the index of line in the cache
	 */
	public void add(String word, int line) {
		Map<Integer, Integer> list = map.get(word);
		if (list == null) {
			list = new HashMap<Integer, Integer>();
			list.put(line, 1);
			map.put(word, list);
		} else {
			Integer n = list.get(line);
			if (n == null) {
				list.put(line, 1);
			} else {
				list.put(line, n + 1);
			}
		}
	}

	/**
	 * search the index map
	 * 
	 * @param word
	 *            the keyword
	 * @return the search result
	 */
	public Map<Integer, Integer> find(String word) {
		return map.get(word);
	}
}
