package day05;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day05", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Integer::parseInt).collect(Collectors.toList());

        Instruction.setInputNumber(5);

        List<Integer> copyList = new ArrayList<>(input);
        int current = 0;
        while (!copyList.get(current).equals(99)) {
            Instruction instruction = Instruction.createInstruction(copyList, current);
            if (instruction.getOpcode() == 4) {
                System.out.println(instruction.getOutput());
            }
            if (instruction.getHasInstuctionPointerBeenModified()) {
                current = instruction.getInstructionPointer();
            } else {
                current += instruction.getNumValues();
            }
        }
    }
}
