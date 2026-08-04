package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Word implements Comparable<Word>, Serializable {
    private static final long serialVersionUID = 1L;

    private String word;

    private Map<String, ArrayList<Integer>> occurrences = new TreeMap<>();

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void addOccurrence(String fileName, int lineNumber) {
        occurrences.computeIfAbsent(fileName, k -> new ArrayList<>()).add(lineNumber);
    }

    public Map<String, ArrayList<Integer>> getOccurrences() {
        return occurrences;
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareToIgnoreCase(other.word);
    }
}