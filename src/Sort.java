import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class implementing sort methods.
 */
public class Sort<K, V extends Comparable<V>> {
    /**
     * A sort method to sort by amount and then alphabetically.
     *
     * @param map - The map to sort.
     * @return    - A sorted map.
     */
    public Map<K, V> hyponymByValue(Map<K, V> map) {

        Comparator<K> valueComparator = new Comparator<>() {
            public int compare(K h1, K h2) {
                // Conpare the amount
                int comp = map.get(h1).compareTo(map.get(h2));
                // Break the tie by comparing the strings alphabetically
                if (comp == 0) {
                    return h1.toString().compareTo(h2.toString());
                } else {
                    return -comp;
                }
            }
        };

        Map<K, V> sortedMap = new TreeMap<>(valueComparator);

        sortedMap.putAll(map);

        return sortedMap;
    }
}



