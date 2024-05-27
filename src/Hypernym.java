import java.util.TreeMap;

/**
 * A class representing a Hyponym.
 */
public class Hypernym implements Comparable<Hypernym> {
    private TreeMap<Hyponym, Integer> map;
    private String name;

    /**
     * Class constructor.
     *
     * @param name - A string to input as name.
     */
    public Hypernym(String name) {
        this.map = new TreeMap<>();
        this.name = name;
    }

    @Override
    public int compareTo(Hypernym h) {
        return this.name.toLowerCase().compareTo(h.getName().toLowerCase());
    }

    /**
     * Getter for the class' map.
     *
     * @return - The class' map.
     */
    public TreeMap<Hyponym, Integer> getMap() {
        return this.map;
    }

    /**
     * Getter for name.
     *
     * @return - The class' name.
     */
    public String getName() {
        return name;
    }
}
