/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Document {

    private final List<String> lines;
    private final String filePath;

    Document(List<String> lines, String filePath) {
        this.lines = lines;
        this.filePath = filePath;
    }

    List<String> getLines() {
        return this.lines;
    }

    String getPath() {
        return this.filePath;
    }

    static Document fromFile(File file) throws IOException {
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        String filePath = file.getPath();
        return new Document(lines, filePath);
    }
}
