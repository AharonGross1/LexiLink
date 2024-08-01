import java.util.*;
import java.io.File;


/**
 * Main class:
 * This program provides two main functions:
 * 1 - Search for Occurrences of a Specific Hyponym: Find and display instances of a specific hyponym in the database.
 * 2 - Create a Hypernym-Hyponym List: Generate a file listing all hypernyms and their related hyponyms from the database.
 * 
 * @param args - is supposed to contain 3 parameters - An option ("1" for option 1 or anything else for option 2), the path
 *               to the corpus, and the path for the output file or keyword, depending on the choice.
 */
public class Main {
    public static void main(String[] args) {
        // Not the right amount of args
        if(args.length != 3) {
            System.out.println("Please enter 3 arguments.");
            return;
        }

        // Check corpus path
        File corpusDirectory = new File(args[1]);
        if (!corpusDirectory.exists() || !corpusDirectory.isDirectory()) {
            System.out.println("Error: The provided path for the corpus is not a valid directory.");
            return;
        }

        HypernymDatabase db = new HypernymDatabase();
        // Option 1: Search for Occurrences of a Specific Hyponym
        if (args[0].equals("1")) {
            db.searchForHyponym(args[1], args[2]);
        } else { // Option 2: Create a Hypernym-Hyponym List
            File corpusOutput = new File(args[2]);
            if (!corpusOutput.exists()) {
            System.out.println("Error: The provided path for the output file is not a valid directory.");
            return;
            }
            db.createDataBase(args[1], args[2] + "/HypeHipeDB.txt");
        }
    }
}