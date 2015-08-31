package com.example.guyazran.logginguserlocationinbackground;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by guyazran on 8/27/15.
 */
public class DBAdapter {
    static final String KEY_DATE = "date";
    static final String KEY_LAT = "lat";
    static final String KEY_LNG = "lng";

    static final String DATABASE_NAME = "MyDB.db";
    static final String DATABASE_TABLE = "Locations";
    static final int DATABASE_VERSION = 1;

    final Context context;
    private SQLiteDatabase db;
    DatabaseHelper dbHelper;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + DATABASE_TABLE + "(" +
                    KEY_DATE + " INTEGER," +
                    KEY_LAT + " REAL," +
                    KEY_LNG + " REAL" +
                    ")";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public long insertLocation(double lat, double lng){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DATE, System.currentTimeMillis());
        contentValues.put(KEY_LAT, lat);
        contentValues.put(KEY_LNG, lng);
        return db.insert(DATABASE_TABLE, null, contentValues);
    }

    public Cursor getAllLocations(){
        return db.query(DATABASE_TABLE, new String[]{KEY_DATE, KEY_LAT, KEY_LNG}, null, null, null, null, null);
    }



}
