package com.example.capstoneproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ConversionPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG, "onCreate: Screen 2");
        setContentView(R.layout.activity_conversion_page);

        EditText fromCurrency = findViewById(R.id.fromCurrency);
        EditText toCurrency = findViewById(R.id.toCurrency);
        EditText amount = findViewById(R.id.amount);
        EditText fromUnitOfMeasure = findViewById(R.id.fromUnitOfMeasure);
        EditText toUnitOfMeasure = findViewById(R.id.toUnitOfMeasure);
        Button convert = findViewById(R.id.convert);
        TextView convertedAmount = findViewById(R.id.convertedAmount);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromCurrencyText = fromCurrency.getText().toString();
                String toCurrencyText = toCurrency.getText().toString();
                String fromUnitOfMeasureText = fromUnitOfMeasure.getText().toString();
                String toUnitOfMeasureText = toUnitOfMeasure.getText().toString();
                String amountText = amount.getText().toString();

                // Check if fields are empty
                if (fromCurrencyText.isEmpty() || toCurrencyText.isEmpty() || amountText.isEmpty()) {
                    Toast.makeText(ConversionPage.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double amountNum = Double.parseDouble(amountText);
                // Call the function to fetch currency rates and do the conversion
                getCurrencyRateAndConvert(fromCurrencyText, toCurrencyText, amountNum, fromUnitOfMeasureText, toUnitOfMeasureText, convertedAmount);
            }
        });
    }

    private void getCurrencyRateAndConvert(String fromCurrency, String toCurrency, double amount, String fromUnitOfMeasure, String toUnitOfMeasure, TextView convertedAmount) {
        // Call the CurrencyConverter API to get the rate
        CurrencyConverter.convertCurrency(fromCurrency, toCurrency, amount, new CurrencyConverter.CurrencyCallback() {
            @Override
            public void onSuccess(double conversionRate) {
                // Handle successful conversion
                double unitOfMeasureMultiplier = getUnitOfMeasureConversion(fromUnitOfMeasure, toUnitOfMeasure);
                double result = amount * conversionRate * unitOfMeasureMultiplier;

                // Display the result with two decimal places
                DecimalFormat df = new DecimalFormat("#.##");
                convertedAmount.setText("Converted: " + df.format(result) + " " + toCurrency + " per " + toUnitOfMeasure);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure in API call
                convertedAmount.setText("Error fetching conversion rates.");
            }
        });
    }

    // Helper method to get the conversion multiplier for unit of measure
    private double getUnitOfMeasureConversion(String fromUnitOfMeasure, String toUnitOfMeasure) {
        if (fromUnitOfMeasure.equals("US Gallons") && toUnitOfMeasure.equals("Litres")) {
            return 3.785;
        }
        // Add additional unit conversion cases here
        return 1.0;  // Default case for no conversion
    }
}
