package com.codecool;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FilePartReader {

    private String filePath;
    private int fromLine;
    private int toLine;

    public FilePartReader() {
        this.filePath = "";
        this.fromLine = -1;
        this.toLine = -3;
    }

    public void setup(String filePath, int fromLine, int toLine) {
        this.filePath = filePath;
        if (validateLineNumberToRead(fromLine, toLine)) throw new IllegalArgumentException("Invalid lines to read!");
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    private boolean validateLineNumberToRead(int fromLine, int toLine) {
        return fromLine < 1 || toLine < fromLine;
    }

    public String read() throws IOException {
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(new File(filePath));
        while(scanner.hasNextLine()) {
            content.append(scanner.nextLine());
            content.append("\n");
        }
        return content.toString();
    }

    public String readLines() {
        String[] lines = {};

        try {
            lines = read().split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    if (requiredLinesOutOfBounds(lines.length)) throw new IllegalArgumentException("Too many lines to read!");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = fromLine - 1; i < toLine; i++) {
            stringBuilder.append(lines[i]);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private boolean requiredLinesOutOfBounds(int length) {
        return length < toLine;
    }

}
