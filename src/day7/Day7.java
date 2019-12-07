package day7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Integer> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day7", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(Integer::parseInt).collect(Collectors.toList());

        List<List<Integer>> phaseSequencesP1 = findValidPhaseSequence(0, 5);
        List<List<Integer>> phaseSequencesP2 = findValidPhaseSequence(5, 10);

        System.out.println("Part 1: " + findMaxOutputFromPhases(input, phaseSequencesP1));
        System.out.println("Part 2: " + findMaxOutputFromPhases(input, phaseSequencesP2));
    }

    private static int findMaxOutputFromPhases(List<Integer> input, List<List<Integer>> phaseSequences) throws InterruptedException {
        int maxOutput = Integer.MIN_VALUE;
        for (List<Integer> phaseSequence : phaseSequences) {
            // initialize amplifiers with initial input (phase)
            List<Amplifier> amplifiers = new ArrayList<>();
            for (int phase : phaseSequence) {
                Amplifier amplifier = new Amplifier(new ArrayList<>(input));
                amplifier.addInputNumber(phase);
                amplifiers.add(amplifier);
            }
            amplifiers.get(0).addInputNumber(0);

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
