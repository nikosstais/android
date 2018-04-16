package com.atcom.nikosstais.warmup.devtest1.remote.helpers;

import android.content.Context;
import android.view.View;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.database.AppDatabase;
import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleCache;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoriesCache;
import com.atcom.nikosstais.warmup.devtest1.remote.apis.ProtoThemaApiInterface;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.CategoriesResponse;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.NewsArticlesResponse;
import com.atcom.nikosstais.warmup.devtest1.system.AndroidTestApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikos on 06/04/18.
 */

public class ContentHelper {

    private static final String TAG = ContentHelper.class.toString();
    private static final ContentHelper INSTANCE = new ContentHelper();

    private ContentHelper(){}

    public static ContentHelper getInstance(){

        return INSTANCE;
    }

    public Single<List<Category>> getCategories() {

        return
                Single.fromCallable(
                    new Callable<List<Category>>() {
                       @Override
                       public List<Category> call() throws Exception {
                           List<Category> allCategories = new ArrayList<>();

                           Call<CategoriesResponse> categoriesCall = getProtoThemaService().getCategories();
                           try {
                               CategoriesResponse mainResponse = categoriesCall.execute().body();

                               if (mainResponse != null) {
                                   addCategoriesToDB(mainResponse);
                                   allCategories = mainResponse.getCategories();
                               }

                           } catch (Exception e) {
                               e.printStackTrace();
                               allCategories = getCategoriesFromDB();
                           }

                           return allCategories;
                       }
                   } );

    }

    public Single<List<Category>> getFilteredCategories() {

        return
        Single.fromCallable(new Callable<List<Category>>() {

            @Override
            public List<Category> call() throws Exception {
                List<Category> allCategories = new ArrayList<>();

                Call<CategoriesResponse> categoriesCall = getProtoThemaService().getCategories();
                try {
                    CategoriesResponse mainResponse = categoriesCall.execute().body();

                    if (mainResponse != null) {
                        addCategoriesToDB(mainResponse);
                        allCategories = mainResponse.getCategories();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    allCategories = getCategoriesFromDB();
                }

                if (allCategories.isEmpty()){
                    return allCategories;
                }

                Collections.sort(allCategories);

                Category homeCategory = allCategories.get(0);

                List<Category> filteredCategories = filterOutEmptyCategories(allCategories);

                filteredCategories.add(homeCategory);

                Collections.sort(filteredCategories);

                return filteredCategories;
            }
        });
    }

    public Single<List<Article>> getNews() {
        return Single.fromCallable(new Callable<List<Article>>() {
            @Override
            public List<Article> call() throws Exception {
                return getNewsArticles();
            }
        });
    }
    public List<Article> getNewsArticles() {

        List<Article> allArticles = new ArrayList<>();

        Call<NewsArticlesResponse> articlesCall = getProtoThemaService().getArticles();
        try {

            NewsArticlesResponse mainResponse = articlesCall.execute().body();

            if (mainResponse != null) {
                addArticleToDB(mainResponse);
                allArticles = mainResponse.getArticles();
            }

        } catch (Exception e) {
            e.printStackTrace();
            allArticles = getArticlesFromDB();
        }

        Collections.sort(allArticles);

        return allArticles;

    }

    private List<Article> getNewsArticlesByCategory(int categoryId, List<Article> articleList) {
        if (categoryId == -1){
            return articleList;
        }
        List<Article> categoryArticles = new ArrayList<>();
        Category searchCategory = new Category();
        searchCategory.setId(categoryId);

        for (Article article : articleList) {
            if (article.getCategoryList().contains(searchCategory)) {
                categoryArticles.add(article);
            }
        }

        return categoryArticles;
    }

    private List<Article> getArticlesFromDB() {
        List<ArticleCache> articleCacheList = AppDatabase.getDatabase()
                .articlesCacheDao()
                .getLatestArticlesFromCache();

        if (articleCacheList.isEmpty()){
            return new ArrayList<>();
        }

        ArticleCache articleCache = articleCacheList.get(0);

        NewsArticlesResponse savedResponse = getGsonBuilder()
                .fromJson(articleCache.responseText, NewsArticlesResponse.class);

        return savedResponse.getArticles();
    }

    private List<Category> getCategoriesFromDB() {
        CategoriesCache response = AppDatabase.getDatabase()
                .categoriesCacheDao()
                .getLatestCategoriesFromCache().get(0);

        CategoriesResponse savedResponse = getGsonBuilder()
                .fromJson(response.responseText, CategoriesResponse.class);

        return savedResponse.getCategories();
    }
    public String getCategoryNameById(int categoryId) {
        CategoriesCache response = AppDatabase.getDatabase()
                .categoriesCacheDao()
                .getLatestCategoriesFromCache().get(0);

        CategoriesResponse savedResponse = getGsonBuilder()
                .fromJson(response.responseText, CategoriesResponse.class);

        Category criterion = new Category();
        criterion.setId(categoryId);

        int i = savedResponse.getCategories().indexOf(criterion);
        if (i>-1){
            return savedResponse.getCategories().get(i).getName();
        }
        return AndroidTestApplication.getInstance().getString(R.string.app_name);
    }

    private void addArticleToDB(NewsArticlesResponse newsArticlesResponse) {
        ArticleCache responseToSave = new ArticleCache();
        responseToSave.responseText = getGsonBuilder().toJson(newsArticlesResponse, NewsArticlesResponse.class);
        responseToSave.dateInserted = Calendar.getInstance().getTime().toString();

        AppDatabase.getDatabase().articlesCacheDao().cleanArticlesCache();
        AppDatabase.getDatabase().articlesCacheDao().addArticlesToCache(responseToSave);
    }

    private void addCategoriesToDB(CategoriesResponse categoriesResponse) {
        CategoriesCache responseToSave = new CategoriesCache();
        responseToSave.responseText = getGsonBuilder().toJson(categoriesResponse, CategoriesResponse.class);
        responseToSave.dateInserted = Calendar.getInstance().getTime().toString();

        AppDatabase.getDatabase().categoriesCacheDao().cleanCategoriesCache();
        AppDatabase.getDatabase().categoriesCacheDao().addCategoriesToCache(responseToSave);
    }

    private Gson getGsonBuilder() {
        return new GsonBuilder()
                .setDateFormat("MMM dd yyyy")
                .create();
    }

    private ProtoThemaApiInterface getProtoThemaService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProtoThemaApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGsonBuilder()))
                .build();

        return retrofit.create(ProtoThemaApiInterface.class);
    }

    private List<Category> filterOutEmptyCategories(List<Category> allCategories) {
        List<Category> filteredCategories = new ArrayList<>();
        HashSet<Integer> existingCategories = new HashSet<>();
        List<Article> articles = getNewsArticles();

        for (Article article : articles) {
            for (Category cat : article.getCategoryList()) {
                existingCategories.add(cat.getId());
            }
        }

        for (Category cat : allCategories) {
            if (existingCategories.contains(cat.getId())) {
                filteredCategories.add(cat);
            }
        }

        return filteredCategories;
    }
}
