package com.atcom.nikosstais.warmup.devtest1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleCache;
import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticlesCacheDao;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoriesCache;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoriesCacheDao;

@Database(entities = {ArticleCache.class, CategoriesCache.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "responsesDatabase")
                            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            //.allowMainThreadQueries()
                            // recreate the database if necessary
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
