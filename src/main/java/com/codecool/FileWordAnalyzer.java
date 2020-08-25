package com.codecool;

import java.util.*;
import java.util.stream.Collectors;

public class FileWordAnalyzer {

    private FilePartReader filePartReader;

    public FileWordAnalyzer(FilePartReader filePartReader) {
        this.filePartReader = filePartReader;
    }

    public List<String> getWordsOrderedAlphabetically() {
        List<String> allWords = getAllWords();
        Collections.sort(allWords);
        return allWords;
    }

    public List<String> getWordsContainingSubstring(String subString) {
        return getAllWords()
                .stream()
                .filter(word -> word.contains(subString))
                .collect(Collectors.toList());
    }

    public List<String> getStringsWhichPalindromes() {
        return getAllWords()
                .stream()
                .filter(this::isPalindrome)
                .collect(Collectors.toList());
    }

    private List<String> getAllWords() {
        String[] lines = filePartReader.readLines().split("\n");
        Set<String> setOfWords = new HashSet<>();
        Arrays.stream(lines).forEach(line -> {
            setOfWords.addAll(Arrays.asList(line.split(" ")));
        });
        return new ArrayList<>(setOfWords);
    }

    private boolean isPalindrome(String word) {
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) != word.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
