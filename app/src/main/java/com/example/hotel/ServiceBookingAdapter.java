package com.example.hotel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceBookingAdapter extends RecyclerView.Adapter<ServiceBookingAdapter.ViewHolder> {

    private ArrayList<ServiceBooking> serviceBookings;

    public ServiceBookingAdapter(ArrayList<ServiceBooking> serviceBookings) {
        this.serviceBookings = serviceBookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceBooking booking = serviceBookings.get(position);
        holder.serviceName.setText(booking.getServiceName());
        holder.reserveDate.setText(booking.getReserveDate());
        holder.servicePrice.setText(String.valueOf("Price: LKR." + booking.getServicePrice()));
        holder.bookingEmail.setText(booking.getEmail());  // Set the email here
    }

    @Override
    public int getItemCount() {
        return serviceBookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName, reserveDate, servicePrice, bookingEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            reserveDate = itemView.findViewById(R.id.reserveDate);
            servicePrice = itemView.findViewById(R.id.servicePrice);
            bookingEmail = itemView.findViewById(R.id.bookingEmail);  // Initialize email TextView
        }
    }
}

