package com.example.capstoneproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {

    TextView userInfoText;
    Button back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        userInfoText = findViewById(R.id.user_info);
        back = findViewById(R.id.back_button);
        next = findViewById(R.id.next_button);

        // Get user info from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "User");
        String lastName = prefs.getString("lastName", "");
        String city = prefs.getString("city", "City");
        String state = prefs.getString("state", "Province");
        String country = prefs.getString("country", "Country");
        String email = prefs.getString("email", "");
        String dob = prefs.getString("dob", "");
        String street = prefs.getString("street", "");
        String postal = prefs.getString("postal", "");

        // Build user info block
        String userInfo = firstName + " " + lastName + "\n"
                + city + ", " + state + ", " + country + "\n"
                + "Date of Birth: " + dob + "\n"
                + "Address: " + street + ", " + postal + "\n"
                + "Email: " + email;

        userInfoText.setText(userInfo);

        // Navigation buttons
        back.setOnClickListener(v -> finish()); // Go back to Sign Up
        next.setOnClickListener(v -> {
            startActivity(new Intent(ProfilePage.this, MainActivity.class)); // Go to Login
            finish();
        });
    }
}
