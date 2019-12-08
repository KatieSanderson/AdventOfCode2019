package day8;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day8", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split("")))
                .map(Integer::parseInt).collect(Collectors.toList());

        int pixelWidth = 25;
        int pixelHeight = 6;
        int pixelsPerLayer = pixelHeight * pixelWidth;
        int numLayers = input.size() / pixelsPerLayer;

        int minZeroes = Integer.MAX_VALUE;
        int output = 0;

        List<List<Integer>> layers = new ArrayList<>();
        for (int i = 0; i < numLayers; i++) {
            List<Integer> layer = input.subList(i * pixelsPerLayer, (i + 1) * pixelsPerLayer);
            int numZeroes = 0;
            int numOnes = 0;
            int numTwos = 0;
            for (int j : layer) {
                numZeroes += j == 0 ? 1 : 0;
                numOnes += j == 1 ? 1 : 0;
                numTwos += j == 2 ? 1 : 0;
            }
            if (numZeroes < minZeroes) {
                output = numOnes * numTwos;
                minZeroes = numZeroes;
            }
            layers.add(layer);
        }
        System.out.println("Part 1: " + output);

        System.out.println("Part 2:");
        List<Integer> color = new ArrayList<>(layers.get(0));
        for (int i = 0; i < layers.get(0).size(); i++) {
            for (List<Integer> layer : layers) {
                if (color.get(i) == 2) {
                    color.set(i, layer.get(i));
                }
            }
        }
        for (int i = 0; i < pixelHeight; i++) {
            for (int j = 0; j < pixelWidth; j++) {
                System.out.print(color.get(j) == 1 ? "#" : " ");
            }
            color.subList(0, pixelWidth).clear();
            System.out.println();
        }
    }
}
