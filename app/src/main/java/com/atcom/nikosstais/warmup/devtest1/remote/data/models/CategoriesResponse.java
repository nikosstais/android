package com.atcom.nikosstais.warmup.devtest1.remote.data.models;

import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nikos on 06/04/18.
 */

public class CategoriesResponse extends NewsArticlesResponse implements Serializable {
    @SerializedName("categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
