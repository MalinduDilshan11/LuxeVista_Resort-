package com.example.hotel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageUser extends AppCompatActivity {

    private EditText txtUName, txtUMail, txtUNic, txtUCon, txtUPass;
    private Button btnSearch, btnCreateAcc, btnUCancel, btnUpdateUser;

    private DB_Operations dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        // Initialize views
        txtUName = findViewById(R.id.txtUName);
        txtUMail = findViewById(R.id.txtUMail);
        txtUNic = findViewById(R.id.txtUNic);
        txtUCon = findViewById(R.id.txtUCon);
        txtUPass = findViewById(R.id.txtUPass);
        btnCreateAcc = findViewById(R.id.btnCreateAcc);
        btnUCancel = findViewById(R.id.btnUCancel);
        btnSearch = findViewById(R.id.btnSearch);
        btnUpdateUser = findViewById(R.id.btnUpdateUser); // Ensure this button is defined in XML

        // Initialize database helper
        dbHelper = new DB_Operations(this);

        // Set listeners
        btnCreateAcc.setOnClickListener(v -> saveUserData());
        btnSearch.setOnClickListener(v -> fetchUserDetailsByEmail());
        btnUpdateUser.setOnClickListener(v -> updateUserData());
        btnUCancel.setOnClickListener(v -> clearFields());
    }

    private void fetchUserDetailsByEmail() {

        String email = txtUMail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an email to search!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DB_Operations.TABLE_USER,
                null,
                DB_Operations.COLUMN_EMAIL + " = ?",
                new String[]{email},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            txtUName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DB_Operations.COLUMN_USERNAME)));
            txtUNic.setText(cursor.getString(cursor.getColumnIndexOrThrow(DB_Operations.COLUMN_NIC)));
            txtUCon.setText(cursor.getString(cursor.getColumnIndexOrThrow(DB_Operations.COLUMN_CONTACT)));
            txtUPass.setText(cursor.getString(cursor.getColumnIndexOrThrow(DB_Operations.COLUMN_PASSWORD)));
            Toast.makeText(this, "User details loaded successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No user found with this email!", Toast.LENGTH_SHORT).show();
            clearFields(); // Optionally clear fields if no user is found
        }

        if (cursor != null) {
            cursor.close();
        }
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

    private void updateUserData() {
        String username = txtUName.getText().toString().trim();
        String nic = txtUNic.getText().toString().trim();
        String contact = txtUCon.getText().toString().trim();
        String password = txtUPass.getText().toString().trim();
        String email = txtUMail.getText().toString().trim();

        if (username.isEmpty() || nic.isEmpty() || contact.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the user ID from the database based on the email
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DB_Operations.TABLE_USER,
                new String[]{DB_Operations.COLUMN_USER_ID},  // Fetch only USER_ID
                DB_Operations.COLUMN_EMAIL + " = ?",  // Search by email
                new String[]{email},
                null,
                null,
                null);

        int userId = -1; // Default value if user doesn't exist

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(DB_Operations.COLUMN_USER_ID));
            cursor.close();
        }

        if (userId == -1) {
            Toast.makeText(this, "No user found with this email!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Now, proceed to update the user data
        User updatedUser = new User(userId, username, email, nic, contact, password);
        int rowsAffected = dbHelper.updateUser(updatedUser);

        if (rowsAffected > 0) {
            Toast.makeText(this, "User Updated Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update Failed! Check the email and try again.", Toast.LENGTH_SHORT).show();
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
