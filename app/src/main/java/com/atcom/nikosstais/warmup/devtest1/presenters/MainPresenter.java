package com.atcom.nikosstais.warmup.devtest1.presenters;

import com.atcom.nikosstais.warmup.devtest1.remote.data.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.managers.ContentManager;

import java.util.List;


/**
 * Created by nikos on 06/04/18.
 */

public class MainPresenter {

    private static final ContentManager contentManager = new ContentManager();

    public List<Category> fetchCategoriesData(){
        return contentManager.getCategories();
    }

    public List<Article> fetchAllArticles(){
        return contentManager.getNewsArticles();
    }

}