package implementations;

import implementations.BSTree;
import utilities.Iterator;

// Reports, not sure if we need, just a placeholder for now
public class ReportGenerator {
    public static void generate(BSTree<Word> tree, String mode, String outputPath) {
        Iterator<Word> it = tree.inorderIterator();
        while (it.hasNext()) {
            System.out.println(it.next().getWord());
        }
    }
}