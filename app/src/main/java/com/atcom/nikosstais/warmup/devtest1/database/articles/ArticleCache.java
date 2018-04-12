package com.atcom.nikosstais.warmup.devtest1.database.articles;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ArticleCache"
        //,indices = {@Index(value = "dateInserted")}
)
public class ArticleCache {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String dateInserted;
    public String responseText;
}
