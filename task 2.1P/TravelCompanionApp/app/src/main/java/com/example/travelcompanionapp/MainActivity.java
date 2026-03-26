package com.example.travelcompanionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private EditText inputValue;
    private Button convertButton;
    private TextView resultText;

    private final String[] categories = {"Currency", "Fuel", "Temperature"};

    private final String[] currencyUnits = {"USD", "AUD", "EUR", "JPY", "GBP"};


    private final String[] fuelUnits = {
            "mpg", "km/L",
            "Gallon (US)", "Liter",
            "Nautical Mile", "Kilometer"
    };

    private final String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        setupCategorySpinner();
        setupCategoryChangeListener();
        setupConvertButton();
    }

    private void setupCategorySpinner() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // 默认显示 Currency
        updateUnitSpinners("Currency");
    }

    private void setupCategoryChangeListener() {
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                updateUnitSpinners(selectedCategory);
                inputValue.setText("");
                resultText.setText(getString(R.string.result_placeholder));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateUnitSpinners(String category) {
        String[] units;

        switch (category) {
            case "Currency":
                units = currencyUnits;
                break;
            case "Fuel":
                units = fuelUnits;
                break;
            case "Temperature":
                units = temperatureUnits;
                break;
            default:
                units = currencyUnits;
                break;
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);

        fromSpinner.setSelection(0);
        if (units.length > 1) {
            toSpinner.setSelection(1);
        }
    }

    private void setupConvertButton() {
        convertButton.setOnClickListener(v -> {
            String category = categorySpinner.getSelectedItem().toString();
            String fromUnit = fromSpinner.getSelectedItem().toString();
            String toUnit = toSpinner.getSelectedItem().toString();
            String input = inputValue.getText().toString().trim();

            if (input.isEmpty()) {
                Toast.makeText(MainActivity.this, getString(R.string.error_empty), Toast.LENGTH_SHORT).show();
                return;
            }

            Double value = tryParseDouble(input);
            if (value == null) {
                Toast.makeText(MainActivity.this, getString(R.string.error_invalid), Toast.LENGTH_SHORT).show();
                return;
            }

            if (fromUnit.equals(toUnit)) {
                Toast.makeText(MainActivity.this, getString(R.string.same_unit_message), Toast.LENGTH_SHORT).show();
                resultText.setText(decimalFormat.format(value));
                return;
            }

            if (category.equals("Fuel") && value < 0) {
                Toast.makeText(MainActivity.this, getString(R.string.error_negative_fuel), Toast.LENGTH_SHORT).show();
                return;
            }

            Double result = convertValue(category, fromUnit, toUnit, value);

            if (result == null) {
                resultText.setText(getString(R.string.error_not_supported));
            } else {
                resultText.setText(decimalFormat.format(result));
            }
        });
    }

    private Double tryParseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double convertValue(String category, String fromUnit, String toUnit, double value) {
        switch (category) {
            case "Currency":
                return convertCurrency(fromUnit, toUnit, value);
            case "Fuel":
                return convertFuel(fromUnit, toUnit, value);
            case "Temperature":
                return convertTemperature(fromUnit, toUnit, value);
            default:
                return null;
        }
    }

    private Double convertCurrency(String fromUnit, String toUnit, double value) {
        // Convert everything to USD first
        double usdValue;

        switch (fromUnit) {
            case "USD":
                usdValue = value;
                break;
            case "AUD":
                usdValue = value / 1.55;
                break;
            case "EUR":
                usdValue = value / 0.92;
                break;
            case "JPY":
                usdValue = value / 148.50;
                break;
            case "GBP":
                usdValue = value / 0.78;
                break;
            default:
                return null;
        }

        // Convert USD to target
        switch (toUnit) {
            case "USD":
                return usdValue;
            case "AUD":
                return usdValue * 1.55;
            case "EUR":
                return usdValue * 0.92;
            case "JPY":
                return usdValue * 148.50;
            case "GBP":
                return usdValue * 0.78;
            default:
                return null;
        }
    }

    private Double convertFuel(String fromUnit, String toUnit, double value) {
        // 1 mpg = 0.425 km/L
        if (fromUnit.equals("mpg") && toUnit.equals("km/L")) {
            return value * 0.425;
        }
        if (fromUnit.equals("km/L") && toUnit.equals("mpg")) {
            return value / 0.425;
        }

        // 1 Gallon (US) = 3.785 Liters
        if (fromUnit.equals("Gallon (US)") && toUnit.equals("Liter")) {
            return value * 3.785;
        }
        if (fromUnit.equals("Liter") && toUnit.equals("Gallon (US)")) {
            return value / 3.785;
        }

        // 1 Nautical Mile = 1.852 Kilometers
        if (fromUnit.equals("Nautical Mile") && toUnit.equals("Kilometer")) {
            return value * 1.852;
        }
        if (fromUnit.equals("Kilometer") && toUnit.equals("Nautical Mile")) {
            return value / 1.852;
        }

        return null;
    }

    private Double convertTemperature(String fromUnit, String toUnit, double value) {
        double celsius;

        switch (fromUnit) {
            case "Celsius":
                celsius = value;
                break;
            case "Fahrenheit":
                celsius = (value - 32) / 1.8;
                break;
            case "Kelvin":
                celsius = value - 273.15;
                break;
            default:
                return null;
        }

        switch (toUnit) {
            case "Celsius":
                return celsius;
            case "Fahrenheit":
                return (celsius * 1.8) + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                return null;
        }
    }
}