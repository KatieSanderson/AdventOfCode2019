package day09;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Long> input = (ArrayList<Long>) Files.readAllLines(FileSystems.getDefault().getPath("src", "day09", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Long::parseLong).collect(Collectors.toList());
//        input = (ArrayList<Long>) List.of(109L,1L,204L,-1L,1001L,100L,1L,100L,1008L,100L,16L,101L,1006L,101L,0L,99L);
        ensureSize(input, input.size() + 2000);

        List<List<Integer>> phaseSequencesP1 = findValidPhaseSequence(0, 5);
        List<List<Integer>> phaseSequencesP2 = findValidPhaseSequence(5, 10);
//        List<List<Integer>> phaseSequencesP1 = List.of(List.of(0, 1, 2, 3, 4));
//
        System.out.println("Part 1: " + findMaxOutputFromPhases(input, phaseSequencesP1));
    }

    private static void ensureSize(ArrayList<Long> list, int size) {
        // Prevent excessive copying while we're adding
        list.ensureCapacity(size);
        while (list.size() < size) {
            list.add(0L);
        }
    }

    private static long findMaxOutputFromPhases(List<Long> input, List<List<Integer>> phaseSequences) throws InterruptedException {
        long maxOutput = Long.MIN_VALUE;
        for (List<Integer> phaseSequence : phaseSequences) {
            // initialize amplifiers with initial input (phase)
            List<Amplifier> amplifiers = new ArrayList<>();
            for (int phase : phaseSequence) {
                Amplifier amplifier = new Amplifier(new ArrayList<>(input));
                amplifier.addInputNumber(phase);
                amplifiers.add(amplifier);
            }
            amplifiers.get(0).addInputNumber(1);

            // connect amplifiers (in circle: A -> B, B -> C, ..., E -> A)
            for (int i = 0; i < amplifiers.size() - 1; i++) {
                amplifiers.get(i).setNextAmplifier(amplifiers.get(i + 1));
            }
            amplifiers.get(amplifiers.size() - 1).setNextAmplifier(amplifiers.get(0));

            // start threads
            List<Thread> threadPool = new ArrayList<>();
            for (Amplifier amplifier : amplifiers) {
                Thread thread = new Thread(new AmplifierRunnable(amplifier));
                thread.start();
                threadPool.add(thread);
            }

            // join on the last thread, which signals programs are finished
            threadPool.get(amplifiers.size() - 1).join();
            maxOutput = Math.max(maxOutput, amplifiers.get(amplifiers.size() - 1).getOutput());
        }
        return maxOutput;
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
