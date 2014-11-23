package com.rob.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roberth on 11/22/14.
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE people (dni INTEGER PRIMARY KEY, name TEXT, university TEXT, subject TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int previous, int next) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS people");
        sqLiteDatabase.execSQL("CREATE TABLE people (dni INTEGER PRIMARY KEY, name TEXT, university TEXT, subject TEXT)");
    }
}
