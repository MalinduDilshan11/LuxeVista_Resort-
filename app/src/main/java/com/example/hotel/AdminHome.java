package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void manageRooms (View view){
        Intent intent = new Intent(this, ManageRoom.class);
        startActivity(intent);
    }

    public void manageServices (View view){
        Intent intent = new Intent(this, ManageService.class);
        startActivity(intent);
    }

    public void viewRoomBook (View view){
        Intent intent = new Intent(this, ViewRoomBook.class);
        startActivity(intent);
    }

    public void viewServiceBook (View view){
        Intent intent = new Intent(this, ViewServiceBook.class);
        startActivity(intent);
    }

    public void manageUsers (View view){
        Intent intent = new Intent(this, ManageUser.class);
        startActivity(intent);
    }

    public void LogoutAdmin (View view){
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
    }
}