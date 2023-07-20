package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     EditText editTextValue;
    private Spinner spinnerFromUnit, spinnerToUnit;
    private Button buttonConvert;
    private TextView textViewResult;

    private String[] units = {"Centimeters", "Meters", "Grams", "Kilograms"};
    private double[][] conversionMatrix = {{1, 0.01, 0, 0},
            {100, 1, 0, 0},
            {0, 0, 1, 0.001},
            {0, 0, 1000, 1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValue = findViewById(R.id.editTextValue);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        String valueStr = editTextValue.getText().toString();
        if (valueStr.isEmpty()) {
            textViewResult.setText("Please enter a value");
            return;
        }

        double value = Double.parseDouble(valueStr);
        int fromIndex = spinnerFromUnit.getSelectedItemPosition();
        int toIndex = spinnerToUnit.getSelectedItemPosition();

        double convertedValue = value * conversionMatrix[fromIndex][toIndex];
        textViewResult.setText(String.valueOf(convertedValue));
    }
}
