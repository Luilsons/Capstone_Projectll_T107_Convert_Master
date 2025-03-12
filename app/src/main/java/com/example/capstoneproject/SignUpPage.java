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

public class SignUpPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG, "onCreate: Screen 3");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        EditText firstname = findViewById(R.id.firstname);
        EditText lastname = findViewById(R.id.lastname);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button signup = findViewById(R.id.signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameText = firstname.getText().toString().trim();
                String lastNameText = lastname.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Store user credentials using SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", emailText);
                editor.putString("password", passwordText);
                editor.apply();

                Toast.makeText(SignUpPage.this, "Signup Successful! Please login.", Toast.LENGTH_SHORT).show();

                // Redirect to login page (MainActivity)
                Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close SignupPage
            }
        });
    }
}
