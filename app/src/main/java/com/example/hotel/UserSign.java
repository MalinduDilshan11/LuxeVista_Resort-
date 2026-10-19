package com.example.hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserSign extends AppCompatActivity {

    private EditText txtUName, txtUMail, txtUNic, txtUCon, txtUPass;
    private Button btnCreateAcc, btnUCancel;

    private DB_Operations dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign);

        txtUName = findViewById(R.id.txtUName);
        txtUMail = findViewById(R.id.txtUMail);
        txtUNic = findViewById(R.id.txtUNic);
        txtUCon = findViewById(R.id.txtUCon);
        txtUPass = findViewById(R.id.txtUPass);
        btnCreateAcc = findViewById(R.id.btnCreateAcc);
        btnUCancel = findViewById(R.id.btnUCancel);

        dbHelper = new DB_Operations(this);

        btnCreateAcc.setOnClickListener(v -> saveUserData());
        btnUCancel.setOnClickListener(v -> clearFields());
    }

    private void saveUserData() {
        String username = txtUName.getText().toString().trim();
        String email = txtUMail.getText().toString().trim();
        String nic = txtUNic.getText().toString().trim();
        String contact = txtUCon.getText().toString().trim();
        String password = txtUPass.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || nic.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, email, nic, contact, password);

        try {
            dbHelper.insertUser(user);
            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
        } catch (Exception e) {
            Toast.makeText(this, "Error creating account. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        txtUName.setText("");
        txtUMail.setText("");
        txtUNic.setText("");
        txtUCon.setText("");
        txtUPass.setText("");
    }
}
