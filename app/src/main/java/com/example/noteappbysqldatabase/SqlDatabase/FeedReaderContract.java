package com.example.noteappbysqldatabase.SqlDatabase;

import android.provider.BaseColumns;

public class FeedReaderContract {

    public FeedReaderContract() {
    }

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "mynotes";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";

    }
}
