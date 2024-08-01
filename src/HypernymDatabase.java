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
        Hyponym hypToFind = new Hyponym(key);

        // Create a map with Hypernyms containing the hypToFind
        TreeMap<String, Integer> finalMap = new TreeMap<>();
        for (Hypernym hyp : map.keySet()) {
            if (map.get(hyp).containsKey(hypToFind)) {
                finalMap.put(hyp.getName(), map.get(hyp).get(hypToFind));
            }
        }

        if (finalMap.isEmpty()) {
            System.out.println("The hyponym you entered doesn't appear in the corpus.");
            return;
        }

        // Sort and print all the Hypernyms
        Sort<String, Integer> sortingAlg = new Sort<>();
        Map<String, Integer> sortedMap = sortingAlg.hyponymByValue(finalMap);
        Set<Map.Entry<String, Integer>> HypernymAndValueSet = sortedMap.entrySet();
        for (Map.Entry<String, Integer> set : HypernymAndValueSet) {
            System.out.println(set.getKey() + ": " + "(" + set.getValue() + ")");
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
                    Sort<Hyponym, Integer> sortingAlg = new Sort<>();
                    Map<Hyponym, Integer> sortedMap = sortingAlg.hyponymByValue(map.get(h));

                    Set<Map.Entry<Hyponym, Integer>> hyponymCountSet = sortedMap.entrySet();
                    int i = 0;
                    for (Map.Entry<Hyponym, Integer> set : hyponymCountSet) {
                        if (i != 0 && i != hyponymCountSet.size()) {
                            dataBase.write(",");
                        }
                        dataBase.write(" ");
                        dataBase.write((set.getKey()).getName() + " (" + set.getValue() + ")");
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


