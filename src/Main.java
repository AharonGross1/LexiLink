import java.util.*;
import java.io.File;


/**
 * Main class.
 */
public class Main {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Please enter 3 arguments.");
            return;
        }
        File corpusDirectory = new File(args[1]);
        if (!corpusDirectory.exists() || !corpusDirectory.isDirectory()) {
            System.out.println("Error: The provided path for the corpus is not a valid directory.");
            return;
        }
            HypernymDatabase db = new HypernymDatabase();
        if (args[0].equals("1")) {
            db.searchForHyponym(args[1], args[2]);
        } else {
            File corpusOutput = new File(args[2]);
            if (!corpusOutput.exists()) {
            System.out.println("Error: The provided path for the output file is not a valid directory.");
            return;
            }
            db.createDataBase(args[1], args[2] + "/HypeHipeDB.txt");
        }
    }
}