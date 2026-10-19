package com.example.hotel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ServiceAdapter extends ArrayAdapter<Service> {
    private Context context;
    private ArrayList<Service> serviceList;

    public ServiceAdapter(Context context, ArrayList<Service> serviceList) {
        super(context, 0, serviceList);
        this.context = context;
        this.serviceList = serviceList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_service_layout, parent, false);
        }

        Service service = serviceList.get(position);

        TextView serviceID = convertView.findViewById(R.id.textViewServiceID);
        TextView serviceName = convertView.findViewById(R.id.textViewServiceName);
        TextView serviceDes = convertView.findViewById(R.id.textViewServiceDes);
        TextView servicePrice = convertView.findViewById(R.id.textViewServicePrice);
        ImageView serviceImage = convertView.findViewById(R.id.imageViewService);
        Button btnAddToService = convertView.findViewById(R.id.btnAddToService);

        serviceID.setText(String.valueOf("Service ID: " + service.getSerID()));
        serviceName.setText(service.getSerName());
        serviceDes.setText(service.getSerDes());
        servicePrice.setText(String.format("LKR.%.2f", service.getSerPrice()));

        // Set the service image if available
        if (service.getSerImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(service.getSerImage(), 0, service.getSerImage().length);
            serviceImage.setImageBitmap(bitmap);
        }

        // Handle the button click
        btnAddToService.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceBook.class);
            // Pass any necessary data about the service to the next activity
            intent.putExtra("serviceID", service.getSerID());
            intent.putExtra("serviceName", service.getSerName());
            intent.putExtra("servicePrice", service.getSerPrice());
            context.startActivity(intent);
        });

        return convertView;
    }

}

