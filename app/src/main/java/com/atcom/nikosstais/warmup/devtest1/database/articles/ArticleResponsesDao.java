package com.atcom.nikosstais.warmup.devtest1.database.articles;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ArticleResponsesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArticleResponses(ArticleResponses ArticleResponses);

    @Query("select * from ArticleResponses order by id desc")
    List<ArticleResponses> getAllArticleResponses();

    @Query("select * from ArticleResponses where id = :ArticleResponsesId")
    List<ArticleResponses> getArticleResponses(long ArticleResponsesId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticleResponses(ArticleResponses ArticleResponses);

    @Query("delete from ArticleResponses")
    void removeAllArticleResponsess();
}
