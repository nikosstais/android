package com.atcom.nikosstais.warmup.devtest1.database.categories;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "CategoryResponses",
        indices = {@Index(value = "dateInserted")}
)
public class CategoryResponses {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String dateInserted;
    public String responseText;
}
