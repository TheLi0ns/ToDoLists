package io.github.theli0ns.todolists;

import android.provider.BaseColumns;

public final class DatabaseSchema {

    private DatabaseSchema() {}

    public static class Lists implements BaseColumns{
        public static final String CREATE_STMT =
                "CREATE TABLE " + Lists.TABLE_NAME + " ( " +
                Lists._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Lists.NAME_COLUMN + " TEXT NOT NULL, " +
                Lists.COLOR_COLUMN + " VARCHAR(7) NOT NULL )";
        public static final String TABLE_NAME = "Lists";
        public static final int _ID_COLUMN_INDEX = 0;
        public static final String NAME_COLUMN = "Name";
        public static final int NAME_COLUMN_INDEX = 1;
        public static final String COLOR_COLUMN = "Color";
        public static final int COLOR_COLUMN_INDEX = 2;
    }

    public static class ListsItems implements BaseColumns{
        public static final String CREATE_STMT =
                "CREATE TABLE " + ListsItems.TABLE_NAME + " ( " +
                ListsItems._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ListsItems.LIST_ID_COLUMN + " INTEGER NOT NULL, " +
                ListsItems.TEXT_COLUMN + " TEXT NOT NULL, " +
                ListsItems.STATE_COLUMN + " INTEGER NOT NULL," +
                "CONSTRAINT fk_list_id " +
                "FOREIGN KEY ( " + ListsItems.LIST_ID_COLUMN + " ) " +
                "REFERENCES " + Lists.TABLE_NAME + "( " + Lists._ID + " )" +
                "ON DELETE CASCADE )";
        public static final String TABLE_NAME = "ListsItems";
        public static final int _ID_COLUMN_INDEX = 0;
        public static final String LIST_ID_COLUMN = "List_ID";
        public static final int LIST_ID_COLUMN_INDEX = 1;
        public static final String TEXT_COLUMN = "Text";
        public static final int TEXT_COLUMN_INDEX = 2;
        public static final String STATE_COLUMN = "State";
        public static final int STATE_COLUMN_INDEX = 3;
    }

}
