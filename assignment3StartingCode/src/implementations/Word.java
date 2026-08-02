package implementations;

import java.util.ArrayList;
import java.util.HashMap;

// PLACEHOLDER - belongs to <serialization person>. Delete on handoff.
// Real version needs: implements Serializable, serialVersionUID,
// and getters the report layer can read occurrences through.
public class Word implements Comparable<Word> {
    private String word;
    private HashMap<String, ArrayList<Integer>> occurrences = new HashMap<>();

    public Word(String word) { this.word = word; }
    public String getWord() { return word; }

    public void addOccurrence(String fileName, int lineNumber) {
        occurrences.computeIfAbsent(fileName, k -> new ArrayList<>()).add(lineNumber);
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareToIgnoreCase(other.word);
    }
}