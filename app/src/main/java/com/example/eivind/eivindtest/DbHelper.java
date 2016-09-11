package com.example.eivind.eivindtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eivind on 22.08.2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DinnerPlanner.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String dinner = Dinner.getDinnerTable();
        db.execSQL(dinner);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Dinner.TABLE_DINNER);
            onCreate(db);
        }
    }
}
