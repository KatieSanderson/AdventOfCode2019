package day2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day2", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Integer::parseInt).collect(Collectors.toList());

        int lowLimit = 0;
        int highLimit = 99;
        int desiredOutput = 19690720;

        for (int noun = lowLimit; noun < highLimit; noun++) {
            for (int verb = lowLimit; verb < highLimit; verb++) {
                List<Integer> copyList = new ArrayList<>(input);
                copyList.set(1, noun);
                copyList.set(2, verb);
                int current = 0;
                while (!copyList.get(current).equals(99)) {
                    parseInstruction(copyList, current);
                    current += 4;
                }
                if (copyList.get(0).equals(desiredOutput)) {
                    System.out.println(100 * noun + verb);
                    return;
                }
            }
        }


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
