package com.algoarena.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWriter {
    public static void write(String content, String filepath){
        Path path = Paths.get(filepath);

        try {
            Files.createDirectories(path.getParent()); // Create directories if they don't exist
            Files.write(path, content.getBytes(), StandardOpenOption.CREATE);

            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
