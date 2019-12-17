package day12;

public class Moon {

    private int xPosition;
    private int yPosition;
    private int zPosition;

    private int xVelocity;
    private int yVelocity;
    private int zVelocity;

    Moon(int xPosition, int yPosition, int zPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;

        xVelocity = 0;
        yVelocity = 0;
        zVelocity = 0;
    }

    static void applyGravity(Moon moon1, Moon moon2) {
        if (moon1.xPosition > moon2.xPosition) {
            moon2.xVelocity++;
            moon1.xVelocity--;
        } else if (moon1.xPosition < moon2.xPosition) {
            moon2.xVelocity--;
            moon1.xVelocity++;
        }
        if (moon1.yPosition > moon2.yPosition) {
            moon2.yVelocity++;
            moon1.yVelocity--;
        } else if (moon1.yPosition < moon2.yPosition) {
            moon2.yVelocity--;
            moon1.yVelocity++;
        }
        if (moon1.zPosition > moon2.zPosition) {
            moon2.zVelocity++;
            moon1.zVelocity--;
        } else if (moon1.zPosition < moon2.zPosition) {
            moon2.zVelocity--;
            moon1.zVelocity++;
        }
    }

    int calculateEnergy() {
        return calculateKineticEnergy() * calculatePotentialEnergy();
    }

    int calculatePotentialEnergy() {
        return Math.abs(xPosition) + Math.abs(yPosition) + Math.abs(zPosition);
    }

    int calculateKineticEnergy() {
        return Math.abs(xVelocity) + Math.abs(yVelocity) + Math.abs(zVelocity);
    }

    void applyVelocity() {
        xPosition += xVelocity;
        yPosition += yVelocity;
        zPosition += zVelocity;
    }
}
