package com.atcom.nikosstais.warmup.devtest1.presenters;

import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleListActivityView;
import com.atcom.nikosstais.warmup.devtest1.remote.data.helpers.ContentHelper;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by nikos on 06/04/18.
 */

public class ArticleListPresenter {

    private ArticleListActivityView mView;
    private Scheduler mMainScheduler;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ArticleListPresenter(ArticleListActivityView view, Scheduler mainScheduler){
        mView = view;
        mMainScheduler = mainScheduler;
    }

    public void loadNews(){
        mCompositeDisposable
                .add(ContentHelper.getInstance().getNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(mMainScheduler)
                        .subscribeWith(new DisposableSingleObserver<List<Article>>() {
                            @Override
                            public void onSuccess(List<Article> articles) {
                                if (articles.isEmpty()) {
                                    mView.displayNoNews();
                                } else {
                                    mView.displayNews(articles, null);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mView.displayNoNews();
                            }
                        }));

    }

    public void loadCategories(){
        mCompositeDisposable.add(ContentHelper.getInstance().getFilteredCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Category>>() {
                    @Override
                    public void onSuccess(List<Category> categories) {
                        mView.prepareNavigationMenu(categories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.displayNoNews();

                    }
                })

        );
    }

    public void loadCategoryNews(@NonNull final Category category) {
        mCompositeDisposable
                .add(ContentHelper.getInstance().getNews()
                        .map(new Function<List<Article>, List<Article>>() {
                            @Override
                            public List<Article> apply(List<Article> articles) throws Exception {
                                if (category.getId() == -1){
                                    return articles;
                                }
                                List<Article> filteredList = new ArrayList<>();
                                for (Article article: articles) {
                                    if (article.getCategoryList().contains(category)){
                                         filteredList.add(article);
                                    }
                                }
                                return filteredList;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(mMainScheduler)
                        .subscribeWith(new DisposableSingleObserver<List<Article>>() {
                            @Override
                            public void onSuccess(List<Article> articles) {
                                if (articles.isEmpty()) {
                                    mView.displayNoNews();
                                } else {
                                    mView.displayNews(articles, category);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mView.displayNoNews();
                            }
                        }));

    }

    public void unsubscribe(){
        mCompositeDisposable.clear();
    }
}
