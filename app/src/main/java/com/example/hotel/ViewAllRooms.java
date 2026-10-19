package com.example.hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import androidx.appcompat.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel.DB_Operations;
import com.example.hotel.R;
import com.example.hotel.Room;
import com.example.hotel.RoomAdapter;

import java.util.ArrayList;

public class ViewAllRooms extends AppCompatActivity {

    ListView roomList;
    DB_Operations db;
    RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_rooms);

        roomList = findViewById(R.id.lstRoomList);
        db = new DB_Operations(this);

        // Fetch all rooms from the database
        ArrayList<Room> rooms = db.getAllRooms();

        if (rooms != null) {
            roomAdapter = new RoomAdapter(this, rooms);
            roomList.setAdapter(roomAdapter);
        }

        // Set up the SearchView to filter rooms by name
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // You can choose to perform actions when the user submits the query
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRoomsByName(newText);
                return false;
            }
        });

        // Set up the Spinner for filtering by price range (as already implemented)
        Spinner priceRangeSpinner = findViewById(R.id.priceRangeSpinner);
        priceRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filterRoomsByPriceRange(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void filterRoomsByName(String query) {
        ArrayList<Room> rooms = db.getAllRooms();
        ArrayList<Room> filteredRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getRoomName().toLowerCase().contains(query.toLowerCase())) {
                filteredRooms.add(room);
            }
        }

        // Update the adapter with the filtered list
        roomAdapter = new RoomAdapter(this, filteredRooms);
        roomList.setAdapter(roomAdapter);
    }

    private void filterRoomsByPriceRange(int position) {
        ArrayList<Room> rooms = db.getAllRooms();
        ArrayList<Room> filteredRooms = new ArrayList<>();

        // Filter logic based on price range (you can adjust as needed)
        switch (position) {
            case 0: // All
                filteredRooms = rooms;
                break;
            case 1: // Below LKR 5000
                for (Room room : rooms) {
                    if (room.getRoomPrice() < 5000) {
                        filteredRooms.add(room);
                    }
                }
                break;
            case 2: // LKR 5000 - 10000
                for (Room room : rooms) {
                    if (room.getRoomPrice() >= 5000 && room.getRoomPrice() <= 10000) {
                        filteredRooms.add(room);
                    }
                }
                break;
            case 3: // LKR 10000 - 15000
                for (Room room : rooms) {
                    if (room.getRoomPrice() >= 10000 && room.getRoomPrice() <= 15000) {
                        filteredRooms.add(room);
                    }
                }
                break;
            case 4: // Above LKR 15000
                for (Room room : rooms) {
                    if (room.getRoomPrice() > 15000) {
                        filteredRooms.add(room);
                    }
                }
                break;
        }

        // Update the adapter with the filtered list
        roomAdapter = new RoomAdapter(this, filteredRooms);
        roomList.setAdapter(roomAdapter);
    }
}

