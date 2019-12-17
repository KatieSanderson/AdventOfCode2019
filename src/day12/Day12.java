package day12;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {

    public static void main(String[] args) throws IOException {
        List<Moon> moons = Files.readAllLines(FileSystems.getDefault().getPath("src", "day12", "input.txt")).stream()
                .flatMap(str -> Arrays.stream(str.split(">")))
                .map(str -> {
                    String[] roughSplit = str.split(",");
                    Pattern pattern = Pattern.compile(".*=(-?\\d+).*");
                    Matcher matcherX = pattern.matcher(roughSplit[0]);
                    Matcher matcherY = pattern.matcher(roughSplit[1]);
                    Matcher matcherZ = pattern.matcher(roughSplit[2]);
                    matcherX.matches();
                    matcherY.matches();
                    matcherZ.matches();
                    int x = Integer.parseInt(matcherX.group(1));
                    int y = Integer.parseInt(matcherY.group(1));
                    int z = Integer.parseInt(matcherZ.group(1));
                    return new Moon(x, y, z);
                }).collect(Collectors.toList());


        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < moons.size(); j++) {
                for (int k = j + 1; k < moons.size(); k++){
                    Moon.applyGravity(moons.get(j), moons.get(k));
                }
            }
            for (Moon moon : moons) {
                moon.applyVelocity();
            }
        }

        int sumEnergy = 0;
        for (Moon moon : moons) {
            sumEnergy += moon.calculateEnergy();
        }

        System.out.println("Part 1: " + (sumEnergy));
    }
}
