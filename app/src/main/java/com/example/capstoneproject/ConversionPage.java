package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ConversionPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG, "onCreate: Screen 2");
        EdgeToEdge.enable(this);
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
                String fromUnitOfMeasureText = fromUnitOfMeasure.getText().toString();
                String amountText = amount.getText().toString();
                Double amountNum = Double.parseDouble(amountText);
                Double finalNum = (amountNum * 1.35)/ 3.785;
                DecimalFormat df_obj = new DecimalFormat("#.##");
                if ((fromCurrencyText != null && fromCurrencyText.length()>0) && (fromUnitOfMeasureText != null && fromUnitOfMeasureText.length()>0) && (finalNum != null && finalNum > 0)) {
                    convertedAmount.setText("Inputted " + amountText + " " + fromCurrencyText + " dollars per " + fromUnitOfMeasureText + ", Outputted " + df_obj.format(finalNum) + " Canadian dollars per litre");
                }
            }
        });




    }
}
