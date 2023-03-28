import java.util.*;

/**
 * Main class.
 */
public class Main {

    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Please enter 3 arguments.");
            return;
        }
            HypernymDatabase db = new HypernymDatabase();
        if (args[0].equals("1")) {
            db.searchForHyponym(args[1], args[2]);
        } else {
            db.createDataBase(args[1], args[2]);
        }
    }
}
