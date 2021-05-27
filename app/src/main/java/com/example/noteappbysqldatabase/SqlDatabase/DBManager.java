package com.example.noteappbysqldatabase.SqlDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.noteappbysqldatabase.SqlDatabase.FeedReaderContract.*;
import com.example.noteappbysqldatabase.widget.ToastMessage;

public class DBManager {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException{

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }


    public long insert(String title,String desc){

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedEntry.COLUMN_TITLE,title);
        contentValues.put(FeedEntry.COLUMN_DESCRIPTION,desc);
        long id = database.insert(FeedEntry.TABLE_NAME, null,contentValues);
        database.close();
        return id;

    }

    public Cursor readAllData() {

        database = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME;
        Cursor cursor = null;
        if (database!=null){
            cursor = database.rawQuery(query,null);
        }
        return cursor;

    }

    public  int update(String id,String name,String desc){

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedEntry.COLUMN_TITLE,name);
        contentValues.put(FeedEntry.COLUMN_DESCRIPTION,desc);
        int result = database.update(FeedEntry.TABLE_NAME,contentValues,"id=?",new String[]{id});
        database.close();
        return result;
    }

    public void delete(String id){
        long result = database.delete(FeedEntry.TABLE_NAME,"id=?",new String[]{id});
        database.close();
        if (result == -1)
        {
            ToastMessage.message(context,"Item not deleted");
        }else {
            ToastMessage.message(context,"Item deleted Successfully");
        }
    }
    public void deleteAllNote() {
        String query = "DELETE FROM "+ FeedEntry.TABLE_NAME;
        database.execSQL(query);
    }

    public int numberOfRows(){

        database = databaseHelper.getReadableDatabase();
        int numRow = (int)DatabaseUtils.queryNumEntries(database,FeedEntry.TABLE_NAME);
        return numRow;

    }




}
