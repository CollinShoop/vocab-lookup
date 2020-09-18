package com.bb.cms.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOUtil {

    public static List<String> readQuizletExport(String path) {
        final File file = new File(path);
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File can not be folder: " + path);
        } else if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }
        final List<String> results = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                final String s = untilBlankLine(scanner);
                if (!s.trim().isEmpty()) {
                    results.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static String untilBlankLine(Scanner scanner) {
        final StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            final String s = scanner.nextLine();
            if (s.isEmpty()) {
                break;
            } else {
                builder.append(s + "\n");
            }
        }
        return builder.toString();
    }

    public static void saveFile(String path, Object o) throws IOException {
        final File file = new File(path);
        try (final FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(o.toString());
        }
    }

}
