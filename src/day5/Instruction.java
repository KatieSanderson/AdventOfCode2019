package day5;

import java.util.List;

class Instruction {

    private static int inputNumber;

    private int[] parameterModes;
    private int[] parameters;
    private int opcode;
    private int originalInstruction;
    private int output;
    private int instructionPointer;
    private boolean hasInstructionPointerBeenModified;

    private Instruction() {}

    static Instruction createInstruction(List<Integer> copyList, int current) {
        Instruction instruction = new Instruction();

        instruction.originalInstruction = copyList.get(current);

        // opcode is stored in right two digits of instruction's first value
        instruction.opcode = copyList.get(current) % 100;
        instruction.parseParameterModes();
        instruction.processParameters(copyList, current + 1);

        return instruction;
    }

    private void processParameters(List<Integer> list, int current) {
        parameters = new int[parameterModes.length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = list.get(current + i);
        }

        switch (opcode) {
            case 1 :
                int firstParameter = getParameterByMode(list, 0);
                int secondParameter = getParameterByMode(list, 1);
                int nextValue = firstParameter + secondParameter;
                list.set(parameters[2], nextValue);
                break;
            case 2 :
                firstParameter = getParameterByMode(list, 0);
                secondParameter = getParameterByMode(list, 1);
                nextValue = firstParameter * secondParameter;
                list.set(parameters[2], nextValue);
                break;
            case 3 :
                list.set(parameters[0], inputNumber);
                break;
            case 4 :
                output = getParameterByMode(list, 0);
                break;
            case 5 :
                if (getParameterByMode(list, 0) != 0) {
                    instructionPointer = parameters[1];
                    hasInstructionPointerBeenModified = true;
                }
                break;
            case 6 :
                if (getParameterByMode(list, 0) == 0) {
                    instructionPointer = parameters[1];
                    hasInstructionPointerBeenModified = true;
                }
                break;
            case 7 :
                int val = getParameterByMode(list, 0) < getParameterByMode(list, 1) ? 1 : 0;
                list.set(parameters[2], val);
                break;
            case 8 :
                val = getParameterByMode(list, 0) == getParameterByMode(list, 1) ? 1 : 0;
                list.set(parameters[2], val);
                break;
            default :
                throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }
    }

    private int getParameterByMode(List<Integer> list, int i) {
        // mode == 0 -> position mode
        // mode == 1 -> immediate mode
        return parameterModes[i] == 0 ? list.get(parameters[i]) : parameters[i];
    }

    private void parseParameterModes() {
        if (opcode == 3 || opcode == 4) {
            parameterModes = new int[1];
        } else if (opcode == 5 || opcode == 6) {
            parameterModes = new int[2];
        } else if (opcode == 1 || opcode == 2 || opcode == 7 || opcode == 8) {
            parameterModes = new int[3];
        } else {
            throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }
        // remove last 2 digits - they contain the opcode
        int modes = originalInstruction / 100;
        for (int i = 0; i < parameterModes.length; i++) {
            int nextMode = modes % 10;
            modes /= 10;
            parameterModes[i] = nextMode;
        }
    }

    int getInstructionPointer() {
        if (hasInstructionPointerBeenModified) {
            return instructionPointer;
        } else {
            return 1 + parameters.length;
        }
    }

    int getOutput() {
        return output;
    }

    static void setInputNumber(int inputNumber) {
        Instruction.inputNumber = inputNumber;
    }

    int getOpcode() {
        return opcode;
    }
}
