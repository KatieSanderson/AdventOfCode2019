package day10;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(FileSystems.getDefault().getPath("src", "day10", "input.txt")));

        int maxFound = 0;
        String coordsOfMax = "";
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                if (input.get(i).charAt(j) != '#') continue;
                int current = calculateVisiblePlanets(input, j, i);
                if (current > maxFound) {
                    maxFound = current;
                    coordsOfMax = i + " " + j;
                    if (current == 32) {

                        System.out.println(maxFound + " " + i + " " + j + " " + (input.get(i).charAt(j)));
                    }
                }
            }
        }
        System.out.println(maxFound + " at " + coordsOfMax);

    }

    private static int calculateVisiblePlanets(List<String> input, int x, int y) {
        MathContext mc = new MathContext(10);
        Set<BigDecimal> slopesContainingPlanets = new HashSet<>();
        for (int i = 0; i < input.get(0).length(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if ((i == x && j == y) || input.get(j).charAt(i) != '#') continue;
                BigDecimal xs = BigDecimal.valueOf(i).subtract(BigDecimal.valueOf(x));
                BigDecimal ys = BigDecimal.valueOf(j).subtract(BigDecimal.valueOf(y));
                if (xs.equals(BigDecimal.valueOf(0))) {
                    if (y > j) {
                        slopesContainingPlanets.add(BigDecimal.valueOf(Double.MIN_VALUE));
                    } else if (y < j) {
                        slopesContainingPlanets.add(BigDecimal.valueOf(Double.MIN_VALUE + 1));
                    }
                    continue;
                }
                if (ys.equals(BigDecimal.valueOf(0))) {
                    if (x > i) {
                        slopesContainingPlanets.add(BigDecimal.valueOf(Double.MIN_VALUE + 2));
                    } else if (x < i) {
                        slopesContainingPlanets.add(BigDecimal.valueOf(Double.MIN_VALUE + 3));
                    }
                    continue;
                }
                BigDecimal slope = ys.divide(xs, mc);
                if (i > x) {
                    slope = BigDecimal.valueOf(1000000).add(slope);
                }
                slopesContainingPlanets.add(slope);
            }
        }
        return slopesContainingPlanets.size();
    }
}
