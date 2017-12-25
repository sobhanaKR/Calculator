package com.example.course.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by course on 24/12/17.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Calculator";
    public static final String SCORE_TABLE_NAME = "Scoreboard";
    public static final String SCORE_COLUMN_ID = "id";
    public static final String SCORE_COLUMN_NAME = "name";
    public static final String SCORE_COLUMN_SCORE = "score";
    private HashMap hp;

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

    public boolean insertScore (String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("score",score);
        db.insert("Scoreboard", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Scoreboard where id="+id+"", null );
        return res;
    }
    public ArrayList<String> getname() {
        ArrayList<String> data = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SCORE_COLUMN_NAME+" FROM "+SCORE_TABLE_NAME+" ORDER BY "+SCORE_COLUMN_SCORE+" DESC ", null);
        // looping through all rows and adding to list
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
        Cursor cursor = db.rawQuery("SELECT "+SCORE_COLUMN_SCORE +" FROM "+SCORE_TABLE_NAME +" ORDER BY "+SCORE_COLUMN_SCORE+" DESC", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getInt(1));

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return data;
    }
    public boolean updateScore (Integer id, String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("score",score);
        db.update("ScoreBoard", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteScore (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Scoreboard",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

}