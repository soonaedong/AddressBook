package com.example.dhkim.address;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, "memodb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "CREATE TABLE TB_ADDRESS" +
                "(name, phone_num, address)";
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
