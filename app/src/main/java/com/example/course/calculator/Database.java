package com.example.course.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by course on 24/12/17.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Calculator";
    public static final String SCORE_TABLE_NAME = "Scoreboard";
    public static final String SCORE_COLUMN_ID = "id";
    public static final String SCORE_COLUMN_NAME = "name";
    public static final String SCORE_COLUMN_SCORE = "score";

    public Database(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + SCORE_TABLE_NAME  +
                        "( id integer primary key AUTOINCREMENT NOT NULL , name text ,score integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS SCORE_TABLE_NAME");
        onCreate(db);
    }

    boolean nameExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db,
                "Scoreboard", "name = ?", new String[] { name });
        return count > 0;
    }
    public long getId(){
        ArrayList<Long> cursorArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT id FROM Scoreboard",null);
        if(cursor.moveToFirst()) {
            do {
                cursorArrayList.add(cursor.getLong(0));
            }
            while (cursor.moveToNext()) ;
        }
        Log.i("check","ids"+cursorArrayList.toString());
        return cursorArrayList.get(cursor.getCount()-1);
    }

    public void insertScore(int score, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("score",score);
        db.update(SCORE_TABLE_NAME, contentValues,SCORE_COLUMN_ID+"="+id,null);
    }public long insertName (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        long id = db.insert("Scoreboard", null, contentValues);
        return id;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Scoreboard ", null );
        return res;
    }
    public ArrayList<String> getname() {
        ArrayList<String> data = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SCORE_COLUMN_NAME+" FROM "+SCORE_TABLE_NAME+" ORDER BY "+SCORE_COLUMN_SCORE+" DESC ",null);
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return data;
    }
    public ArrayList<Integer> getscore() {
        ArrayList<Integer> data = new ArrayList<Integer>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SCORE_COLUMN_SCORE +" FROM "+SCORE_TABLE_NAME+" ORDER BY "+SCORE_COLUMN_SCORE+" DESC ", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getInt(0));

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return data;
    }

}