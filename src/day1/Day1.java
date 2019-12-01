package day1;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class Day1 {

    public static void main(String[] args) throws IOException {
        System.out.println(Files.readAllLines(FileSystems.getDefault().getPath("src", "day1", "input.txt")).stream()
                .map(Integer::parseInt).reduce(0, (sum, val) -> sum + (val / 3 - 2)));
    }
}
