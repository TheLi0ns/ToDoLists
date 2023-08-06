package io.github.theli0ns.todolists;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDoList.db";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseSchema.Lists.CREATE_STMT);
        sqLiteDatabase.execSQL(DatabaseSchema.ListsItems.CREATE_STMT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public ArrayList<ListRecord> selectAllLists(){
        SQLiteDatabase db = getReadableDatabase();
        String select_stmt = "SELECT * FROM " + DatabaseSchema.Lists.TABLE_NAME;
        Cursor cursor = db.rawQuery(select_stmt, null);

        ArrayList<ListRecord> records = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                ListRecord listRecord = new ListRecord(
                        cursor.getInt(DatabaseSchema.Lists._ID_COLUMN_INDEX),
                        cursor.getString(DatabaseSchema.Lists.NAME_COLUMN_INDEX),
                        ListColors.getByName(cursor.getString(DatabaseSchema.Lists.COLOR_COLUMN_INDEX))
                );
                records.add(listRecord);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return records;
    }

    public long addNewList(ListRecord list){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.Lists.NAME_COLUMN, list.getName());
        values.put(DatabaseSchema.Lists.COLOR_COLUMN, list.getColor().name);

        long ID = db.insert(DatabaseSchema.Lists.TABLE_NAME, null, values);

        db.close();
        return ID;
    }

    public boolean deleteList(ListRecord list){
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = DatabaseSchema.Lists._ID + "=" + list.getID();
        int success = db.delete(DatabaseSchema.Lists.TABLE_NAME, whereClause, null);

        db.close();
        return success > 0;
    }

    public ArrayList<ListItemRecord> selectAllListItemOnListId(long listId){
        SQLiteDatabase db = getReadableDatabase();
        String select_stmt =
                "SELECT * FROM " + DatabaseSchema.ListsItems.TABLE_NAME +
                " WHERE " + DatabaseSchema.ListsItems.LIST_ID_COLUMN + " = " + listId;
        Cursor cursor = db.rawQuery(select_stmt, null);

        ArrayList<ListItemRecord> records = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                ListItemRecord listRecord = new ListItemRecord(
                        cursor.getInt(DatabaseSchema.ListsItems._ID_COLUMN_INDEX),
                        cursor.getInt(DatabaseSchema.ListsItems.LIST_ID_COLUMN_INDEX),
                        cursor.getString(DatabaseSchema.ListsItems.TEXT_COLUMN_INDEX),
                        cursor.getInt(DatabaseSchema.ListsItems.STATE_COLUMN_INDEX)
                );
                records.add(listRecord);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return records;
    }

    public long addNewListItem(ListItemRecord listItem){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ListsItems.LIST_ID_COLUMN, listItem.getList_id());
        values.put(DatabaseSchema.ListsItems.TEXT_COLUMN, listItem.getText());
        values.put(DatabaseSchema.ListsItems.STATE_COLUMN, listItem.getState());

        long success = db.insert(DatabaseSchema.ListsItems.TABLE_NAME, null, values);

        db.close();
        return success;
    }

    public boolean deleteListItem(ListItemRecord listItem){
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = DatabaseSchema.ListsItems._ID + "=" + listItem.getID();
        int success = db.delete(DatabaseSchema.ListsItems.TABLE_NAME, whereClause, null);

        db.close();
        return success > 0;
    }
}
