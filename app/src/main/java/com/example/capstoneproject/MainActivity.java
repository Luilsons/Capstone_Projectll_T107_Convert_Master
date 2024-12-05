package com.example.capstoneproject;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Capstone Project";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Main Screen");
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

        // Clear fields when returning to MainActivity
        email.setText("");
        password.setText("");

        // Login button logic
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                if (isValidLogin(enteredEmail, enteredPassword)) {
                    // Navigate to ConversionPage if login is valid
                    Intent intent = new Intent(MainActivity.this, ConversionPage.class);
                    startActivity(intent);
                } else {
                    // Show error message if login is invalid
                    Toast.makeText(MainActivity.this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Navigate to Sign Up page
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(intent);
            }
        });
    }

    // Method to validate the login credentials
    private boolean isValidLogin(String email, String password) {
        // Retrieve the list of users from SignUpPage
        ArrayList<HashMap<String, String>> users = SignUpPage.getUserList();

        // Loop through the user list to validate credentials
        for (HashMap<String, String> user : users) {
            if (user.get("email").equalsIgnoreCase(email) && user.get("password").equals(password)) {
                return true;
            }
        }
        return false;
    }
}
