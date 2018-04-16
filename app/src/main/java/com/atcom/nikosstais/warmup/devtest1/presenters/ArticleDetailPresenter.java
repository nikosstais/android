package com.atcom.nikosstais.warmup.devtest1.presenters;

import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleDetailActivityView;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;

/**
 * Created by nikos on 16/04/18.
 */

public class ArticleDetailPresenter {
    private ArticleDetailActivityView mView;

    public ArticleDetailPresenter(ArticleDetailActivityView view) {
        this.mView = view;
    }

    public void showDetailedArticle(Article article){
        mView.displayArticle(article);
    }
}
