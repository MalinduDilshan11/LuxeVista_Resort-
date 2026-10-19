package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    private EditText txtAdmin, txtPassAd;
    private Button btnLoginAd;
    private TextView txtAttemptAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize views
        txtAdmin = findViewById(R.id.textAdmin);
        txtPassAd = findViewById(R.id.textPassAd);
        btnLoginAd = findViewById(R.id.bttnLoginAd);
        txtAttemptAd = findViewById(R.id.txtAttemptAd);

        // Set click listener for login button
        btnLoginAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });
    }

    private void validateLogin() {
        // Get input data
        String username = txtAdmin.getText().toString().trim();
        String password = txtPassAd.getText().toString().trim();

        // Check for empty fields
        if (username.isEmpty() || password.isEmpty()) {
            txtAttemptAd.setText("Please fill in all fields.");
            return;
        }

        // Validate credentials
        if (username.equals("admin") && password.equals("admin")) {
            Toast.makeText(AdminLogin.this, "Login Successful!", Toast.LENGTH_SHORT).show();

            // Navigate to AdminHome
            Intent intent = new Intent(AdminLogin.this, AdminHome.class);
            startActivity(intent);
            finish(); // Close the current activity

        } else {
            txtAttemptAd.setText("Invalid username or password. Please try again.");
            txtAdmin.setText("");
            txtPassAd.setText("");
        }
    }
}
