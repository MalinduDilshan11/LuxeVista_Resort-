package com.example.hotel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ServiceBook extends AppCompatActivity {

    private DB_Operations dbOperations;
    private EditText txtServiceID, txtServiceName, txtEmail, txtReserveDate, txtServicePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_book);

        createNotificationChannel();

        dbOperations = new DB_Operations(this);

        txtServiceID = findViewById(R.id.txtBookServiceID);
        txtServiceName = findViewById(R.id.txtBookServiceName);
        txtEmail = findViewById(R.id.txtBookServiceMail);
        txtReserveDate = findViewById(R.id.txtBookServiceRDate);
        txtServicePrice = findViewById(R.id.txtBookServicePrice);

        Button btnBookServiceConfirm = findViewById(R.id.btnBookServiceConfirm);
        btnBookServiceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceID = txtServiceID.getText().toString();
                String serviceName = txtServiceName.getText().toString();
                String email = txtEmail.getText().toString();
                String reserveDate = txtReserveDate.getText().toString();
                String servicePrice = txtServicePrice.getText().toString();

                if (isValidInput(serviceID, serviceName, email, reserveDate, servicePrice)) {
                    dbOperations.insertServiceBooking(Integer.parseInt(serviceID), serviceName, Double.parseDouble(servicePrice), email, reserveDate);
                    Toast.makeText(ServiceBook.this, "Booking confirmed!", Toast.LENGTH_SHORT).show();
                    clearFields();

                    postNotification(serviceName);
                }
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "SERVICE_BOOK_CHANNEL",
                    "Service Booking Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for service bookings");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void postNotification(String serviceName) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ServiceBook.this, "SERVICE_BOOK_CHANNEL")
                .setSmallIcon(R.drawable.lv)
                .setContentTitle("Booking Confirmed")
                .setContentText("Service " + serviceName + " has been successfully booked.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent intent = new Intent(ServiceBook.this, MainActivity.class); // Replace with your target activity
        PendingIntent pendingIntent = PendingIntent.getActivity(ServiceBook.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

    private boolean isValidInput(String serviceID, String serviceName, String email, String reserveDate, String servicePrice) {
        if (serviceID.isEmpty() || serviceName.isEmpty() || email.isEmpty() || reserveDate.isEmpty() || servicePrice.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double price = Double.parseDouble(servicePrice);
            if (price <= 0) {
                Toast.makeText(this, "Service Price must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
            return false;
        }

        return isValidDate(reserveDate);
    }

    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            Toast.makeText(this, "Please enter a valid date (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void clearFields() {
        txtServiceID.setText("");
        txtServiceName.setText("");
        txtEmail.setText("");
        txtReserveDate.setText("");
        txtServicePrice.setText("");
    }
}
