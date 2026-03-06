package com.link.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class CalculatorModel {

    static float finalResult;

    public static void calculateAll(List<Float> numbers, List<String> operations) {

        if (numbers.size() == 1 || operations.isEmpty()) {
            finalResult = numbers.get(0);
            return;
        }

        float result = 0;
        int index = -1;

        int indexMultiply = operations.indexOf("*");
        int indexDivide = operations.indexOf("/");

        if (indexMultiply != -1 && indexDivide != -1) {
            if (indexMultiply < indexDivide) {
                result += numbers.get(indexMultiply) * numbers.get(indexMultiply + 1);
                index = indexMultiply;
            } else {
                result += numbers.get(indexDivide) / numbers.get(indexDivide + 1);
                index = indexDivide;
            }
            updateLists(numbers, operations, index, result);
            calculateAll(numbers, operations);
            return;
        }
        if (indexMultiply != -1) {
            result += numbers.get(indexMultiply) * numbers.get(indexMultiply + 1);
            updateLists(numbers, operations, indexMultiply, result);
            calculateAll(numbers, operations);
            return;
        }
        if (indexDivide != -1) {
            result += numbers.get(indexDivide) / numbers.get(indexDivide + 1);
            updateLists(numbers, operations, indexDivide, result);
            calculateAll(numbers, operations);
            return;
        }
        int indexPlus = operations.indexOf("+");
        int indexMinus = operations.indexOf("-");

        if (indexMinus != -1 && indexPlus != -1) {
            if (indexPlus < indexMinus) {
                result += numbers.get(indexPlus) + numbers.get(indexPlus + 1);
                index = indexPlus;
            } else {
                result += numbers.get(indexMinus) - numbers.get(indexMinus + 1);
                index = indexMinus;
            }
            updateLists(numbers, operations, index, result);
            calculateAll(numbers, operations);
            return;
        }
        if (indexPlus != -1) {
            result += numbers.get(indexPlus) + numbers.get(indexPlus + 1);
            updateLists(numbers, operations, indexPlus, result);
            calculateAll(numbers, operations);
            return;
        }
        if (indexMinus != -1) {
            result += numbers.get(indexMinus) - numbers.get(indexMinus + 1);
            updateLists(numbers, operations, indexMinus, result);
            calculateAll(numbers, operations);
        }
    }

    // Helper method for clean code (DRY principle)
    private static void updateLists(List<Float> numbers, List<String> operations, int index, float result) {
        numbers.set(index, result);
        numbers.remove(index + 1);
        operations.remove(index);
    }


    /**
     * Evaluates the entire mathematical expression from the display.
     * Those methods are self-contained, and it follows the order of operations.
     */
    public static String evaluateExpression(String expression) {

        //get all entered numbers in string format

        // Handle unary operators at the start by prefixing a zero (e.g., "-5" becomes "0-5")
        // for easier parsing
        if (expression.charAt(0) == '+' || expression.charAt(0) == '-') {
            expression = 0 + expression;
        }

        // Split expression by any operator to extract operands (numbers)
        String[] numbers = expression.split("[-+*/=]");

        //parse all operations
        List<String> operationList = new ArrayList<>();
        for (int i = 0; i < expression.length() - 1; i++) {
            char charAt = expression.charAt(i);
            if ("+-*/".contains(String.valueOf(charAt))) {
                operationList.add(String.valueOf(charAt));
            }
        }

        //parse all string numbers to float ones
        List<Float> numberList = new ArrayList<>();
        for (String number : numbers) {

            if (number.isEmpty()) continue;

            if (number.equals("-Infinity")) {
                numberList.add(Float.NEGATIVE_INFINITY);
            } else if (number.equals("Infinity")) {
                numberList.add(Float.POSITIVE_INFINITY);
            } else {
                try {
                    numberList.add(Float.parseFloat(number));
                } catch (Exception exc) {
                    return "ERROR";
                }
            }
        }
        calculateAll(numberList, operationList);
        return Float.toString(finalResult);
    }
}
