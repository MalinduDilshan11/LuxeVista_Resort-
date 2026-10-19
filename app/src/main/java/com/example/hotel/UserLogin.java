package com.example.hotel;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserLogin extends AppCompatActivity {

    private EditText txtUserMail, txtPassUser;
    private TextView txtAttemptUser;
    private Button btnUserLogin, btnSignUp, btnAdmin;

    private DB_Operations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txtUserMail = findViewById(R.id.txtUserMail);
        txtPassUser = findViewById(R.id.txtPassUser);
        txtAttemptUser = findViewById(R.id.txtAttemptUser);
        btnUserLogin = findViewById(R.id.btnUserLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnAdmin = findViewById(R.id.btnAdmin);

        dbOperations = new DB_Operations(this);

        btnUserLogin.setOnClickListener(v -> handleLogin());

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserSign.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String email = txtUserMail.getText().toString().trim();
        String password = txtPassUser.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            txtAttemptUser.setText("Please fill in both fields.");
            return;
        }

        if (checkCredentials(email, password)) {
            txtAttemptUser.setText("Login successful!");
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();

            clearFields();
            Intent intent = new Intent(this, UserHome.class);
            startActivity(intent);
            finish();
        } else {
            txtAttemptUser.setText("Invalid username or password. Try again.");
            clearFields2();
        }
    }

    private boolean checkCredentials(String email, String password) {
        SQLiteDatabase db = dbOperations.getReadableDatabase();


        String query = "SELECT * FROM " + DB_Operations.TABLE_USER +
                " WHERE " + DB_Operations.COLUMN_EMAIL + " = ? AND " +
                DB_Operations.COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
    }

    private void clearFields() {
        txtUserMail.setText("");
        txtPassUser.setText("");
        txtAttemptUser.setText("");
    }

    private void clearFields2() {
        txtUserMail.setText("");
        txtPassUser.setText("");
    }

    public void adminIndex (View view){
        Intent intent = new Intent(this, AdminLogin.class);
        startActivity(intent);
    }
}
