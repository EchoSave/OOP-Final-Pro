package implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Word Tracker
 *
 * Description: 
 * Responsibilities of this class (input side only):
 *   - parse and validate command line arguments
 *   - read the input file and record every word occurrence into the tree
 *   - call out to the repository layer for load/save
 *   - call out to the report layer for output
 *
 */
public class WordTracker {

    private static final String USAGE =
        "Usage: java -jar WordTracker.jar <input.txt> -pf|-pl|-po [-f<output.txt>]";

    public static void main(String[] args) {

    	// INPUT
        // Parse and validate arguments 
        if (args.length < 2) {
            System.out.println(USAGE);
            return;
        }

        String inputPath = args[0];
        String mode = args[1];
        String outputPath = null;

        File inputFile = new File(inputPath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.out.println("Error: cannot find input file '" + inputPath + "'");
            return;
        }

        if (!mode.equals("-pf") && !mode.equals("-pl") && !mode.equals("-po")) {
            System.out.println("Error: mode must be -pf, -pl, or -po");
            System.out.println(USAGE);
            return;
        }

        if (args.length >= 3) {
            if (args[2].startsWith("-f") && args[2].length() > 2) {
                outputPath = args[2].substring(2);
            } else {
                System.out.println("Error: output flag must be -f<filename> with no space");
                System.out.println(USAGE);
                return; 
            }
        }

        
        // Serialization part for Irteza
        BSTree<Word> tree = RepositoryManager.load();

        // Scan the input file into the tree 
        try {
            processFile(tree, inputFile);
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Report ----------
        ReportGenerator.generate(tree, mode, outputPath);

        RepositoryManager.save(tree);
    }

    /**
     * Reads the given file line by line and records each word occurrence in the tree.
     */
    private static void processFile(BSTree<Word> tree, File file) throws IOException {
        String fileName = file.getName();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                for (String token : line.split("[^a-zA-Z']+")) {
                    String word = clean(token);
                    if (word.isEmpty()) continue;
                    record(tree, word, fileName, lineNumber);
                }
            }
        }
    }

    /**
     * Trims leading and trailing apostrophes so that "it's" survives intact
     * but 'quoted' does not keep its quote marks.
     */
    private static String clean(String token) {
        int start = 0;
        int end = token.length();
        while (start < end && token.charAt(start) == '\'') start++;
        while (end > start && token.charAt(end - 1) == '\'') end--;
        return token.substring(start, end);
    }

    /**
     * Adds an occurrence to an existing word node, or creates a new node if the
     * word has not been seen before. The find first pattern is required because
     * BSTree.add() rejects duplicates rather than merging them.
     */
    private static void record(BSTree<Word> tree, String word, String fileName, int lineNumber) {
        Word probe = new Word(word);
        BSTreeNode<Word> found = tree.search(probe);

        if (found != null) {
            found.getElement().addOccurrence(fileName, lineNumber);
        } else {
            probe.addOccurrence(fileName, lineNumber);
            tree.add(probe);
        }
    }
}