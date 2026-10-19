package com.example.hotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Hotel.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE room_booking (id INTEGER PRIMARY KEY AUTOINCREMENT, room_id TEXT, room_name TEXT, room_price TEXT, email TEXT, check_in_date TEXT, check_out_date TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS room_booking");
        onCreate(db);
    }

    public long insert(String table, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(table, null, values);
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM room_booking", null);

        if (cursor.moveToFirst()) {
            do {
                bookingList.add(new Booking(
                        cursor.getString(cursor.getColumnIndex("room_id")),
                        cursor.getString(cursor.getColumnIndex("room_name")),
                        cursor.getString(cursor.getColumnIndex("room_price")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("check_in_date")),
                        cursor.getString(cursor.getColumnIndex("check_out_date"))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookingList;
    }



}
