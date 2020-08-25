package com.codecool;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FilePartReaderTest {

    @Test
    public void setup_InvalidLinesToRead_ThrowsExceptions() {
        FilePartReader filePartReader = new FilePartReader();
        assertThrows(IllegalArgumentException.class, () -> {
            filePartReader.setup("", 0, 5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            filePartReader.setup("", 4, 1);
        });
        assertDoesNotThrow(() -> filePartReader.setup("", 1, 1));
    }

    @Test
    public void setup_ValidLinesToRead_DoesNotThrowException() {
        FilePartReader filePartReader = new FilePartReader();
        assertDoesNotThrow(() -> filePartReader.setup("", 1, 1));
        assertDoesNotThrow(() -> filePartReader.setup("", 1, 5));
        assertDoesNotThrow(() -> filePartReader.setup("", 3, 8));
    }

    @Test
    public void read_InvalidPathToFile_ThrowsIOException() {
        FilePartReader filePartReader = new FilePartReader();
        filePartReader.setup("invalidPathToFile", 1, 1);
        assertThrows(IOException.class, filePartReader::read);
    }

    @Test
    public void read_PathToFileInResources_ReturnsNotEmptyString() {
        FilePartReader filePartReader = new FilePartReader();
        String pathToTestFile = getClass().getClassLoader().getResource("testFile.txt").getPath();
        filePartReader.setup(pathToTestFile, 1, 4);
        String returnedString = "";
        try {
            returnedString = filePartReader.read();
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
        assertNotNull(returnedString);
        assertNotEquals("", returnedString);
    }

    @Test
    public void readLines_InvalidPathToFile_ThrowsException() {
        FilePartReader filePartReader = new FilePartReader();
        filePartReader.setup("invalidPathToFile", 1, 1);
        assertThrows(IllegalArgumentException.class, filePartReader::readLines);
    }

    @Test
    public void readLines_TooManyLinesToRead_ThrowsException() {
        FilePartReader filePartReader = new FilePartReader();
        String pathToTestFile = getClass().getClassLoader().getResource("testFile.txt").getPath();
        int amountOfLinesInTestFile = 25;
        filePartReader.setup(pathToTestFile, 1, amountOfLinesInTestFile + 1);
        assertThrows(IllegalArgumentException.class, filePartReader::readLines);
    }

    @ParameterizedTest
    @MethodSource("returnIntegerPairsArray")
    public void readLines_ValidAmountOfLines_ReturnsProperAmountOfLines(int[] parameters) {
        FilePartReader filePartReader = new FilePartReader();
        int fromLine = parameters[0];
        int toLine = parameters[1];
        int lineAmount = toLine - fromLine + 1;
        String pathToTestFile = getClass().getClassLoader().getResource("testFile.txt").getPath();
        filePartReader.setup(pathToTestFile, fromLine, toLine);
        String allLines = filePartReader.readLines();
        int amountOfReturnedLines = Long.valueOf(Arrays.stream(allLines.split("\n")).count()).intValue();
        assertEquals(lineAmount, amountOfReturnedLines);
    }

    private static int[][] returnIntegerPairsArray() {
        return new int[][]{{1, 1}, {1, 3}, {1, 25}, {3, 6}, {11, 22}, {12, 19}};
    }

}