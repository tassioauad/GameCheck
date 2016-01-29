package com.tassioauad.gamecheck.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.gamecheck.model.database.GameRatingDao;
import com.tassioauad.gamecheck.model.database.PlatformRatingDao;
import com.tassioauad.gamecheck.model.database.impl.DatabaseConnection;
import com.tassioauad.gamecheck.model.database.impl.GameRatingDaoImpl;
import com.tassioauad.gamecheck.model.database.impl.PlatformRatingDaoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = AppModule.class)
public class DaoModule {

    @Singleton
    @Provides
    public SQLiteDatabase provideSQLiteDatabase(Context context) {
        return new DatabaseConnection(context).getWritableDatabase();
    }

    @Provides
    public PlatformRatingDao providePlatformRatingDao(SQLiteDatabase database) {
        return new PlatformRatingDaoImpl(database);
    }

    @Provides
    public GameRatingDao provideGameRatingDao(SQLiteDatabase database) {
        return new GameRatingDaoImpl(database);
    }

}
