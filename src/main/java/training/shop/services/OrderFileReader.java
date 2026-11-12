package training.shop.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OrderFileReader {

    // Строки файла без пустых
    public List<String> readLines(Path path) throws IOException {
        List<String> result = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            if (!line.isBlank()) {
                result.add(line);
            }
        }
        return result;
    }
}
