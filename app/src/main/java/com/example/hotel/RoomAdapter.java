package com.example.hotel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Room> rooms;

    public RoomAdapter(Context context, ArrayList<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View roomView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        roomView = inflater.inflate(R.layout.custom_room_layout, null);

        // Fetch views
        TextView roomId = roomView.findViewById(R.id.textViewRoomId);
        TextView roomName = roomView.findViewById(R.id.textViewRoomName);
        TextView roomCap = roomView.findViewById(R.id.textViewRoomCapacity);
        TextView roomDes = roomView.findViewById(R.id.textViewRoomDes);
        TextView roomPrice = roomView.findViewById(R.id.textViewRoomPrice);
        ImageView roomImage = roomView.findViewById(R.id.imageViewRoom);
        Button btnAddToCart = roomView.findViewById(R.id.btnAddToCart);

        // Bind data to views
        Room room = rooms.get(position);

        roomId.setText("Room ID: " + room.getRoomID());
        roomName.setText(room.getRoomName());
        roomCap.setText("Capacity: " + room.getRoomCapacity());
        roomDes.setText(room.getRoomDescription());
        roomPrice.setText("Price: LKR. " + room.getRoomPrice());

        Bitmap bitmap = BitmapFactory.decodeByteArray(room.getRoomImage(), 0, room.getRoomImage().length);
        roomImage.setImageBitmap(bitmap);

        btnAddToCart.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomBook.class);
            intent.putExtra("roomID", room.getRoomID());
            intent.putExtra("roomName", room.getRoomName());
            intent.putExtra("roomPrice", room.getRoomPrice());
            intent.putExtra("roomCapacity", room.getRoomCapacity());
            intent.putExtra("roomDescription", room.getRoomDescription());
            intent.putExtra("roomImage", room.getRoomImage());
            context.startActivity(intent);
        });

        return roomView;
    }

}
