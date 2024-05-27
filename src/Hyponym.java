/**
 * A class representing a Hyponym.
 */
public class Hyponym implements Comparable<Hyponym> {
    private String name;

    /**
     * Class constructor.
     *
     * @param name - String to input as name.
     */
    public Hyponym(String name) {
        this.name = name;
    }

    /**
     * Getter for name.
     * @return - The class' name.
     */
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Hyponym h) {
        return this.name.toLowerCase().compareTo(h.getName().toLowerCase());
    }
}
