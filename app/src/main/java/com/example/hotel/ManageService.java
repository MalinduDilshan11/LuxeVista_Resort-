package com.example.hotel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ManageService extends AppCompatActivity {

    private EditText txtID, txtName, txtDes, txtPrice;
    private ImageView serviceImage;
    private byte[] imageByte;
    DB_Operations db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DB_Operations(this);
        txtID = findViewById(R.id.txtServiceID);
        txtName = findViewById(R.id.txtServiceName);
        txtDes = findViewById(R.id.txtServiceDes);
        txtPrice = findViewById(R.id.txtServicePrice);
        serviceImage = findViewById(R.id.imageService);
    }

    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 380);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                imageByte = outputStream.toByteArray();
                serviceImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(View view) {

        String id = txtID.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String description = txtDes.getText().toString().trim();
        String price = txtPrice.getText().toString().trim();


        if (id.isEmpty()) {
            Toast.makeText(this, "Service ID cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty()) {
            Toast.makeText(this, "Service Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(this, "Service Description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (price.isEmpty()) {
            Toast.makeText(this, "Service Price cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int serviceID = Integer.parseInt(id);
            double servicePrice = Double.parseDouble(price);

            Service service = new Service();
            service.setSerID(serviceID);
            service.setSerName(name);
            service.setSerDes(description);
            service.setSerPrice(servicePrice);
            service.setSerImage(imageByte);

            if (db.insertService(service) > 0) {
                Toast.makeText(this, "New Service Added", Toast.LENGTH_SHORT).show();
                clear();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid numeric value for Price", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        txtID.setText(null);
        txtName.setText(null);
        txtDes.setText(null);
        txtPrice.setText(null);
        serviceImage.setImageDrawable(null);
        txtID.requestFocus();
    }

    public void OpenViewAllServices(View view) {
        Intent intent = new Intent(this, ViewAllService.class);
        startActivity(intent);
    }
}
