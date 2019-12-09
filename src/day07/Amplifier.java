package day07;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Amplifier {
    // program inputs
    private final List<Integer> list;
    private final Queue<Integer> inputNumbers;
    private Amplifier nextAmplifier;

    // instruction
    private int current;
    private int opcode;
    private int[] parameterModes;
    private int[] parameters;

    // program outputs
    private int output;
    private int instructionPointer;
    private boolean hasInstructionPointerBeenModified;

    Amplifier(List<Integer> list) {
        this.list = list;
        inputNumbers = new LinkedList<>();
    }

    void processInstruction(int current) throws InterruptedException {
        this.current = current;
        // opcode is stored in right two digits of instruction's first value
        opcode = list.get(current) % 100;

        if (opcode == 3 || opcode == 4) {
            parameterModes = new int[1];
            parameters = new int[1];
        } else if (opcode == 5 || opcode == 6) {
            parameterModes = new int[2];
            parameters = new int[2];
        } else if (opcode == 1 || opcode == 2 || opcode == 7 || opcode == 8) {
            parameterModes = new int[3];
            parameters = new int[3];
        } else if (opcode == 99) {
            // opcode 99 should never be executed; is included to show is valid opcode
            parameterModes = new int[0];
            parameters = new int[0];
        } else {
            throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }

        parseParameterModes();
        parseParameters();

        switch (opcode) {
            case 1 :
                int firstParameter = getParameterByMode(0);
                int secondParameter = getParameterByMode(1);
                int nextValue = firstParameter + secondParameter;
                list.set(parameters[2], nextValue);
                break;
            case 2 :
                firstParameter = getParameterByMode(0);
                secondParameter = getParameterByMode(1);
                nextValue = firstParameter * secondParameter;
                list.set(parameters[2], nextValue);
                break;
            case 3 :
                // wait for added inputs if no inputs for this Amplifier
                synchronized (this) {
                    while (inputNumbers.isEmpty()) {
                        wait();
                    }
                }
                list.set(parameters[0], inputNumbers.poll());
                break;
            case 4 :
                output = getParameterByMode(0);
                synchronized (nextAmplifier) {
                    nextAmplifier.addInputNumber(output);
                    if (nextAmplifier.inputNumbers.size() == 1) {
                        nextAmplifier.notify();
                    }
                }
                break;
            case 5 :
                if (getParameterByMode(0) != 0) {
                    instructionPointer = getParameterByMode(1);
                    hasInstructionPointerBeenModified = true;
                }
                break;
            case 6 :
                if (getParameterByMode(0) == 0) {
                    instructionPointer = getParameterByMode(1);
                    hasInstructionPointerBeenModified = true;
                }
                break;
            case 7 :
                int val = getParameterByMode(0) < getParameterByMode(1) ? 1 : 0;
                list.set(parameters[2], val);
                break;
            case 8 :
                val = getParameterByMode(0) == getParameterByMode(1) ? 1 : 0;
                list.set(parameters[2], val);
                break;
            case 99 :
                // does nothing
                break;
            default :
                throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }
    }

    private int getParameterByMode(int i) {
        // mode == 0 -> position mode
        // mode == 1 -> immediate mode
        return parameterModes[i] == 0 ? list.get(parameters[i]) : parameters[i];
    }

    private void parseParameterModes() {
        // remove last 2 digits - they contain the opcode
        int modes = list.get(current) / 100;
        for (int i = 0; i < parameterModes.length; i++) {
            parameterModes[i] = modes % 10;
            modes /= 10;
        }
    }

    private void parseParameters() {
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = list.get(current + 1 + i);
        }
    }

    void addInputNumber(int input) {
        inputNumbers.offer(input);
    }

    boolean hasInstructionPointerBeenModified() {
        return hasInstructionPointerBeenModified;
    }

    void resetInstructionPointer() {
        hasInstructionPointerBeenModified = false;
    }

    int getInstructionPointer() {
        return instructionPointer;
    }

    int getNumValues() {
        return 1 + parameters.length;
    }

    List<Integer> getList() {
        return list;
    }

    int getOutput() {
        return output;
    }

    void setNextAmplifier(Amplifier amplifier) {
        nextAmplifier = amplifier;
    }
}
