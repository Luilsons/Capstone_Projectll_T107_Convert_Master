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

public class SignUpPage extends AppCompatActivity {

    private static ArrayList<HashMap<String, String>> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG, "Sign Up Screen");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        EditText firstName = findViewById(R.id.firstname);
        EditText lastName = findViewById(R.id.lastname);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button signup = findViewById(R.id.signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sign Up button logic
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredFirstName = firstName.getText().toString().trim();
                String enteredLastName = lastName.getText().toString().trim();
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                if (enteredFirstName.isEmpty() || enteredLastName.isEmpty() || enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isDuplicateUser(enteredEmail)) {
                    Toast.makeText(SignUpPage.this, "User already exists. Try logging in.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add user to the list
                HashMap<String, String> newUser = new HashMap<>();
                newUser.put("firstName", enteredFirstName);
                newUser.put("lastName", enteredLastName);
                newUser.put("email", enteredEmail);
                newUser.put("password", enteredPassword);

                userList.add(newUser);
                Toast.makeText(SignUpPage.this, "Sign up successful! Please log in.", Toast.LENGTH_SHORT).show();

                // Navigate back to MainActivity
                Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear activity stack
                startActivity(intent);
                finish(); // Close SignUpPage
            }
        });
    }

    private boolean isDuplicateUser(String email) {
        for (HashMap<String, String> user : userList) {
            if (user.get("email").equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<HashMap<String, String>> getUserList() {
        return userList;
    }
}
