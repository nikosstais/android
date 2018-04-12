package com.atcom.nikosstais.warmup.devtest1.database.articles;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ArticleResponses",
        indices = {@Index(value = "dateInserted")}
)
public class ArticleResponses {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String dateInserted;
    public String responseText;
}
