package day07;

public class AmplifierRunnable implements Runnable {

    private final Amplifier amplifier;

    AmplifierRunnable(Amplifier amplifier) {
        this.amplifier = amplifier;
    }

    @Override
    public void run() {
        int current = 0;
        while (amplifier.getList().get(current) != 99) {
            try {
                amplifier.processInstruction(current);

                if (amplifier.hasInstructionPointerBeenModified()) {
                    current = amplifier.getInstructionPointer();
                    amplifier.resetInstructionPointer();
                } else {
                    current += amplifier.getNumValues();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
