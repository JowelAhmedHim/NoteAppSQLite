package com.example.noteappbysqldatabase.SqlDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.noteappbysqldatabase.SqlDatabase.FeedReaderContract.*;

import androidx.annotation.Nullable;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyNotes";
    public static final int DATABASE_VERSION = 1;


    public static final String SQL_CREATE_ENTRIES ="CREATE TABLE " + FeedEntry.TABLE_NAME +
            "(" + FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FeedEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            FeedEntry.COLUMN_DESCRIPTION + " TEXT);";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ FeedEntry.TABLE_NAME;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
