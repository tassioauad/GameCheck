package com.tassioauad.gamecatalog.model.database.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.gamecatalog.model.database.Dao;
import com.tassioauad.gamecatalog.model.database.PlatformRatingDao;
import com.tassioauad.gamecatalog.model.entity.Platform;

public class PlatformRatingDaoImpl extends Dao implements PlatformRatingDao {

    public static final String TABLE_NAME = "platform_rating";
    public static final String COLUMN_ID_PLATFORM = "platform_id";
    public static final String COLUMN_RATING = "rating";

    public PlatformRatingDaoImpl(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public boolean insert(Platform platform, float rating) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_PLATFORM, platform.getId());
        contentValues.put(COLUMN_RATING, rating);

        return getDatabase().insert(TABLE_NAME, COLUMN_ID_PLATFORM, contentValues) != -1;
    }

    @Override
    public boolean update(Platform platform, float rating) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RATING, rating);

        return getDatabase().update(TABLE_NAME, contentValues,
                COLUMN_ID_PLATFORM + " = ?", new String[]{String.valueOf(platform.getId())}) != 0;
    }

    @Override
    public Float getRatingOf(Platform platform) {
        Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{COLUMN_RATING},
                COLUMN_ID_PLATFORM + " = ?", new String[]{String.valueOf(platform.getId())}, null, null, null, null);

        if(cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }

        return null;
    }

}
