package com.example.hoho.impoor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hoho on 11/20/16.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static int sortMethod = 0;

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

    public void addFinanceItem(String name, Date date, Double amount, Boolean gain) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        String dateString = dateFormat.format(date);
        values.put("date", dateString);
        values.put("amount", amount);
        values.put("gain", gain);

        // Inserting Row
        db.insert(TABLE_FINANCEITEMS, null, values);
        db.close(); // Closing database connection
    }

    public Double getBalance() {
        String query = "SELECT amount,"
                + "(select sum(amount) FROM " + TABLE_FINANCEITEMS + ") total, gain"
        + " FROM " + TABLE_FINANCEITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        Double balance = 0.0;
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(2) == 0) {
                    balance =- cursor.getDouble(0);
                } else {
                    balance =+ cursor.getDouble(0);
                }
            } while (cursor.moveToNext());
        }
        return balance;
        // Toast.makeText(c, MainActivity.balance.toString(),Toast.LENGTH_SHORT).show();

    }

    public void removeFinanceItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String table = TABLE_FINANCEITEMS;
        String whereClause = "_id=?";
        String[] whereArgs = new String[] { id };

        db.delete(table, "_id=" + id, null);
        // db.delete(table, whereClause, whereArgs);
    }

    public ArrayList<FinanceItem> getFinanceItems(int sortMethod) {
        ArrayList<FinanceItem> listOfFinanceItems = new ArrayList<FinanceItem>();
        String selectQuery = "SELECT * FROM " + TABLE_FINANCEITEMS;

        if (sortMethod == 0) {
            // sort by date of items
            selectQuery += " ORDER BY date DESC";

        } else if (sortMethod == 1) {
            // sort by net type
            selectQuery += " ORDER BY gain, date DESC";
        } else if (sortMethod == 2) {
            // sort by net amount
            selectQuery += " ORDER BY gain DESC, amount DESC";
        } else if (sortMethod == 3) {
            // sort by inserted time
            selectQuery += " ORDER BY _id DESC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Double testbalance = 0.0;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                boolean gain;
                if (cursor.getInt(4) == 0) {
                    gain = false;
                    testbalance = testbalance - cursor.getDouble(3);
                } else {
                    gain = true;
                    testbalance = testbalance + cursor.getDouble(3);
                }
                Date dateFromDB = new Date();
                try {
                    dateFromDB = dateFormat.parse(cursor.getString(2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                MainActivity.balance = testbalance;
                FinanceItem fi = new FinanceItem(cursor.getString(1), dateFromDB, cursor.getDouble(3), gain, cursor.getInt(0));
                //FinanceItem fi = new FinanceItem(cursor.getString(1), new Date(), cursor.getDouble(2));
                // Adding contact to list
                listOfFinanceItems.add(fi);
            } while (cursor.moveToNext());
        }

        return listOfFinanceItems;
    }
}

