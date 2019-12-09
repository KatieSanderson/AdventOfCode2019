package day06;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

public class Day6 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(FileSystems.getDefault().getPath("src", "day06", "input.txt"));
        Map<String, OrbitObject> objectMap = new HashMap<>();

        for (String inputLine : input) {
            String[] objectArr = inputLine.split("\\)");
            String parentString = objectArr[0];
            String childString = objectArr[1];
            OrbitObject parentObject;
            OrbitObject childObject;
            if (!objectMap.containsKey(parentString)) {
                parentObject = new OrbitObject(parentString);
                objectMap.put(parentString, parentObject);
            } else {
                parentObject = objectMap.get(parentString);
            }
            if (!objectMap.containsKey(childString)) {
                childObject = new OrbitObject(childString);
                objectMap.put(childString, childObject);
            } else {
                childObject = objectMap.get(childString);
            }
            parentObject.addChild(childObject);
            childObject.addParent(parentObject);
        }

        int orbitCount = 0;
        OrbitObject comObject = objectMap.get("COM");
        for (Map.Entry<String, OrbitObject> entry : objectMap.entrySet()) {
            int currentOrbitCount = 0;
            OrbitObject currentObject = entry.getValue();
            while (currentObject != comObject) {
                currentOrbitCount++;
                currentObject = currentObject.getParentObject();
            }
            orbitCount += currentOrbitCount;
        }
        System.out.println("Part 1: " + orbitCount);

        PriorityQueue<OrbitObject> priorityQueue = new PriorityQueue<>(new OrbitObjectComparator());
        Set<OrbitObject> evaluatedObjects = new HashSet<>();
        OrbitObject parentObject = objectMap.get("YOU").getParentObject();
        objectMap.get("YOU").setDistanceFromYou(0);
        parentObject.setDistanceFromYou(0);
        priorityQueue.add(parentObject);

        OrbitObject santaObject = objectMap.get("SAN");
        while (!priorityQueue.isEmpty()) {
            OrbitObject orbitObject = priorityQueue.poll();
            if (orbitObject.equals(santaObject)) {
                System.out.println("Part 2: " + orbitObject.getDistanceFromYou());
                return;
            }
            int currentDistance = orbitObject.getDistanceFromYou();
            for (OrbitObject child : orbitObject.getChildren()) {
                if (!evaluatedObjects.contains(child)) {
                    child.setDistanceFromYou(currentDistance + 1);
                    priorityQueue.offer(child);
                    evaluatedObjects.add(child);
                }
            }
            OrbitObject parentOfOrbitObject = orbitObject.getParentObject();
            if (!evaluatedObjects.contains(parentOfOrbitObject)) {
                parentOfOrbitObject.setDistanceFromYou(currentDistance + 1);
                priorityQueue.offer(parentOfOrbitObject);
                evaluatedObjects.add(parentOfOrbitObject);
            }
        }
    }
}
