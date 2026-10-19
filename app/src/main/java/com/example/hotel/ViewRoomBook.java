package com.example.hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewRoomBook extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvNoBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_book);

        recyclerView = findViewById(R.id.recyclerView);
        tvNoBookings = findViewById(R.id.tvNoBookings);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch booking data
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Booking> bookingList = dbHelper.getAllBookings();

        if (bookingList.isEmpty()) {
            tvNoBookings.setVisibility(View.VISIBLE);
        } else {
            tvNoBookings.setVisibility(View.GONE);
            BookingAdapter adapter = new BookingAdapter(bookingList);
            recyclerView.setAdapter(adapter);
        }
    }
}
