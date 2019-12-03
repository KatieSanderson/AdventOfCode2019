package day3;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day3", "input.txt"));
        List<String> wireInput1 = List.of(input.get(0).split(","));
        List<String> wireInput2 = List.of(input.get(1).split(","));

        Set<Point> wireSet1 = new HashSet<>();
        parseInstructions(wireSet1, wireInput1);

        Set<Point> wireSet2 = new HashSet<>();
        parseInstructions(wireSet2, wireInput2);

        Set<Point> intersection1 = wireSet1.stream()
                .filter(wireSet2::contains)
                .collect(Collectors.toSet());

        Set<Point> intersection2 = wireSet2.stream()
                .filter(wireSet1::contains)
                .collect(Collectors.toSet());

        System.out.println("Part 1: " + findMinManhattanDistanceByDistance(intersection1));
        System.out.println("Part 2: " + findMinManhattanDistanceByStep(intersection1, intersection2));
    }

    private static int findMinManhattanDistanceByStep(Set<Point> wireSet1, Set<Point> wireSet2) {
        int minManhattanDistance = Integer.MAX_VALUE;
        for (Point p1 : wireSet1) {
            for (Point p2 : wireSet2) {
                if (p1.equals(p2)) {
                    minManhattanDistance = Math.min(minManhattanDistance, p1.getStep() + p2.getStep());
                }
            }
        }
        return minManhattanDistance;
    }

    private static int findMinManhattanDistanceByDistance(Set<Point> intersection) {
        int minManhattanDistance = Integer.MAX_VALUE;
        for (Point p : intersection) {
            minManhattanDistance = Math.min(minManhattanDistance, Math.abs(p.getxCoordinate()) + Math.abs(p.getyCoordinate()));
        }
        return minManhattanDistance;
    }

    private static void parseInstructions(Set<Point> set, List<String> input) {
        int currentX = 0;
        int currentY = 0;
        int step = 0;
        for (String str : input) {
            char direction = str.charAt(0);
            int count = Integer.parseInt(str.substring(1));

            switch (direction) {
                case 'R' :
                    for (int i = 0; i < count; i++) {
                        currentX++;
                        step++;
                        set.add(new Point(currentX, currentY, step));
                    }
                    break;
                case 'L' :
                    for (int i = 0; i < count; i++) {
                        currentX--;
                        step++;
                        set.add(new Point(currentX, currentY, step));
                    }
                    break;
                case 'U' :
                    for (int i = 0; i < count; i++) {
                        currentY++;
                        step++;
                        set.add(new Point(currentX, currentY, step));
                    }
                    break;
                case 'D' :
                    for (int i = 0; i < count; i++) {
                        currentY--;
                        step++;
                        set.add(new Point(currentX, currentY, step));
                    }
                    break;
                default :
                    throw new IllegalArgumentException("Unknown direction [" + direction + "]");
            }
        }
    }
}
