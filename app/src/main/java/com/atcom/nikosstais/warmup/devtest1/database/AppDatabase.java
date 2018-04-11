package com.atcom.nikosstais.warmup.devtest1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleResponses;
import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleResponsesDao;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoryResponses;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoryResponsesDao;
@Database(entities = {ArticleResponses.class,  CategoryResponses.class},
        version = 16,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ArticleResponsesDao articleResponsesDao();
    public abstract CategoryResponsesDao categoryResponsesDao();

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
}
