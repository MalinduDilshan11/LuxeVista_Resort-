package com.example.hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class ViewAllService extends AppCompatActivity {
    private ListView lstServiceList;
    private Spinner priceRangeSpinner;
    private DB_Operations db;
    private ArrayList<Service> services;
    private ArrayList<Service> filteredServices;
    private ServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_service);

        lstServiceList = findViewById(R.id.lstServiceList);
        priceRangeSpinner = findViewById(R.id.priceRangeSpinner);

        db = new DB_Operations(this);
        services = db.getAllServices();
        filteredServices = new ArrayList<>(services);  // Initialize with all services

        adapter = new ServiceAdapter(this, filteredServices);
        lstServiceList.setAdapter(adapter);

        // Handle price range filter selection
        priceRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filterServicesByPrice(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Optionally handle when no selection is made
            }
        });
    }

    private void filterServicesByPrice(int position) {
        filteredServices.clear();
        for (Service service : services) {
            double price = service.getSerPrice();
            switch (position) {
                case 1: // Below LKR 5000
                    if (price < 5000) {
                        filteredServices.add(service);
                    }
                    break;
                case 2: // LKR 5000 - 10000
                    if (price >= 5000 && price <= 10000) {
                        filteredServices.add(service);
                    }
                    break;
                case 3: // LKR 10000 - 15000
                    if (price > 10000 && price <= 15000) {
                        filteredServices.add(service);
                    }
                    break;
                case 4: // Above LKR 15000
                    if (price > 15000) {
                        filteredServices.add(service);
                    }
                    break;
                default: // All
                    filteredServices.add(service);
                    break;
            }
        }
        adapter.notifyDataSetChanged();  // Update the ListView with the filtered data
    }
}
