package com.zemoso.rideapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 11/1/16.
 */
public class MYSQLiteHelper extends SQLiteOpenHelper

{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";
    private static final String TABLE_DISPLAYS = "displays";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";//
    private static final String KEY_MOBILENUMBER = "mobilenumber";
    private static final String KEY_STARTINGPLACE = "startingplace";
    private static final String KEY_RIDEDISTANCE = "ridedistance";
    private static final String KEY_TIMETAKEN = "timetaken";
    private static final String KEY_WAITINGTIME = "waitingtime";
    private static final String KEY_CABTYPE = "cabtype";
    private static final String KEY_BOOKINGTIME = "bookingtime";
    private static final String[] COLUMNS = {KEY_ID,KEY_USERNAME,KEY_MOBILENUMBER,KEY_STARTINGPLACE,KEY_RIDEDISTANCE,KEY_TIMETAKEN,
    KEY_WAITINGTIME,KEY_CABTYPE,KEY_BOOKINGTIME};


    public MYSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DISPLAY_TABLE = "CREATE TABLE displays ( " +


                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT,"+
                "mobilenumber TEXT,"+
                "startingplace TEXT,"+
                "ridedistance TEXT,"+
                "timetaken TEXT,"+
                "waitingtime TEXT,"+
                "cabtype TEXT , " +
                "bookingtime Timestamp)";
                db.execSQL(CREATE_DISPLAY_TABLE);;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        this.onCreate(db);
    }
    public void addDisplay(Display display ){
        Log.d("addDisplay", display.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME,display.getUsername());
        values.put(KEY_MOBILENUMBER,display.getMobilenumber());
        values.put(KEY_STARTINGPLACE,display.getStartingplace());
        values.put(KEY_RIDEDISTANCE,display.getRidedistance());
        values.put(KEY_TIMETAKEN,display.getTimetaken());
        values.put(KEY_WAITINGTIME,display.getWaitingtime());
        values.put(KEY_CABTYPE,display.getCabtype());
        values.put(KEY_BOOKINGTIME,display.getBoookingTime().getTime());
        db.insert(TABLE_DISPLAYS, null, values);
        db.close();
    }

    public Display getDisplay(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISPLAYS, COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Display display = new Display();
        display.setId(cursor.getInt(0));
        display.setUsername(cursor.getString(1));
        display.setMobilenumber(cursor.getString(2));
        display.setStartingplace(cursor.getString(2));
        display.setRidedistance(cursor.getString(2));
        display.setWaitingtime(cursor.getString(2));
        display.setTimetaken(cursor.getString(2));
        display.setCabtype(cursor.getString(2));
        Log.d("getDisplay(" + id + ")", display.toString());
      return display;
    }
    public List<Display> getAllDisplay() {
        List<Display> display = new ArrayList<Display>();
        String query = "SELECT  * FROM " + TABLE_DISPLAYS+" Order by id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Display Display = null;
        if (cursor.moveToFirst()) {
            do {
                Display = new Display();
                Display.setId(Integer.parseInt(cursor.getString(0)));
                Display.setUsername(cursor.getString(1));
                Display.setMobilenumber(cursor.getString(2));
                Display.setStartingplace(cursor.getString(3));
                Display.setRidedistance(cursor.getString(4));
                Display.setTimetaken(cursor.getString(5));
                Display.setWaitingtime(cursor.getString(6));
                Display.setCabtype(cursor.getString(7));
                Display.setBoookingTime(new Timestamp(cursor.getLong(8)));
                display.add(Display);
            }
            while (cursor.moveToNext());
            }
        db.close();
        cursor.close();
        return display;
    }

    public int updateDisplay(Display display) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, display.getUsername());
        values.put(KEY_MOBILENUMBER, display.getMobilenumber());
        values.put(KEY_STARTINGPLACE, display.getStartingplace());
        values.put(KEY_RIDEDISTANCE,display.getRidedistance() );
        values.put(KEY_TIMETAKEN, display.getWaitingtime());
        values.put(KEY_WAITINGTIME, display.getTimetaken());
        values.put(KEY_CABTYPE, display.getCabtype());

        int i = db.update(TABLE_DISPLAYS, values, KEY_ID+" = ?", new String[] { String.valueOf(display.getId()) });
        db.close();
        return i;

    }


    public void deleteDisplay(Display display) {


        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAYS, KEY_ID+" = ?", new String[] { Integer.toString(display.getId()) });
        db.close();
        Log.d("deleteDisplay", display.toString());

    }

}
