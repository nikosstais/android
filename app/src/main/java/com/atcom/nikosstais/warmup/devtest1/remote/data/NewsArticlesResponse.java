package com.atcom.nikosstais.warmup.devtest1.remote.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * Created by nikos on 06/04/18.
 */

public class NewsArticlesResponse  implements Serializable {

    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
