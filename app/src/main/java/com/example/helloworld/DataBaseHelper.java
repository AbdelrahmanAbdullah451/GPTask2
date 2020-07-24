package com.example.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String POINTS_TABLE = "POINTS_TABLE";
    public static final String X_POINTS_COLUMN = "X_POINTS";
    public static final String Y_POINTS_COLUMN = "Y_POINTS";
    public static final String Z_POINTS_COLUMN = "Z_POINTS";
    public static final String STREAM_FLAG_COLUMN = "STREAM_FLAG";
    public static final String ID_COLUMN = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "AccelerometerData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + POINTS_TABLE + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT , " + X_POINTS_COLUMN + " REAL , " + Y_POINTS_COLUMN + " REAL , " + Z_POINTS_COLUMN + " REAL , " + STREAM_FLAG_COLUMN + " BOOL ) ";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addRecord(Point point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(X_POINTS_COLUMN,point.getX());
        cv.put(Y_POINTS_COLUMN,point.getY());
        cv.put(Z_POINTS_COLUMN,point.getZ());
        cv.put(STREAM_FLAG_COLUMN,point.isStreamFlag());

        long insert= db.insert(POINTS_TABLE,null,cv);
        if (insert == -1){
            return false;
        }
        else
        {
            return true;
        }

    }
}
