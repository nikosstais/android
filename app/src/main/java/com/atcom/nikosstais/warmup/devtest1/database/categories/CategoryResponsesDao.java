package com.atcom.nikosstais.warmup.devtest1.database.categories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface CategoryResponsesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategoryResponses(CategoryResponses CategoryResponses);

    @Query("select * from CategoryResponses order by id desc")
    List<CategoryResponses> getAllCategoryResponses();

    @Query("select * from CategoryResponses where id = :CategoryResponsesId")
    List<CategoryResponses> getCategoryResponses(long CategoryResponsesId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCategoryResponses(CategoryResponses CategoryResponses);

    @Query("delete from CategoryResponses")
    void removeAllCategoryResponsess();
}
