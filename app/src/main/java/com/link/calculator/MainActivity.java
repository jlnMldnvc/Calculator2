package com.link.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.link.calculator.logic.CalculatorLogic;
import com.link.calculator.model.CalculatorModel;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Reference to the display TextView (View element)
    TextView calcDisplay;

    private final String[] SUPPORTED_OPERATORS = {"+", "-", "*", "/"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        calcDisplay = findViewById(R.id.resultTextView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Handles clicks for all numeric buttons (0-9) and the dot button
    public void handleNumericButtonClick(View v) {
        // Guard Clause: Stop if the display is too long
        if (calcDisplay.length() >= 30) {
            return;
        }
        // Guard Clause: Stop if the current number is too long
        int lastOpIndex = CalculatorLogic.getLastArithmeticIndex(calcDisplay);
        int currentNumberLength = calcDisplay.getText().length() - lastOpIndex;
        if (currentNumberLength > 8) {
            return;
        }

        String buttonText = ((Button) v).getText().toString();

        // Grouping all number buttons (1-9) for cleaner code
        switch (buttonText) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                calcDisplay.append(buttonText);
                return;

            // ZERO button logic - to prevent leading zeros like "00" but allow a single "0"
            case "0":

                //first check if some digits are entered
                if (calcDisplay.length() > 0) {

                    //get all individual numbers
                    String[] digits = calcDisplay.getText().toString().split("[-+*/]");

                    //check last number
                    String lastNumber = digits[digits.length - 1];

                    //no more than one zero in the beginning of number can exist
                    if (lastNumber.length() == 1) {
                        if (lastNumber.charAt(0) == '0') {
                            calcDisplay.append("0");
                        }
                    } else {
                        //if there is no digits entered, just write zero
                        calcDisplay.append("0");
                    }
                } else {
                    // Allow "0" if it's the first character
                    calcDisplay.append("0");
                }
                return;

            // DOT button logic - to allow only one dot per number
            case ".":

                if (calcDisplay.length() > 0) {

                    //get all entered numbers
                    String[] digits = calcDisplay.getText().toString().split("[-+*/]");

                    String lastNumber = digits[digits.length - 1];

                    //check if dot exists in the current number,
                    //dot can not be entered more than once
                    if (!lastNumber.contains(".")) {

                        // Also check that the last char is not an operator
                        if (!Arrays.asList(SUPPORTED_OPERATORS).contains(CalculatorLogic.getLastChar(calcDisplay))) {
                            calcDisplay.append(".");
                        }
                    }
                }
                break;
        }
    }

    // Handles clicks for all arithmetic buttons (+, -, *, /)
    public void handleArithmeticButtonClick(View v) {

        // Guard Clause: Stop if the display is too long
        if (calcDisplay.length() >= 30) {
            return;
        }
        // Guard Clause: Prevent adding a new operator if the last char is already an operator
        if (calcDisplay.length() > 0 && Arrays.asList(SUPPORTED_OPERATORS).contains(CalculatorLogic.getLastChar(calcDisplay))) {
            return;
        }

        String buttonText = ((Button) v).getText().toString();

        // If the display is empty, only allow "-" or "+"
        //divide and multiply can not be at the beginning
        if (calcDisplay.length() == 0 && (buttonText.equals("*") || buttonText.equals("/"))) {
            return;
        }
        // If the display is not empty, allow any operator
        calcDisplay.append(buttonText);
    }

    // This method removes the last character from the display.
    public void handleClearButtonClick(View v) {
        CharSequence existingText = calcDisplay.getText();
        if (existingText.length() > 0) {
            calcDisplay.setText(existingText.subSequence(0, existingText.length() - 1));
        }
    }

    // Handles the click for the equals "=" button
    public void handleEqualsButtonClick(View v) {
        // Guard Clause: Do nothing if display is empty or ends with an operator
        if (calcDisplay.getText().length() == 0 || Arrays.asList(SUPPORTED_OPERATORS).contains(CalculatorLogic.getLastChar(calcDisplay))) {
            return;
        }
        String result = CalculatorModel.evaluateExpression(calcDisplay.getText().toString());
        calcDisplay.setText(result);
    }
}