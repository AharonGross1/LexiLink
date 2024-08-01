import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class with methods to search for hypernyms and hyponyms in text files.
**/
public class Search {
    private final String lemma; // The lemma to search for
    private final String phrase = "<np>[^<]*</np>"; // Pattern for noun phrases

    /**
     * Class constructor.
     *
     * @param search - A lemma to search for.
     */
    public Search(String search) {
        if (search != null) {
            search = search.toLowerCase();
        }
        // Set the lemma to search for (in lowercase)
        this.lemma = search; 
    }

    /**
     * Specific search for Hypernyms appearing at the beginning of the sentence.
     *
     * @param np  - Current matcher.
     * @param map - Map to add the relations to.
     */
    private void start(Matcher np, TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map) {
        int counter = 0;
        Hypernym father = new Hypernym("");
        Hyponym kid;
        while (np.find()) { // Find all noun phrases in the matcher
            String sub = np.group().substring(4, np.group(0).length() - 5); // Extract noun phrase without tags
            if (counter == 0) { // First noun phrase is the hypernym
                father = new Hypernym(sub);
                if (!map.containsKey(father)) {
                    map.put(father, father.getMap());
                }
                counter++;
            } else { // Subsequent noun phrases are hyponyms
                kid = new Hyponym(sub);
                addToMap(father, kid, map);
            }
        }
    }

    /**
     * Specific search for Hypernyms appearing at the end of the sentence.
     *
     * @param np  - Current matcher.
     * @param map - Map to add the relations to.
     */
    private void end(Matcher np, TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map) {
        int counter = 0;
        Hypernym father;
        Hyponym kid = new Hyponym("");

        // Find up to two noun phrases
        while (np.find() && counter < 2) { 
            String sub = np.group().substring(4, np.group(0).length() - 5); // Extract noun phrase without tags
            if (counter == 0) { // First noun phrase is the hyponym
                kid = new Hyponym(sub);
            } else if (counter == 1) { // Second noun phrase is the hypernym
                father = new Hypernym(sub);
                if (!map.containsKey(father)) {
                    map.put(father, father.getMap());
                }
                addToMap(father, kid, map);
            }

            counter++;
        }
    }

    /**
     * Add Hyponym to map under Hypernym if not already present, otherwise increment its count.
     *
     * @param father - The Hypernym.
     * @param kid    - Hyponym to add.
     * @param map    - Map to add the Hyponym to.
     */
    private void addToMap(Hypernym father, Hyponym kid, TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map) {
        boolean inMap = false;
        for (Hyponym h : map.get(father).keySet()) {
            // Check if hyponym already exists
            if (kid.getName().equals(h.getName())) { 
                inMap = true;
                break;
            }
        }

        // If the hyponym isn't present in the map yet, add it with a count of 1
        if (!inMap) {
            map.get(father).put(kid, 1);
        } else { // if the hyponym is already present, increment its count
            map.get(father).replace(kid, map.get(father).get(kid) + 1);
        }
    }

    /**
     * Running each line of the db and searching for patterns.
     *
     * @param pattern    - The pattern to search for.
     * @param nounPhrase - "np" appearances in the text.
     * @param line       - Current line read by the stream.
     * @param map        - The map to collect the data.
     * @param searchType - If "np" should appear at the end or start of the phrase.
     */
    private void searchInside(Pattern pattern, Pattern nounPhrase, String line,
                              TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map, String searchType) {
        // Find all matches of the pattern in the line
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            Matcher np = nounPhrase.matcher(matcher.group(0));

            // If search type is "end", call end method
            if (searchType.equals("end")) {
                this.end(np, map);
            } else if (searchType.equals("start")) { // If search type is "start", call start method
                this.start(np, map);
            }
        }
    }

    /**
     * Search for patterns in the database, looking for hypernyms at the end of the sentence.
     *
     * @param pattern  - The pattern to search for.
     * @param map      - The map to collect the data.
     * @param fileList - The list of files to search in.
     * @throws IOException - Throws an exception.
     */
    public void searchEnd(Pattern pattern, TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map, File[] fileList) throws IOException {
        Pattern nounPhrase = Pattern.compile(this.phrase);
        for (File file : fileList) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            // Read each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                line = line.toLowerCase();
                // Check if line contains specific phrase
                boolean b = line.contains("which is");
                if (this.lemma != null) {
                    if (line.contains(this.lemma) && b) {
                        // Search for patterns if conditions met
                        this.searchInside(pattern, nounPhrase, line, map, "end");
                    }
                } else if (b) {
                    this.searchInside(pattern, nounPhrase, line, map, "end");
                }
            }
        }
    }

    /**
     * Search for patterns in the database, looking for hypernyms at the beginning of the sentence.
     *
     * @param pattern  - The pattern to search for.
     * @param map      - The map to collect the data.
     * @param fileList - The list of files to search in.
     * @throws IOException - Throws an exception.
     */
    public void searchBeginning(Pattern pattern, TreeMap<Hypernym, TreeMap<Hyponym, Integer>> map, File[] fileList)
            throws IOException {
        Pattern nounPhrase = Pattern.compile(this.phrase);
        for (File file : fileList) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            // Read each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                line = line.toLowerCase();
                // Check if line contains specific phrases
                boolean b = line.contains("such") || line.contains("including") || line.contains("especially");
                if (this.lemma != null) {
                    if (line.contains(this.lemma) && b) {
                        // Search for patterns if conditions met
                        this.searchInside(pattern, nounPhrase, line, map, "start");
                    }
                } else {
                    if (b) {
                        this.searchInside(pattern, nounPhrase, line, map, "start");
                    }
                }
            }
        }
    }
}