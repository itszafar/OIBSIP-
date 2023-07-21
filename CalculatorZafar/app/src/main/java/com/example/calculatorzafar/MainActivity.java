package com.example.calculatorzafar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String currentNumber = "";
    private double result = 0;
    private char operator = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        // Set click listeners for the calculator buttons
        setClickListenersForButtons();
    }

    private void setClickListenersForButtons() {
        // Buttons for digits and operators
        int[] digitButtonIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9};
        int[] operatorButtonIds = {R.id.buttonPlus, R.id.buttonMinus,
                R.id.buttonMultiply, R.id.buttonDivide};

        for (int id : digitButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDigitButtonClick(button.getText().toString());
                }
            });
        }

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOperatorButtonClick(button.getText().toString().charAt(0));
                }
            });
        }

        // Decimal button
        Button decimalButton = findViewById(R.id.buttonDecimal);
        decimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDecimalButtonClick();
            }
        });

        // Equal button
        Button equalButton = findViewById(R.id.buttonEqual);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqualButtonClick();
            }
        });

        // Clear (AC) button
        Button clearButton = findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearButtonClick();
            }
        });
    }

    private void onDigitButtonClick(String digit) {
        currentNumber += digit;
        updateDisplay();
    }

    private void onOperatorButtonClick(char op) {
        // Calculate the result if an operator is already set
        if (operator != ' ') {
            calculateResult();
        }
        operator = op;
        result = Double.parseDouble(currentNumber);
        currentNumber = "";
    }

    private void onDecimalButtonClick() {
        if (!currentNumber.contains(".")) {
            currentNumber += ".";
            updateDisplay();
        }
    }

    private void onEqualButtonClick() {
        calculateResult();
        operator = ' ';
    }

    private void onClearButtonClick() {
        currentNumber = "";
        result = 0;
        operator = ' ';
        updateDisplay();
    }

    private void calculateResult() {
        if (currentNumber.isEmpty()) {
            return;
        }

        double num = Double.parseDouble(currentNumber);
        switch (operator) {
            case '+':
                result += num;
                break;
            case '-':
                result -= num;
                break;
            case '*':
                result *= num;
                break;
            case '/':
                result /= num;
                break;
        }
        currentNumber = "";
        updateDisplay();
    }

    private void updateDisplay() {
        resultTextView.setText(currentNumber.isEmpty() ? String.valueOf(result) : currentNumber);
    }
}


