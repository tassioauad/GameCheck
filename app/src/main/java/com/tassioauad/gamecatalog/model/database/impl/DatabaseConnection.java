package com.tassioauad.gamecatalog.model.database.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game-check";
    private static final int DATABASE_VERSION = 1;
    private final String createPlatformRatingTable = "CREATE TABLE " + PlatformRatingDaoImpl.TABLE_NAME + " (" +
            PlatformRatingDaoImpl.COLUMN_ID_PLATFORM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PlatformRatingDaoImpl.COLUMN_RATING + " REAL NOT NULL)";
    private final String createGameRatingTable = "CREATE TABLE " + GameRatingDaoImpl.TABLE_NAME + " (" +
            GameRatingDaoImpl.COLUMN_ID_GAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GameRatingDaoImpl.COLUMN_RATING + " REAL NOT NULL)";

    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPlatformRatingTable);
        db.execSQL(createGameRatingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
