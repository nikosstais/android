package com.atcom.nikosstais.warmup.devtest1.remote.helpers;

import android.content.Context;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.database.AppDatabase;
import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleCache;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoriesCache;
import com.atcom.nikosstais.warmup.devtest1.remote.apis.ProtoThemaApiInterface;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.CategoriesResponse;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.NewsArticlesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikos on 06/04/18.
 */

public class ContentHelper {

    private static final String TAG = ContentHelper.class.toString();

    public static List<Category> getCategories(Context ctx) {

        List<Category> allCategories = new ArrayList<>();

            Call<CategoriesResponse> categoriesCall = getProtoThemaService().getCategories();
            try {
                CategoriesResponse mainResponse = categoriesCall.execute().body();

                if (mainResponse != null) {
                    addCategoriesToDB(mainResponse, ctx);
                    allCategories = mainResponse.getCategories();
                }

            } catch (Exception e) {
                e.printStackTrace();
                allCategories = getCategoriesFromDB(ctx);
            }

        return allCategories;
    }

    public static List<Category> getFilteredCategories(Context ctx) {

        List<Category> allCategories = new ArrayList<>();

            Call<CategoriesResponse> categoriesCall = getProtoThemaService().getCategories();
            try {
                CategoriesResponse mainResponse = categoriesCall.execute().body();

                if (mainResponse != null) {
                    addCategoriesToDB(mainResponse, ctx);
                    allCategories = mainResponse.getCategories();
                }

            } catch (IOException e) {
                e.printStackTrace();
                allCategories = getCategoriesFromDB(ctx);
            }

        if (allCategories.isEmpty()){
            return allCategories;
        }

        Collections.sort(allCategories);

        Category homeCategory = allCategories.get(0);

        List<Category> filteredCategories = filterOutEmptyCategories(allCategories, ctx);

        filteredCategories.add(homeCategory);

        Collections.sort(filteredCategories);

        return filteredCategories;
    }

    public static List<Article> getNewsArticles(Context ctx) {

        List<Article> allArticles = new ArrayList<>();

        Call<NewsArticlesResponse> articlesCall = getProtoThemaService().getArticles();
        try {

            NewsArticlesResponse mainResponse = articlesCall.execute().body();

            if (mainResponse != null) {
                addArticleToDB(mainResponse, ctx);
                allArticles = mainResponse.getArticles();
            }

        } catch (Exception e) {
            e.printStackTrace();
            allArticles = getArticlesFromDB(ctx);
        }

        Collections.sort(allArticles);

        return allArticles;

    }

    public static List<Article> getNewsArticlesByCategory(int categoryId, List<Article> articleList) {
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

    private static List<Article> getArticlesFromDB(Context ctx) {
        List<ArticleCache> articleCacheList = AppDatabase.getDatabase(ctx)
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

    private static List<Category> getCategoriesFromDB(Context ctx) {
        CategoriesCache response = AppDatabase.getDatabase(ctx)
                .categoriesCacheDao()
                .getLatestCategoriesFromCache().get(0);

        CategoriesResponse savedResponse = getGsonBuilder()
                .fromJson(response.responseText, CategoriesResponse.class);

        return savedResponse.getCategories();
    }
    public static String getCategoryNameById(Context ctx, int categoryId) {
        CategoriesCache response = AppDatabase.getDatabase(ctx)
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
        return ctx.getApplicationContext().getResources().getString(R.string.app_name);
    }

    private static void addArticleToDB(NewsArticlesResponse newsArticlesResponse, Context ctx) {
        ArticleCache responseToSave = new ArticleCache();
        responseToSave.responseText = getGsonBuilder().toJson(newsArticlesResponse, NewsArticlesResponse.class);
        responseToSave.dateInserted = Calendar.getInstance().getTime().toString();

        AppDatabase.getDatabase(ctx).articlesCacheDao().cleanArticlesCache();
        AppDatabase.getDatabase(ctx).articlesCacheDao().addArticlesToCache(responseToSave);
    }

    private static void addCategoriesToDB(CategoriesResponse categoriesResponse, Context ctx) {
        CategoriesCache responseToSave = new CategoriesCache();
        responseToSave.responseText = getGsonBuilder().toJson(categoriesResponse, CategoriesResponse.class);
        responseToSave.dateInserted = Calendar.getInstance().getTime().toString();

        AppDatabase.getDatabase(ctx).categoriesCacheDao().cleanCategoriesCache();
        AppDatabase.getDatabase(ctx).categoriesCacheDao().addCategoriesToCache(responseToSave);
    }

    private static Gson getGsonBuilder() {
        return new GsonBuilder()
                .setDateFormat("MMM dd yyyy")
                .create();
    }

    private static ProtoThemaApiInterface getProtoThemaService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProtoThemaApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGsonBuilder()))
                .build();

        return retrofit.create(ProtoThemaApiInterface.class);
    }

    private static List<Category> filterOutEmptyCategories(List<Category> allCategories, Context ctx) {
        List<Category> filteredCategories = new ArrayList<>();
        HashSet<Integer> existingCategories = new HashSet<>();
        List<Article> articles = getNewsArticles(ctx);

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
