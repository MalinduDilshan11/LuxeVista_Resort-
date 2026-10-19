package com.example.hotel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RoomBook extends AppCompatActivity {

    private static final String CHANNEL_ID = "booking_channel";

    private EditText txtBookRoomID, txtBookRoomName, txtBookRoomPrice, txtBookRoomMail, txtBookRoomCIDate, txtBookRoomCODate;
    private Button btnBookConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_book);

        txtBookRoomID = findViewById(R.id.txtBookRoomID);
        txtBookRoomName = findViewById(R.id.txtBookRoomName);
        txtBookRoomPrice = findViewById(R.id.txtBookRoomPrice);
        txtBookRoomMail = findViewById(R.id.txtBookRoomMail);
        txtBookRoomCIDate = findViewById(R.id.txtBookRoomCIDate);
        txtBookRoomCODate = findViewById(R.id.txtBookRoomCODate);
        btnBookConfirm = findViewById(R.id.btnBookConfirm);

        Intent intent = getIntent();
        String roomID = intent.getStringExtra("roomID");
        String roomName = intent.getStringExtra("roomName");
        String roomPrice = intent.getStringExtra("roomPrice");

        txtBookRoomID.setText(roomID);
        txtBookRoomName.setText(roomName);
        txtBookRoomPrice.setText(roomPrice);


        createNotificationChannel();

        btnBookConfirm.setOnClickListener(v -> saveBooking());
    }

    private void saveBooking() {
        String roomID = txtBookRoomID.getText().toString().trim();
        String roomName = txtBookRoomName.getText().toString().trim();
        String roomPrice = txtBookRoomPrice.getText().toString().trim();
        String email = txtBookRoomMail.getText().toString().trim();
        String checkInDate = txtBookRoomCIDate.getText().toString().trim();
        String checkOutDate = txtBookRoomCODate.getText().toString().trim();

        if (roomID.isEmpty() || roomName.isEmpty() || roomPrice.isEmpty() || email.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!roomID.matches("\\d+")) {
            Toast.makeText(this, "Room ID must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!roomPrice.matches("\\d+(\\.\\d{1,2})?")) {
            Toast.makeText(this, "Room price must be a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isDateValid(checkInDate) || !isDateValid(checkOutDate)) {
            Toast.makeText(this, "Invalid date format. Use yyyy-MM-dd", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isCheckOutAfterCheckIn(checkInDate, checkOutDate)) {
            Toast.makeText(this, "Check-out date must be after check-in date", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        values.put("room_id", roomID);
        values.put("room_name", roomName);
        values.put("room_price", roomPrice);
        values.put("email", email);
        values.put("check_in_date", checkInDate);
        values.put("check_out_date", checkOutDate);

        long result = dbHelper.insert("room_booking", values);

        if (result > 0) {
            Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
            postNotification(roomID, roomName, checkInDate, checkOutDate);
            finish();
        } else {
            Toast.makeText(this, "Booking Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDateValid(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isCheckOutAfterCheckIn(String checkIn, String checkOut) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date checkInDate = sdf.parse(checkIn);
            Date checkOutDate = sdf.parse(checkOut);
            return checkOutDate != null && checkInDate != null && checkOutDate.after(checkInDate);
        } catch (ParseException e) {
            return false;
        }
    }

    private void postNotification(String roomID, String roomName, String checkInDate, String checkOutDate) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.lv)
                .setContentTitle("Booking Confirmed")
                .setContentText("Room " + roomName + " (ID: " + roomID + ") is booked from " + checkInDate + " to " + checkOutDate + ".")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Booking Notifications";
            String description = "Notifications for room bookings";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
