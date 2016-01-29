package com.tassioauad.gamecheck.model.database.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.gamecheck.model.database.Dao;
import com.tassioauad.gamecheck.model.database.GameRatingDao;
import com.tassioauad.gamecheck.model.entity.Game;

public class GameRatingDaoImpl extends Dao implements GameRatingDao {

    public static final String TABLE_NAME = "game_rating";
    public static final String COLUMN_ID_GAME = "game_id";
    public static final String COLUMN_RATING = "rating";


    public GameRatingDaoImpl(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public boolean insert(Game game, float rating) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_GAME, game.getId());
        contentValues.put(COLUMN_RATING, rating);

        return getDatabase().insert(TABLE_NAME, COLUMN_ID_GAME, contentValues) != -1;
    }

    @Override
    public boolean update(Game game, float rating) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RATING, rating);

        return getDatabase().update(TABLE_NAME, contentValues,
                COLUMN_ID_GAME + " = ?", new String[]{String.valueOf(game.getId())}) != 0;
    }

    @Override
    public Float getRatingOf(Game game) {
        Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{COLUMN_RATING},
                COLUMN_ID_GAME + " = ?", new String[]{String.valueOf(game.getId())}, null, null, null, null);

        if(cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }

        return null;
    }
}
