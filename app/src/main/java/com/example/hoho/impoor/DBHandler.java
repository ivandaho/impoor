package com.example.hoho.impoor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_FIRST_TABLE = "create table if not exists "
            + TABLE_FINANCEITEMS
            + " ( _id integer primary key autoincrement, "
            + "name TEXT NOT NULL, " // name field
            + "date TEXT NOT NULL, " // date field
            + "amount REAL NOT NULL, " // amount field
            + "gain INTEGER);"; // amount field

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //dropAll();
        sqLiteDatabase.execSQL(CREATE_FIRST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void dropAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS financeItems");
    }

    public void addFinanceItem(FinanceItem fi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", fi.name);
        String dateString = dateFormat.format(fi.date);
        values.put("date", dateString);
        values.put("amount", fi.amount);
        values.put("gain", fi.gain);

        // Inserting Row
        db.insert(TABLE_FINANCEITEMS, null, values);
        db.close(); // Closing database connection
    }

    public void removeFinanceItem(String name, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // String item_name = fi.name;
        // String item_amount = fi.amount.toString();
        // String item_date = fi.date.toString();
        String table = TABLE_FINANCEITEMS;
        String whereClause = "name=? AND amount=?";
        String[] whereArgs = new String[] { name, amount};

        db.delete(table, whereClause, whereArgs);
    }

    public ArrayList<FinanceItem> getFinanceItems() {
        ArrayList<FinanceItem> listOfFinanceItems = new ArrayList<FinanceItem>();
        String selectQuery = "SELECT * FROM " + TABLE_FINANCEITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                boolean gain;
                if (cursor.getInt(4) == 0) {
                    gain = false;
                } else {
                    gain = true;
                }
                Date dateFromDB = new Date();
                try {
                    dateFromDB = dateFormat.parse(cursor.getString(2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                FinanceItem fi = new FinanceItem(cursor.getString(1), dateFromDB, cursor.getDouble(3), gain);
                //FinanceItem fi = new FinanceItem(cursor.getString(1), new Date(), cursor.getDouble(2));
                // Adding contact to list
                listOfFinanceItems.add(fi);
            } while (cursor.moveToNext());
        }

        return listOfFinanceItems;
    }
}

