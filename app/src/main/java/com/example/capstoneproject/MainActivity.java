package com.example.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Capstone Project";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Screen 1");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String storedEmail = sharedPreferences.getString("email", null);
                String storedPassword = sharedPreferences.getString("password", null);

                if (emailText.equals(storedEmail) && passwordText.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ConversionPage.class);
                    startActivity(intent);
                    finish(); // Close Login Page
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(intent);
            }
        });
    }
}
