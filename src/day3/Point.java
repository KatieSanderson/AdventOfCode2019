package day3;

public class Point {

    private int xCoordinate;
    private int yCoordinate;
    private int step;

    public Point(int xCoordinate, int yCoordinate, int step) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.step = step;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getStep() {
        return step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (xCoordinate != point.xCoordinate) return false;
        return yCoordinate == point.yCoordinate;
    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }
}
