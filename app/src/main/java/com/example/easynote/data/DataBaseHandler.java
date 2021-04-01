package com.example.easynote.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.easynote.model.Item;
import com.example.easynote.ui.Constant;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {


    public DataBaseHandler(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + Constant.DATABASE_TABLE_NAME + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constant.KEY_NOTES_MAIN + " TEXT,"
                + Constant.KEY_NOTES_ADD + " TEXT,"
                + Constant.KEY_DATA_NAME + " LONG" + ")";
        db.execSQL(CREATE_NOTES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf("DROP TABLE IF EXIST ");
        db.execSQL(DROP_TABLE + Constant.DATABASE_TABLE_NAME);
        onCreate(db);
    }


    public void addNotes(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_NOTES_MAIN, item.getNotesMain());
        values.put(Constant.KEY_NOTES_ADD, item.getNotesAdd());
        values.put(Constant.KEY_DATA_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constant.DATABASE_TABLE_NAME, null, values);
        Log.d("DBHandler", "added item: ");

        db.close();


    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constant.DATABASE_TABLE_NAME, new String[]{Constant.KEY_ID, Constant.KEY_NOTES_MAIN, Constant.KEY_NOTES_ADD, Constant.KEY_DATA_NAME},
                Constant.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
            item.setNotesMain(cursor.getString(cursor.getColumnIndex(Constant.KEY_NOTES_MAIN)));
            item.setNotesAdd(cursor.getString(cursor.getColumnIndex(Constant.KEY_NOTES_ADD)));


            DateFormat dateFormat = DateFormat.getDateInstance();
            String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATA_NAME))).getTime());
            item.setDateItemAdded(formatDate);
        }

        return item;
    }

    public List<Item> getAllItem() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();


        Cursor cursor = db.query(Constant.DATABASE_TABLE_NAME,
                new String[]{Constant.KEY_ID,
                        Constant.KEY_NOTES_MAIN,
                        Constant.KEY_NOTES_ADD,
                        Constant.KEY_DATA_NAME},
                null, null, null, null, Constant.KEY_DATA_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
                item.setNotesMain(cursor.getString(cursor.getColumnIndex(Constant.KEY_NOTES_MAIN)));
                item.setNotesAdd(cursor.getString(cursor.getColumnIndex(Constant.KEY_NOTES_ADD)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATA_NAME))).getTime());
                item.setDateItemAdded(formatDate);


                itemList.add(item);

            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_NOTES_MAIN, item.getNotesMain());
        values.put(Constant.KEY_NOTES_ADD, item.getNotesAdd());
        values.put(Constant.KEY_DATA_NAME, java.lang.System.currentTimeMillis());

        return db.update(Constant.DATABASE_TABLE_NAME, values,
                Constant.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.DATABASE_TABLE_NAME, Constant.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
    }

public  int getItemsCount(){
        String countQuery="SELECT * FROM " + Constant.DATABASE_TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        return cursor.getCount();
}

}
