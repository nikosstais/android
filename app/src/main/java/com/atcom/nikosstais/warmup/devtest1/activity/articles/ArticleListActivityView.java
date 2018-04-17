package com.atcom.nikosstais.warmup.devtest1.activity.articles;

import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;

import java.util.List;

/**
 * Created by nikos on 15/04/18.
 */

public interface ArticleListActivityView {
    void displayNews(List<Article> articles, Category category);
    void displayNoNews();
    void prepareNavigationMenu(List<Category> categories);
}
