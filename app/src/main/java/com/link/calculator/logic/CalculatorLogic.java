package com.link.calculator.logic;

import android.widget.TextView;

public class CalculatorLogic {

    // Helper method to get the last character from the display
    public static String getLastChar(TextView calcDisplay) {
        String lastChar = "";

        if (calcDisplay != null) {
            if (calcDisplay.getText().length() == 0)
                lastChar = "";
            else
                lastChar = calcDisplay.getText().subSequence(
                        calcDisplay.getText().length() - 1,
                        calcDisplay.getText().length()).toString();
        }
        return lastChar;
    }

    // Helper method for getting the index of the last arithmetic char (+, -, *, /)
    public static int getLastArithmeticIndex(TextView calcDisplay) {
        //convert calc display text to string
        String displayText = calcDisplay.getText().toString();

        // Find the last index of each operator and return the highest one.
        int lastPlus = displayText.lastIndexOf('+');
        int lastMinus = displayText.lastIndexOf('-');
        int lastMultiply = displayText.lastIndexOf('*');
        int lastDivide = displayText.lastIndexOf('/');

        // Return the maximum of the found indices.
        // return value will be -1, if there is no arithmetic sign
        return Math.max(
                Math.max(lastPlus, lastMinus),
                Math.max(lastMultiply, lastDivide));
    }
}
