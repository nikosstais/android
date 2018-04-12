package com.atcom.nikosstais.warmup.devtest1.database.articles;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticlesCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addArticlesToCache(ArticleCache ArticleCache);

    @Query("select * from ArticleCache order by id desc")
    List<ArticleCache> getLatestArticlesFromCache();

    @Query("delete from ArticleCache")
    void cleanArticlesCache();

//    @Query("select * from ArticleCache where id = :ArticleResponsesId")
//    List<ArticleCache> getArticleResponses(long ArticleResponsesId);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateArticleResponses(ArticleCache ArticleCache);
}
