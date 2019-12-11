package day11;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Long> input = (ArrayList<Long>) Files.readAllLines(FileSystems.getDefault().getPath("src", "day11", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Long::parseLong).collect(Collectors.toList());
        ensureSize(input, input.size() + 1000);

        System.out.println("Part 2: " + findMaxOutput(input));
    }

    private static void ensureSize(ArrayList<Long> list, int size) {
        list.ensureCapacity(size);
        while (list.size() < size) {
            list.add(0L);
        }
    }

    private static long findMaxOutput(List<Long> input) {
        int maxX = 0;
        int maxY = 0;
        int minX = 0;
        int minY = 0;
        Map<String, Boolean> cells = new HashMap<>();
        // false = black, true = white
        int x = 0;
        int y = 0;
        // 1 is up, 2 is right, 3 is down, 4 is left
        int direction = 0;
        cells.put(x + " " + y, true);

        Amplifier amplifier = new Amplifier(new ArrayList<>(input));

        int current = 0;
        while (cells.size() < 2322 || amplifier.getList().get(current) != 99) {
            System.out.println(cells.size() + " " + amplifier.getList().get(current) + " at "+ current);
            String identifier = x + " " + y;
            if (!cells.containsKey(identifier)) {
                cells.put(identifier, false);
            }
            if (cells.get(identifier)) {
                amplifier.addInputNumber(1);
            } else {
                amplifier.addInputNumber(0);
            }
            Queue<Long> outputs = new LinkedList<>();
            while (outputs.size() < 2 && amplifier.getList().get(current) != 99) {
                try {
                    amplifier.processInstruction(current);

                    if (amplifier.getOpcode() == 4) {
                        System.out.println("New output: " + amplifier.getOutput());
                        outputs.offer(amplifier.getOutput());
                    }
                    if (amplifier.hasInstructionPointerBeenModified()) {
                        current = amplifier.getInstructionPointer();
                        amplifier.resetInstructionPointer();
                    } else {
                        current += amplifier.getNumValues();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (amplifier.getList().get(current) == 99) {
                System.out.println("99 opcode");
                break;
            }
            long firstOutput = outputs.poll();
            if (firstOutput == 1) {
                System.out.println(cells.containsKey(identifier));
                cells.put(identifier, true);
            } else {
                cells.put(identifier, false);
            }

            long secondOutput = outputs.poll();
            if (secondOutput == 1) {
                direction = ((direction + 4) + 1) % 4;
            } else {
                direction = ((direction + 4) - 1) % 4;
            }
            switch (direction) {
                case 0 :
                    y++;
                    break;
                case 1 :
                    x++;
                    break;
                case 2:
                    y--;
                    break;
                case 3:
                    x--;
                    break;
            }
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
        }
        System.out.println("x: " + minX + "-" + maxX + " y: " + minY + "-" + maxY);

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                System.out.print((cells.containsKey(j + " " + i) && cells.get(j + " " + i)) ? "#" : " ");
            }
            System.out.println();
        }
        return cells.size();
    }

    private static List<List<Integer>> findValidPhaseSequence(int low, int high) {
        List<List<Integer>> phaseSequences = new ArrayList<>();
        for (int i = low; i < high; i++) {
            List<Integer> phaseSequence = new ArrayList<>();
            phaseSequence.add(i);
            for (int j = low; j < high; j++) {
                List<Integer> phaseSequence1 = new ArrayList<>(phaseSequence);
                if (!phaseSequence1.contains(j)) {
                    phaseSequence1.add(j);
                }
                for (int k = low; k < high; k++) {
                    List<Integer> phaseSequence2 = new ArrayList<>(phaseSequence1);
                    if (!phaseSequence2.contains(k)) {
                        phaseSequence2.add(k);
                    }
                    for (int l = low; l < high; l++) {
                        List<Integer> phaseSequence3 = new ArrayList<>(phaseSequence2);
                        if (!phaseSequence3.contains(l)) {
                            phaseSequence3.add(l);
                        }
                        for (int m = low; m < high; m++) {
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
