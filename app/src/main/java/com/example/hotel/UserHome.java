package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void ViewRooms (View view){
        Intent intent = new Intent(this, ViewAllRooms.class);
        startActivity(intent);
    }

    public void ViewRoomBooks (View view){
        Intent intent = new Intent(this, RoomBook.class);
        startActivity(intent);
    }

    public void ViewServices (View view){
        Intent intent = new Intent(this, ViewAllService.class);
        startActivity(intent);
    }

    public void ViewServiceBooks (View view){
        Intent intent = new Intent(this, ServiceBook.class);
        startActivity(intent);
    }

    public void ViewNearPlaces (View view){
        Intent intent = new Intent(this, NearPlaces.class);
        startActivity(intent);
    }

    public void ViewPromotions (View view){
        Intent intent = new Intent(this, Promotions.class);
        startActivity(intent);
    }

    public void LogoutUser (View view){
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
    }
}