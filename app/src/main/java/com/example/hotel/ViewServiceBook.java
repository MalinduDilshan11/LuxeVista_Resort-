package com.example.hotel;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewServiceBook extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceBookingAdapter adapter;
    private DB_Operations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service_book);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbOperations = new DB_Operations(this);

        // Fetch all service bookings (without email filter)
        ArrayList<ServiceBooking> serviceBookings = dbOperations.getAllServiceBookings();

        // Create an adapter and set it to the RecyclerView
        adapter = new ServiceBookingAdapter(serviceBookings);
        recyclerView.setAdapter(adapter);
    }
}


