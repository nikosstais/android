package com.atcom.nikosstais.warmup.devtest1.database.categories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoriesCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategoriesToCache(CategoriesCache CategoriesCache);

    @Query("select * from CategoriesCache order by id desc")
    List<CategoriesCache> getLatestCategoriesFromCache();

    @Query("delete from CategoriesCache")
    void cleanCategoriesCache();

    @Query("select * from CategoriesCache where id = :id")
    CategoriesCache getCategoryById(long id);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateCategoryResponses(CategoriesCache categoriesCache);
}
