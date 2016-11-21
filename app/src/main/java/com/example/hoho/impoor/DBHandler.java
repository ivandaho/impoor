package com.example.hoho.impoor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hoho on 11/20/16.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mainDB";
    private static final String TABLE_FINANCEITEMS = "financeItems";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_FIRST_TABLE = "create table if not exists "
            + TABLE_FINANCEITEMS
            + " ( _id integer primary key autoincrement, "
            + "name TEXT NOT NULL, " // name field
            + "amount REAL NOT NULL);"; // amount field

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FIRST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addFinanceItem(FinanceItem fi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", fi.name);
        values.put("amount", fi.amount);

        // Inserting Row
        db.insert(TABLE_FINANCEITEMS, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<FinanceItem> getFinanceItems() {
        ArrayList<FinanceItem> listOfFinanceItems = new ArrayList<FinanceItem>();
        String selectQuery = "SELECT * FROM " + TABLE_FINANCEITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FinanceItem shop = new FinanceItem(cursor.getString(1), new Date(), cursor.getDouble(2));
                // Adding contact to list
                listOfFinanceItems.add(shop);
            } while (cursor.moveToNext());
        }

        return listOfFinanceItems;
    }
}

