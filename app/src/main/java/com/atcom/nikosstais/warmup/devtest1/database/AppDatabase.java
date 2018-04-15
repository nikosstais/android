package com.atcom.nikosstais.warmup.devtest1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleCache;
import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticlesCacheDao;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoriesCache;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoriesCacheDao;
import com.atcom.nikosstais.warmup.devtest1.system.AndroidTestApplication;

@Database(entities = {ArticleCache.class, CategoriesCache.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase() {
        if (INSTANCE == null) {
            Context context = AndroidTestApplication.getInstance().getApplicationContext();
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "responsesDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ArticlesCacheDao articlesCacheDao();

    public abstract CategoriesCacheDao categoriesCacheDao();
}
