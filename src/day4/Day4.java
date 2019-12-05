package day4;

public class Day4 {

    public static void main(String[] args) {
        int low = 347312;
        int high = 805915;
        int current = low;
        int count = 0;
        while (current <= high) {
            if (containsTwoSameAdjacents(current) && isDecreasing(current)) {
                count++;
            }
            current++;
        }
        System.out.println("Part 1: " + count);
    }

    private static boolean containsTwoSameAdjacents(int val) {
        // initialized to non 0-9 value
        int previous = 10;
        while (val > 0) {
            int current = val % 10;
            if (current == previous) {
                return true;
            }
            previous = current;
            val /= 10;
        }
        return false;
    }

    private static boolean isDecreasing(int val) {
        int maxNext = 9;
        while (val > 0) {
            int current = val % 10;
            if (current > maxNext) {
                return false;
            }
            maxNext = current;
            val /= 10;
        }
        return true;
    }


}
