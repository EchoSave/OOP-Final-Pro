package implementations;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import utilities.Iterator;

/**
 * Utility class responsible for generating formatted reports from the Word
 * BSTree.
 * Output can be printed directly to the console or written to a specified text
 * file.
 */
public class ReportGenerator {

    /**
     * Generates a word tracking report based on the provided command-line mode.
     * 
     * @param tree       The Binary Search Tree containing the word data.
     * @param mode       The display mode flag (-pf, -pl, or -po).
     * @param outputPath The file path for output redirection (null or empty for
     *                   console output).
     */
    public static void generate(BSTree<Word> tree, String mode, String outputPath) {
        PrintWriter out = null;

        try {
            // Redirect output to a file if an output path is provided, otherwise use
            // System.out
            if (outputPath != null && !outputPath.trim().isEmpty()) {
                out = new PrintWriter(new FileWriter(outputPath));
            } else {
                out = new PrintWriter(System.out);
            }

            // Perform an in-order traversal to retrieve words in alphabetical order (A-Z)
            Iterator<Word> it = tree.inorderIterator();

            while (it.hasNext()) {
                Word wordObj = it.next();
                printWordDetails(out, wordObj, mode);
            }

            out.flush(); // Ensure all buffered data is written to the output stream

        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        } finally {
            // Only close the PrintWriter if writing to a file (avoid closing System.out)
            if (outputPath != null && out != null) {
                out.close();
            }
        }
    }

    /**
     * Formats and prints the details of a single word based on the selected mode.
     * 
     * @param out     The PrintWriter destination (file or console).
     * @param wordObj The Word object containing occurrence information.
     * @param mode    The output mode (-pf, -pl, -po).
     */
    private static void printWordDetails(PrintWriter out, Word wordObj, String mode) {
        out.println("Key : ===" + wordObj.getWord() + "===");

        Map<String, ArrayList<Integer>> occurrences = wordObj.getOccurrences();

        for (Map.Entry<String, ArrayList<Integer>> entry : occurrences.entrySet()) {
            String fileName = entry.getKey();
            ArrayList<Integer> lines = entry.getValue();

            switch (mode.toLowerCase()) {
                case "-pf":
                    // -pf: Prints word and the list of files containing it
                    out.println("  file: " + fileName);
                    break;

                case "-pl":
                    // -pl: Prints word, file names, and line numbers
                    out.println("  file: " + fileName + " lines: " + formatLines(lines));
                    break;

                case "-po":
                    // -po: Prints word, file names, occurrence frequencies, and line numbers
                    out.println("  file: " + fileName +
                            " number of occurrences: " + lines.size() +
                            " lines: " + formatLines(lines));
                    break;
            }
        }
        out.println(); // Blank line separator between entries
    }

    /**
     * Formats an ArrayList of line numbers into a comma-separated String (e.g., "1,
     * 3, 7").
     * 
     * @param lines The list of line numbers.
     * @return A formatted comma-separated String.
     */
    private static String formatLines(ArrayList<Integer> lines) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i));
            if (i < lines.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}