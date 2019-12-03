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

        Set<Point> intersection = wireSet1.stream()
                .filter(wireSet2::contains)
                .collect(Collectors.toSet());
        intersection.remove(new Point(0, 0));

        System.out.println(findMinManhattanDistance(intersection));
    }

    private static int findMinManhattanDistance(Set<Point> intersection) {
        int minManhattanDistance = Integer.MAX_VALUE;
        for (Point p : intersection) {
//            System.out.println(minManhattanDistance + " " + (p.getyCoordinate() + p.getxCoordinate()));
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
//            System.out.println(direction + " " + count);
//            System.out.println(currentX + " Y : " + currentY);
            switch (direction) {
                case 'R' :
                    for (int i = 0; i < count; i++) {
                        set.add(new Point(currentX, currentY, step));
                        currentX++;
                    }
                    break;
                case 'L' :
                    for (int i = 0; i < count; i++) {
                        set.add(new Point(currentX, currentY, step));
                        currentX--;
                    }
                    break;
                case 'U' :
                    for (int i = 0; i < count; i++) {
                        set.add(new Point(currentX, currentY, step));
                        currentY++;
                    }
                    break;
                case 'D' :
                    for (int i = 0; i < count; i++) {
                        set.add(new Point(currentX, currentY, step));
                        currentY--;
                    }
                    break;
                default :
                    throw new IllegalArgumentException("Unknown direction [" + direction + "]");
            }
        }
    }
}
