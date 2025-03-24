package com.example.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpPage extends AppCompatActivity {

    EditText firstname, lastname, email, password, dob, street, postal;
    Spinner countrySpinner, stateSpinner, citySpinner;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignUpPage", "onCreate: SignUpPage Loaded");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Link views
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        dob = findViewById(R.id.dob);
        street = findViewById(R.id.street);
        postal = findViewById(R.id.postal);
        countrySpinner = findViewById(R.id.country_spinner);
        stateSpinner = findViewById(R.id.state_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        signup = findViewById(R.id.signup);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupSpinners();

        signup.setOnClickListener(v -> handleSignup());
    }

    private void setupSpinners() {
        // Country
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Canada"});
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        // Province
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Ontario", "Quebec", "British Columbia"});
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        // City
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Toronto", "Ottawa", "Mississauga"});
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
    }

    private void handleSignup() {
        String firstNameText = firstname.getText().toString().trim();
        String lastNameText = lastname.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String dobText = dob.getText().toString().trim();
        String streetText = street.getText().toString().trim();
        String postalText = postal.getText().toString().trim();
        String country = countrySpinner.getSelectedItem().toString();
        String state = stateSpinner.getSelectedItem().toString();
        String city = citySpinner.getSelectedItem().toString();

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() ||
                passwordText.isEmpty() || dobText.isEmpty() || streetText.isEmpty() || postalText.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dobText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Toast.makeText(this, "DOB must be in format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", firstNameText);
        editor.putString("lastName", lastNameText);
        editor.putString("email", emailText);
        editor.putString("password", passwordText);
        editor.putString("dob", dobText);
        editor.putString("street", streetText);
        editor.putString("postal", postalText);
        editor.putString("country", country);
        editor.putString("state", state);
        editor.putString("city", city);
        editor.apply();

        Toast.makeText(this, "Signup Successful! Redirecting to Profile Page.", Toast.LENGTH_SHORT).show();

        // 🔄 Navigate to Profile Page (not login)
        Intent intent = new Intent(SignUpPage.this, ProfilePage.class);
        startActivity(intent);
        finish();
    }
}
