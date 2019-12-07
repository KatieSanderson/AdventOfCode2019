package day7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day7", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Integer::parseInt).collect(Collectors.toList());

        List<List<Integer>> phaseSequences = findValidPhaseSequence();

        int maxOutput = Integer.MIN_VALUE;
        for (List<Integer> phaseSequence : phaseSequences) {
            int inputNumber = 0;
            for (int phase : phaseSequence) {
                List<Integer> copyList = new ArrayList<>(input);
                Instruction.setInputNumbers(phase, inputNumber);
                int current = 0;
                while (!copyList.get(current).equals(99)) {
                    Instruction instruction = Instruction.createInstruction(copyList, current);
                    if (instruction.getOpcode() == 4) {
                        inputNumber = instruction.getOutput();
                    }
                    if (instruction.getHasInstuctionPointerBeenModified()) {
                        current = instruction.getInstructionPointer();
                    } else {
                        current += instruction.getNumValues();
                    }
                }
            }
            maxOutput = Math.max(maxOutput, inputNumber);
        }
        System.out.println("Part 1: " + maxOutput);
    }

    private static List<List<Integer>> findValidPhaseSequence() {
        List<List<Integer>> phaseSequences = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Integer> phaseSequence = new ArrayList<>();
            phaseSequence.add(i);
            for (int j = 0; j < 5; j++) {
                List<Integer> phaseSequence1 = new ArrayList<>(phaseSequence);
                if (!phaseSequence1.contains(j)) {
                    phaseSequence1.add(j);
                }
                for (int k = 0; k < 5; k++) {
                    List<Integer> phaseSequence2 = new ArrayList<>(phaseSequence1);
                    if (!phaseSequence2.contains(k)) {
                        phaseSequence2.add(k);
                    }
                    for (int l = 0; l < 5; l++) {
                        List<Integer> phaseSequence3 = new ArrayList<>(phaseSequence2);
                        if (!phaseSequence3.contains(l)) {
                            phaseSequence3.add(l);
                        }
                        for (int m = 0; m < 5; m++) {
                            if (!phaseSequence3.contains(m)) {
                                phaseSequence3.add(m);
                                if (phaseSequence3.size() == 5) {
                                    phaseSequences.add(phaseSequence3);
                                }
                            }
                        }
                    }
                }
            }
        }
        return phaseSequences;
    }
}
