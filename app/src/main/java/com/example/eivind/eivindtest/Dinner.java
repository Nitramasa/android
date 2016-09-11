package com.example.eivind.eivindtest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Eivind on 23.08.2016.
 * Object class for dinners!
 */
public class Dinner  {
    public static final String TABLE_DINNER = "dinner";
    public static final String DINNER_ID = "id";
    public static final String DINNER_TEXT = "din_text";
    public static final String DINNER_DAY = "day";
    public static final String DINNER_INFO = "info";
    private DbHelper db;
    protected String dinner;
    protected String day;
    protected String info;
    protected String id;
    private Context context;
    public Dinner(){
        super();
    }
    public Dinner(Context context){
        db = new DbHelper(context);
    }

    public boolean save() {

        Log.w("DinnerS","Dinner: " + this.dinner + " Info: " + this.info + " Day: " + this.day);

        SQLiteDatabase myDb = this.db.getWritableDatabase();
        //myDb.beginTransaction();
        if(!this.id.isEmpty()){
            long res = addOrUpdateDinner();
            if(res > 0) {
                return true;
            }
        }
        ContentValues val = new ContentValues();
        val.put(DINNER_TEXT, this.dinner);
        val.put(DINNER_DAY, this.day);
        val.put(DINNER_INFO, this.info);
        long newId;
        newId = myDb.insert(TABLE_DINNER, null, val);
        //myDb.setTransactionSuccessful();

        if(newId > 0)
            return true;
        else
            return false;
    }

    public static String getDinnerTable(){
        String query = "CREATE TABLE " + TABLE_DINNER + "(" +
                DINNER_ID + " INTEGER PRIMARY KEY, " +
                DINNER_TEXT + " TEXT, " +
                DINNER_DAY + " TEXT, " +
                DINNER_INFO + " TEXT)";

        return query;
    }

    public String getDay(){
        Date d = new Date();
        return (String) android.text.format.DateFormat.format("EEEE", d);
    }

    public List<Dinner> getDinners(){
        List<Dinner> dinners = new ArrayList<>();
        SQLiteDatabase myDb = this.db.getReadableDatabase();

        String[] projection = {DINNER_ID, DINNER_TEXT, DINNER_DAY, DINNER_INFO};
        String sortOrder = DINNER_DAY + " DESC";

        Cursor c = myDb.query(
                TABLE_DINNER,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(c.moveToFirst()){
            do {
                Dinner newDinner = new Dinner();
                newDinner.id = c.getString(c.getColumnIndex(DINNER_ID));
                newDinner.dinner = c.getString(c.getColumnIndex(DINNER_TEXT));
                newDinner.info = c.getString(c.getColumnIndex(DINNER_INFO));
                newDinner.day = c.getString(c.getColumnIndex(DINNER_DAY));
                dinners.add(newDinner);
            } while(c.moveToNext());
        }
        return dinners;
    }

    public void deleteAllDinners() {
        SQLiteDatabase myDb = this.db.getWritableDatabase();
        myDb.beginTransaction();
        try {
            myDb.delete(TABLE_DINNER, null, null);
            myDb.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d("Error", "Error while trying to delete all dinners");
        }
        finally {
            myDb.endTransaction();
        }
    }
    public long addOrUpdateDinner() {
        SQLiteDatabase myDb = this.db.getWritableDatabase();
        long dinId = -1;

        ContentValues values = new ContentValues();
        //VAlUES PUT HERE
        values.put(DINNER_TEXT, this.dinner);
        values.put(DINNER_DAY, this.day);
        values.put(DINNER_INFO, this.info);

        int rows = myDb.update(TABLE_DINNER, values, DINNER_ID +"= ?", new String[]{String.valueOf(this.id)});
        if(rows == 1){
            String dinQuery = String.format("Select %s From %s Where %s = ?", DINNER_ID, TABLE_DINNER, DINNER_ID);
            Cursor cursor = myDb.rawQuery(dinQuery, new String[]{String.valueOf(this.id)});

            if(cursor.moveToFirst()){
                dinId = cursor.getInt(0);
            }
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        else {
            dinId = myDb.insert(TABLE_DINNER, null, values);
        }
        return dinId;
    }

    public Dinner getDinner(){
        SQLiteDatabase myDb = this.db.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = myDb.rawQuery("Select * from " + TABLE_DINNER + " where id =?", new String[] {this.id});
            Dinner d = new Dinner();
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                d.id = cursor.getString(cursor.getColumnIndex(DINNER_ID));
                d.dinner = cursor.getString(cursor.getColumnIndex(DINNER_TEXT));
                d.day = cursor.getString(cursor.getColumnIndex(DINNER_DAY));
                d.info = cursor.getString(cursor.getColumnIndex(DINNER_INFO));
            }
            return d;
        }
        finally {
            cursor.close();
        }

    }
    public String toString(){
        return dinner + "\t\t\t" + info;
    }

}
