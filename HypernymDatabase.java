import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * A class to search and create a database of Hypernym - Hyponym
 * relations out of a corpus.
 */
public class HypernymDatabase {
    private static final Pattern REGEX1 = Pattern.compile("<np>[^<]*</np>( ,)? (such as|including|especially) "
            + "<np>[^<]*</np>(( ,)? <np>[^<]*</np>)*( ,)?(( and| or) <np>[^<]*</np>)?");
    private static final Pattern REGEX2 = Pattern.compile("such <np>[^<]*</np> as "
            + "<np>[^<]*</np>(( ,)? <np>[^<]*</np>)*( ,)?(( and| or) <np>[^<]*</np>)?");
    private static final Pattern REGEX3 = Pattern.compile("<np>[^<]*</np>( ,)? which is ((an example |a kind |a class )"
            + "?of )?<np>[^<]*</np>");

    /**
     * Search for a specific Hypernym-Hyponym relation without creating a database.
     *
     * @param path - Corpus path.
     * @param key  - Hyponym to search for.
     */
    public void searchForHyponym(String path, String key) {
        // Create map
        TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map = createMap(path, key);

        Hyponym lemma = new Hyponym(key);
        // Create a map with Hypernyms containing the lemma
        TreeMap<String, Integer> finalMap = new TreeMap<>();
        for (Hypernym h : map.keySet()) {
            if (map.get(h).containsKey(lemma)) {
                finalMap.put(h.getName(), map.get(h).get(lemma));
            }
        }
        if (finalMap.isEmpty()) {
            System.out.println("The lemma doesn't appear in the corpus.");
            return;
        }
        // Sort and print
        Sort<String, Integer> sort = new Sort<>();
        Map<String, Integer> sortedMap = sort.hyponymByValue(finalMap);

        Set<Map.Entry<String, Integer>> set = sortedMap.entrySet();
        for (Map.Entry<String, Integer> o : set) {
            System.out.println(o.getKey() + ": " + "(" + o.getValue() + ")");
        }
    }

    /**
     * Create a Hypernym-Hyponym relation database.
     *
     * @param path1 - Corpus path.
     * @param path2 - Database path.
     */
    public void createDataBase(String path1, String path2) {
        // Create a map
        TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map = createMap(path1, null);

        // Write to file
        int j = 0;
        try {
            FileWriter dataBase = new FileWriter(path2, false);
            for (Hypernym h : map.keySet()) {
                // Write only if there are more than 3 Hyponyms
                if (map.get(h).size() >= 3) {
                    if (j != 0) {
                        dataBase.write("\n");
                    }
                    dataBase.write(h.getName() + ":");
                    j++;

                    // Sort and write Hyponyms
                    Sort<Hyponym, Integer> sort = new Sort<>();
                    Map<Hyponym, Integer> sortedMap = sort.hyponymByValue(map.get(h));

                    Set<Map.Entry<Hyponym, Integer>> hyponymCountSet = sortedMap.entrySet();
                    int i = 0;
                    for (Map.Entry<Hyponym, Integer> o : hyponymCountSet) {
                        if (i != 0 && i != hyponymCountSet.size()) {
                            dataBase.write(",");
                        }
                        dataBase.write(" ");
                        dataBase.write((o.getKey()).getName() + " (" + o.getValue() + ")");
                        i++;
                    }
                }
            }
            dataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a map of Hypernyms and Hyponyms.
     *
     * @param path  - The corpus path.
     * @param lemma - A lemma to search for.
     * @return a map containing the relations.
     */
    private TreeMap<Hypernym, TreeMap<Hyponym, Integer>> createMap(String path, String lemma) {
        // Create a map using the regex
        TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map = new TreeMap<>();
        try {
            File file = new File(path);
            File[] fileList = file.listFiles();
            Search search = new Search(lemma);
            search.searchBeginning(REGEX1, map, fileList);
            search.searchBeginning(REGEX2, map, fileList);
            search.searchEnd(REGEX3, map, fileList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}


