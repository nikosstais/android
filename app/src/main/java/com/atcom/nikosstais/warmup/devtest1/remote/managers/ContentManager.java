package com.atcom.nikosstais.warmup.devtest1.remote.managers;

import com.atcom.nikosstais.warmup.devtest1.remote.apis.ProtoThemaApiInterface;
import com.atcom.nikosstais.warmup.devtest1.remote.data.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.CategoriesResponse;
import com.atcom.nikosstais.warmup.devtest1.remote.data.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.data.NewsArticlesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikos on 06/04/18.
 */

public class ContentManager {

    private final static String TAG = ContentManager.class.toString();
    private static final String articleListUrl = "http://mobileapps.atcom.gr/services/protoThema/articlelist_gson.json";
    private static final String categoryListUrl = "http://mobileapps.atcom.gr/services/protoThema/categories_gson.json";

    public static List<Category> getCategories() {

        Call<CategoriesResponse> categoriesCall = getProtoThemaService().getCategories();

        List<Category> allCategories = new ArrayList<>();
        try {
            allCategories = categoriesCall.execute().body().getCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
//      TODO make it asynchronous
//        articlesCallback.enqueue(new Callback<CategoriesResponse>() {
//            @Override
//            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
//                currentActivity.getIntent().putExtra("categoriesResponse", response.body());
//            }
//
//            @Override
//            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
//
//            }
//        });

        return allCategories;
    }

    public static List<Article> getNewsArticles(){

        Call<NewsArticlesResponse> articlesCall = getProtoThemaService().getArticles();

        List<Article> allArticles = new ArrayList<>();
        try {
            allArticles = articlesCall.execute().body().getArticles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Arrays.sort(allArticles.toArray());
//      TODO make it asynchronous
//        articlesCallback.enqueue(new Callback<NewsArticlesResponse>() {
//            public NewsArticlesResponse articles;
//            @Override
//            public void onResponse(Call<NewsArticlesResponse> call, Response<NewsArticlesResponse> response) {
//                ContentManager.this.notify();
//            }
//
//            @Override
//            public void onFailure(Call<NewsArticlesResponse> call, Throwable t) {
//
//            }
//        });

        return allArticles;

    }
    private static ProtoThemaApiInterface getProtoThemaService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("MMM dd yyyy")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProtoThemaApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ProtoThemaApiInterface.class);
    }

    public static List<Article> getNewsArticlesByCategory(int categoryId, List<Article> articleList){
        List<Article> categoryArticles = new ArrayList<>();
        Category searchCategory = new Category();
        searchCategory.setId(categoryId);

        for (Article article : articleList) {
            if (article.getCategoryList().contains(searchCategory)){
                categoryArticles.add(article);
            }
        }

        return categoryArticles;
    }
}
