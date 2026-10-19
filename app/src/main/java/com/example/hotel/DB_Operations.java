package com.example.hotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Operations extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_Hotel";
    private static final int DATABASE_VERSION = 11;


    public static final String TABLE_USER = "tblUser";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_NIC = "NIC";
    public static final String COLUMN_CONTACT = "CONTACT";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    public DB_Operations(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_NIC + " TEXT, " +
                COLUMN_CONTACT + " TEXT, " +
                COLUMN_PASSWORD + " TEXT);";
        db.execSQL(createUserTable);

        String createRoomTable = "CREATE TABLE tblRoom (ROOM_ID INTEGER PRIMARY KEY, ROOM_NAME VARCHAR(25), ROOM_CAPACITY INTEGER, ROOM_DESCRIPTION VARCHAR, ROOM_PRICE DOUBLE, ROOM_IMAGE BLOB)";
        db.execSQL(createRoomTable);

        String createRoomBookingTable = "CREATE TABLE tblRoomBooking (" +
                "Book_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Room_ID INTEGER, " +
                "Room_Name VARCHAR(25), " +
                "Room_Price DOUBLE, " +
                "Email TEXT, " +
                "CheckInDate TEXT, " +
                "CheckOutDate TEXT, " +
                "FOREIGN KEY (Room_ID) REFERENCES tblRoom(ROOM_ID), " +
                "FOREIGN KEY (Email) REFERENCES tblUser(EMAIL))";
        db.execSQL(createRoomBookingTable);

        String createServiceTable = "CREATE TABLE tblService (SERVICE_ID INTEGER PRIMARY KEY, SERVICE_NAME VARCHAR(25), SERVICE_DESCRIPTION VARCHAR, SERVICE_PRICE DOUBLE, SERVICE_IMAGE BLOB)";
        db.execSQL(createServiceTable);

        String createServiceBookingTable = "CREATE TABLE tblServiceBooking (" +
                "Book_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SERVICE_ID INTEGER, " +
                "SERVICE_NAME VARCHAR(25), " +
                "SERVICE_PRICE DOUBLE, " +
                "Email TEXT, " +
                "ReserveDate TEXT, " +
                "FOREIGN KEY (SERVICE_ID) REFERENCES tblService(SERVICE_ID), " +
                "FOREIGN KEY (Email) REFERENCES tblUser(EMAIL))";
        db.execSQL(createServiceBookingTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS tblRoom");
        db.execSQL("DROP TABLE IF EXISTS tblRoomBooking");
        db.execSQL("DROP TABLE IF EXISTS tblService");
        db.execSQL("DROP TABLE IF EXISTS tblServiceBooking");
        onCreate(db);
    }

    public long insertUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_NIC, user.getNic());
        contentValues.put(COLUMN_CONTACT, user.getContact());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        return database.insert(TABLE_USER, null, contentValues);
    }

    public int updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_NIC, user.getNic());
        contentValues.put(COLUMN_CONTACT, user.getContact());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());

        // Use userId for identifying the user to update
        return database.update(TABLE_USER, contentValues, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
    }

    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        boolean valid = cursor != null && cursor.moveToFirst();
        cursor.close();
        return valid;
    }

    public long insertRoom(Room room) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ROOM_NAME", room.getRoomName());
        contentValues.put("ROOM_CAPACITY", room.getRoomCapacity());
        contentValues.put("ROOM_DESCRIPTION", room.getRoomDescription());
        contentValues.put("ROOM_PRICE", room.getRoomPrice());
        contentValues.put("ROOM_IMAGE", room.getRoomImage());
        return database.insert("tblRoom", null, contentValues);
    }

    public long insertService(Service service){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SERVICE_NAME", service.getSerName());
        contentValues.put("SERVICE_DESCRIPTION",service.getSerDes());
        contentValues.put("SERVICE_PRICE", service.getSerPrice());
        contentValues.put("SERVICE_IMAGE", service.getSerImage());
        return database.insert("tblService",null,contentValues);
    }

    public ArrayList<Room> getAllRooms() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tblRoom", null);
        ArrayList<Room> rooms = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setRoomID(cursor.getInt(0));
                room.setRoomName(cursor.getString(1));
                room.setRoomCapacity(cursor.getInt(2));
                room.setRoomDescription(cursor.getString(3));
                room.setRoomPrice(cursor.getDouble(4));
                room.setRoomImage(cursor.getBlob(5));
                rooms.add(room);
            } while (cursor.moveToNext());
        }
        return rooms;
    }

    public long insertRoomBooking(int roomID, String roomName, double roomPrice, String email, String checkInDate, String checkOutDate) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Room_ID", roomID);
        contentValues.put("Room_Name", roomName);
        contentValues.put("Room_Price", roomPrice);
        contentValues.put("Email", email);
        contentValues.put("CheckInDate", checkInDate);
        contentValues.put("CheckOutDate", checkOutDate);
        return database.insert("tblRoomBooking", null, contentValues);
    }

    public Cursor getAllRoomBookings() {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM tblRoomBooking", null);
    }

    public ArrayList<Service> getAllServices() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tblService", null);
        ArrayList<Service> services = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Service service = new Service();
                service.setSerID(cursor.getInt(0));
                service.setSerName(cursor.getString(1));
                service.setSerDes(cursor.getString(2));
                service.setSerPrice(cursor.getDouble(3));
                service.setSerImage(cursor.getBlob(4));

                services.add(service);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return services;
    }

    public long insertServiceBooking(int serviceID, String serviceName, double servicePrice, String email, String reserveDate) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SERVICE_ID", serviceID);
        contentValues.put("SERVICE_NAME", serviceName);
        contentValues.put("SERVICE_PRICE", servicePrice);
        contentValues.put("Email", email);
        contentValues.put("ReserveDate", reserveDate);
        return database.insert("tblServiceBooking", null, contentValues);
    }

    public ArrayList<ServiceBooking> getAllServiceBookings() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<ServiceBooking> serviceBookings = new ArrayList<>();

        String query = "SELECT * FROM tblServiceBooking";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Fetch data from the cursor and pass it to the ServiceBooking constructor
                String serviceName = cursor.getString(2);
                String reserveDate = cursor.getString(5);
                double servicePrice = cursor.getDouble(3);
                String email = cursor.getString(4);

                ServiceBooking serviceBooking = new ServiceBooking(serviceName, reserveDate, servicePrice, email);
                serviceBookings.add(serviceBooking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return serviceBookings;
    }


}