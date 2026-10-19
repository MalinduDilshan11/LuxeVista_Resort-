package com.example.hotel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.tvRoomID.setText("Room ID: " + booking.getRoomID());
        holder.tvRoomName.setText("Room Name: " + booking.getRoomName());
        holder.tvRoomPrice.setText("Price: LKR." + booking.getRoomPrice());
        holder.tvEmail.setText("Email: " + booking.getEmail());
        holder.tvCheckInDate.setText("Check-In: " + booking.getCheckInDate());
        holder.tvCheckOutDate.setText("Check-Out: " + booking.getCheckOutDate());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomID, tvRoomName, tvRoomPrice, tvEmail, tvCheckInDate, tvCheckOutDate;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomID = itemView.findViewById(R.id.tvRoomID);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRoomPrice = itemView.findViewById(R.id.tvRoomPrice);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvCheckInDate = itemView.findViewById(R.id.tvCheckInDate);
            tvCheckOutDate = itemView.findViewById(R.id.tvCheckOutDate);
        }
    }
}
