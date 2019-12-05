package day5;

import java.util.List;

class Instruction {

    private static int inputNumber;

    private int[] parameterModes;
    private int[] parameters;
    private int opcode;
    private int originalInstruction;
    private int output;

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

    private void processParameters(List<Integer> copyList, int current) {
        parameters = new int[parameterModes.length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = copyList.get(current + i);
        }
        switch (opcode) {
            case 1 :
                int firstParameter = getParameterByMode(copyList, 0);
                int secondParameter = getParameterByMode(copyList, 1);
                int nextValue = firstParameter + secondParameter;
                copyList.set(parameters[2], nextValue);
                break;
            case 2 :
                firstParameter = getParameterByMode(copyList, 0);
                secondParameter = getParameterByMode(copyList, 1);
                nextValue = firstParameter * secondParameter;
                copyList.set(parameters[2], nextValue);
                break;
            case 3 :
                copyList.set(parameters[0], inputNumber);
                break;
            case 4 :
                output = copyList.get(parameters[0]);
                break;
            default :
                throw new IllegalArgumentException("Unknown opcode [" + opcode + "]");
        }
    }

    private int getParameterByMode(List<Integer> copyList, int i) {
        // mode == 0 -> position mode
        // mode == 1 -> immediate mode
        return parameterModes[i] == 0 ? copyList.get(parameters[i]) : parameters[i];
    }

    private void parseParameterModes() {
        switch (opcode) {
            case 1 :
                parameterModes = new int[3];
                break;
            case 2 :
                parameterModes = new int[3];
                break;
            case 3 :
                parameterModes = new int[1];
                break;
            case 4 :
                parameterModes = new int[1];
                break;
            default :
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

    int getNumMetadata() {
        return 1 + parameters.length;
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
