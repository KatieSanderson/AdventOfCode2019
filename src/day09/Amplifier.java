package day09;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Amplifier {
    // program inputs
    private final ArrayList<Long> list;
    private final Queue<Long> inputNumbers;
    private Amplifier nextAmplifier;
    private int relativePosition;

    // instruction
    private int current;
    private int opcode;
    private int[] parameterModes;
    private long[] parameters;

    // program outputs
    private long output;
    private int instructionPointer;
    private boolean hasInstructionPointerBeenModified;

    Amplifier(ArrayList<Long> list) {
        this.list = list;
        inputNumbers = new LinkedList<>();
    }

    void processInstruction(int current) throws InterruptedException {
        this.current = current;
        // opcode is stored in right two digits of instruction's first value
        opcode = Math.toIntExact(list.get(current)) % 100;

        if (opcode == 3 || opcode == 4 || opcode == 9) {
            parameterModes = new int[1];
            parameters = new long[1];
        } else if (opcode == 5 || opcode == 6) {
            parameterModes = new int[2];
            parameters = new long[2];
        } else if (opcode == 1 || opcode == 2 || opcode == 7 || opcode == 8) {
            parameterModes = new int[3];
            parameters = new long[3];
        } else if (opcode == 99) {
            // opcode 99 should never be executed; is included to show is valid opcode
            parameterModes = new int[0];
            parameters = new long[0];
        } else {
            throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }

        parseParameterModes();
        parseParameters();

        switch (opcode) {
            case 1 :
                long firstParameter = getParameterByMode(0);
                long secondParameter = getParameterByMode(1);
                long nextValue = firstParameter + secondParameter;
                if (parameterModes[2] == 2) {
                    list.set(Math.toIntExact(parameters[2] + relativePosition), nextValue);
                } else if (parameterModes[2] == 0) {
                    list.set(Math.toIntExact(parameters[2]), nextValue);
                }
                break;
            case 2 :
                firstParameter = getParameterByMode(0);
                secondParameter = getParameterByMode(1);
                nextValue = firstParameter * secondParameter;
                if (parameterModes[2] == 2) {
                    list.set(Math.toIntExact(parameters[2] + relativePosition), nextValue);
                } else if (parameterModes[2] == 0) {
                    list.set(Math.toIntExact(parameters[2]), nextValue);
                }
                break;
            case 3 :
                // wait for added inputs if no inputs for this Amplifier
                synchronized (this) {
                    while (inputNumbers.isEmpty()) {
                        wait();
                    }
                }
                if (parameterModes[0] == 2) {
                    list.set(Math.toIntExact(parameters[0] + relativePosition), inputNumbers.poll());
                } else if (parameterModes[0] == 0) {
                    list.set(Math.toIntExact(parameters[0]), inputNumbers.poll());
                }
                break;
            case 4 :
                output = getParameterByMode(0);
                synchronized (nextAmplifier) {
                    nextAmplifier.addInputNumber(output);
                    if (nextAmplifier.inputNumbers.size() <= 1) {
                        nextAmplifier.notify();
                    }
                }
                break;
            case 5 :
                if (getParameterByMode(0) != 0) {
                    instructionPointer = Math.toIntExact(getParameterByMode(1));
                    hasInstructionPointerBeenModified = true;
                }
                break;
            case 6 :
                if (getParameterByMode(0) == 0) {
                    instructionPointer = Math.toIntExact(getParameterByMode(1));
                    hasInstructionPointerBeenModified = true;
                }
                break;
            case 7 :
                long val = getParameterByMode(0) < getParameterByMode(1) ? 1 : 0;
                if (parameterModes[2] == 2) {
                    list.set(Math.toIntExact(parameters[2] + relativePosition), val);
                } else if (parameterModes[2] == 0) {
                    list.set(Math.toIntExact(parameters[2]), val);
                }
                break;
            case 8 :
                val = getParameterByMode(0) == getParameterByMode(1) ? 1 : 0;
                if (parameterModes[2] == 2) {
                    list.set(Math.toIntExact(parameters[2] + relativePosition), val);
                } else if (parameterModes[2] == 0) {
                    list.set(Math.toIntExact(parameters[2]), val);
                }
                break;
            case 9 :
                relativePosition += getParameterByMode(0);
                break;
            case 99 :
                // does nothing
                break;
            default :
                throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }
    }

    private long getParameterByMode(int i) {
        // mode == 0 -> position mode
        // mode == 1 -> immediate mode
        // mode == 2 -> relative mode
        switch (parameterModes[i]) {
            case 0 :
                return list.get(Math.toIntExact(parameters[i]));
            case 1 :
                return parameters[i];
            case 2 :
                return list.get(Math.toIntExact(parameters[i]) + relativePosition);
            default :
                throw new RuntimeException();
        }
    }

    private void parseParameterModes() {
        // remove last 2 digits - they contain the opcode
        int modes = Math.toIntExact(list.get(current)) / 100;
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

    void addInputNumber(long input) {
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

    List<Long> getList() {
        return list;
    }

    void setNextAmplifier(Amplifier amplifier) {
        nextAmplifier = amplifier;
    }

    public long getOutput() {
        return output;
    }
}
