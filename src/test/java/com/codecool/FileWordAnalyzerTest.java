package com.codecool;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.apache.commons.lang3.StringUtils;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileWordAnalyzerTest {

    private FileWordAnalyzer fileWordAnalyzer;

    public FileWordAnalyzerTest() {
        String pathToTestFile = getClass().getClassLoader().getResource("testFile.txt").getPath();
        FilePartReader filePartReader = new FilePartReader();
        int amountOfLinesInTestFile = 25;
        filePartReader.setup(pathToTestFile, 1, amountOfLinesInTestFile);
        fileWordAnalyzer = new FileWordAnalyzer(filePartReader);
    }

    @Test
    public void getWordsOrderedAlphabetically_ValidFile_ReturnsSortedList() {
        List<String> sortedList = fileWordAnalyzer.getWordsOrderedAlphabetically();
        assert !sortedList.isEmpty();
        assert sortedList.size() != 1;
        for (int i = 1; i < sortedList.size(); i++) {
            assertTrue(sortedList.get(i-1).compareTo(sortedList.get(i)) < 0);
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "est", "Line"})
    public void getWordsContainingSubstring_SubstringContainedInTestFile_ReturnsListOfStrings(String substring) {
        List<String> wordsContainingSubstring = fileWordAnalyzer.getWordsContainingSubstring(substring);
        assert wordsContainingSubstring.stream().allMatch(s -> s.contains(substring));
    }

    @Test
    public void getStringsWhichPalindromes_FileContainsPalindromes_ReturnsListOfStrings() {
        List<String> stringsWhichPalindromes = fileWordAnalyzer.getStringsWhichPalindromes();
        assert stringsWhichPalindromes.stream().allMatch(s -> StringUtils.reverse(s).equals(s));
    }

}