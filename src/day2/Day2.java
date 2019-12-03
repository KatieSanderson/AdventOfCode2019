package day2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day2", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Integer::parseInt).collect(Collectors.toList());
        input.set(1, 12);
        input.set(2, 2);

        int current = 0;
        while (!input.get(current).equals(99)) {
            parseInstruction(input, current);
            current += 4;
        }
        System.out.println(input.get(0));
    }

    private static void parseInstruction(List<Integer> input, int current) {
        int currentPlus1Value = input.get(input.get(current + 1));
        int currentPlus2Value = input.get(input.get(current + 2));
        int currentPlus3 = input.get(current + 3);
        int nextValue;
        switch (input.get(current)) {
            case 1 :
                nextValue = currentPlus1Value + currentPlus2Value;
                break;
            case 2 :
                nextValue = currentPlus1Value * currentPlus2Value;
                break;
            default :
                throw new IllegalArgumentException("Unknown opcode [" + input.get(current) + "] at position [" + current + "].");
        }
        input.set(currentPlus3, nextValue);
    }
}
