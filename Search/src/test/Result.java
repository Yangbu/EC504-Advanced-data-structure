import java.util.HashMap;
import java.util.Map;

/**
 * Search result class
 *
 */
public class Result implements Comparable<Result> {
	// line index
	int line;
	// [word:count]
	Map<String, Integer> match = new HashMap<String, Integer>();

	public int getLine() {
		return line;
	}

	public Map<String, Integer> getMatch() {
		return match;
	}

	int total() {
		int t = 0;
		for (Integer i : match.values()) {
			t += i;
		}
		return t;
	}

	// Descending order
	@Override
	public int compareTo(Result o) {
		if (match.size() == o.match.size()) {
			return o.total() - total();
		}
		return o.match.size() - match.size();
	}

	/**
	 * add count
	 * 
	 * @param word
	 *            keyword
	 * @param n
	 *            count
	 */
	public void add(String word, int n) {
		if (match.containsKey(word)) {
			match.put(word, match.get(word) + n);
		} else {
			match.put(word, n);
		}
	}

	@Override
	public String toString() {
		return match.toString();
	}
}
