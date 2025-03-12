package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConversionPage extends AppCompatActivity {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/a28d024d46d91d18ae1f5880/latest/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG, "onCreate: Screen 2");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversion_page);

        Spinner fromCurrencyDropdown = findViewById(R.id.fromCurrencyDropdown);
        EditText toCurrency = findViewById(R.id.toCurrency);
        EditText amount = findViewById(R.id.amount);
        Spinner unitConversionDropdown = findViewById(R.id.unitConversionDropdown);
        Button convert = findViewById(R.id.convert);
        Button backButton = findViewById(R.id.backButton); // Added Back Button
        TextView convertedAmount = findViewById(R.id.convertedAmount);

        String[] currencyOptions = {"USD", "EUR", "JPY", "AUD", "MXN", "GBP"};
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyOptions);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencyDropdown.setAdapter(currencyAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.unit_conversion_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitConversionDropdown.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromCurrencyText = fromCurrencyDropdown.getSelectedItem().toString().trim().toUpperCase();
                String finalAmountText = amount.getText().toString().trim();
                String selectedConversion = unitConversionDropdown.getSelectedItem().toString();

                if (fromCurrencyText.isEmpty() || finalAmountText.isEmpty()) {
                    Toast.makeText(ConversionPage.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedConversion.equals("Select Conversion")) {
                    Toast.makeText(ConversionPage.this, "Unit conversion must be selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double amountNum = Double.parseDouble(finalAmountText);
                    fetchExchangeRate(fromCurrencyText, amountNum, convertedAmount, selectedConversion);
                } catch (NumberFormatException e) {
                    Toast.makeText(ConversionPage.this, "Amount is Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Back Button Click Listener
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConversionPage.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }

    private void fetchExchangeRate(String fromCurrency, double amount, TextView convertedAmount, String selectedConversion) {
        OkHttpClient client = new OkHttpClient();
        String url = API_URL + fromCurrency;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(ConversionPage.this, "Exchange Rate Invalid", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(ConversionPage.this, "Error Fetching Data", Toast.LENGTH_SHORT).show());
                    return;
                }
                String responseData = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();
                if (jsonObject.has("conversion_rates")) {
                    JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
                    if (rates.has("CAD")) {
                        double conversionFactor = 1.0;
                        switch (selectedConversion) {
                            case "Gallon to Litre":
                                conversionFactor = 3.785;
                                break;
                            case "Mile to Kilometre":
                                conversionFactor = 1.609;
                                break;
                            case "Pound to Kilogram":
                                conversionFactor = 0.453;
                                break;
                            case "Inch to Centimetre":
                                conversionFactor = 2.54;
                                break;
                        }
                        double exchangeRate = rates.get("CAD").getAsDouble();
                        double finalAmount = (amount * exchangeRate) / conversionFactor;
                        DecimalFormat df = new DecimalFormat("#.##");
                        double roundedAmount = Double.parseDouble(df.format(finalAmount));
                        String[] unitOfMeasure = selectedConversion.split(" to ");
                        String toUnitOfMeasure = unitOfMeasure.length > 1 ? unitOfMeasure[1] : selectedConversion;
                        runOnUiThread(() -> convertedAmount.setText("CAD Price Per " + toUnitOfMeasure + ": $" + roundedAmount));
                    } else {
                        runOnUiThread(() -> Toast.makeText(ConversionPage.this, "Currency is Invalid", Toast.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }
}
