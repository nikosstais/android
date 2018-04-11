package com.atcom.nikosstais.warmup.devtest1.remote.managers;

import android.content.Context;
import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.database.AppDatabase;
import com.atcom.nikosstais.warmup.devtest1.database.articles.ArticleResponses;
import com.atcom.nikosstais.warmup.devtest1.database.categories.CategoryResponses;
import com.atcom.nikosstais.warmup.devtest1.remote.apis.ProtoThemaApiInterface;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.CategoriesResponse;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.NewsArticlesResponse;
import com.atcom.nikosstais.warmup.devtest1.remote.tools.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikos on 06/04/18.
 */

public class ContentManager{

    private static final String TAG = ContentManager.class.toString();
    private static final String articleListUrl = "http://mobileapps.atcom.gr/services/protoThema/articlelist_gson.json";
    private static final String categoryListUrl = "http://mobileapps.atcom.gr/services/protoThema/categories_gson.json";

    public static List<Category> getCategories(Context ctx) {

        List<Category> allCategories = new ArrayList<>();
        if (NetworkUtil.isNetworkAvailable(ctx)){
            Call<CategoriesResponse> categoriesCall = getProtoThemaService().getCategories();
            try {
                CategoriesResponse mainResponse = categoriesCall.execute().body();

                if (mainResponse!=null){
                    addCategoriesToDB(mainResponse,ctx);
                    allCategories = mainResponse.getCategories();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            allCategories = getCategoriesFromDB(ctx);
        }

        List<Category> filteredCategories = filterOutEmptyCategories(allCategories, ctx);
        Arrays.sort(filteredCategories.toArray());

        return filteredCategories;
    }

    public static List<Article> getNewsArticles(Context ctx){

        List<Article> allArticles = new ArrayList<>();

        if (NetworkUtil.isNetworkAvailable(ctx)){
            Call<NewsArticlesResponse> articlesCall = getProtoThemaService().getArticles();
            try {
                NewsArticlesResponse mainResponse = articlesCall.execute().body();

                if (mainResponse!=null){
                    addArticleToDB(mainResponse,ctx);
                    allArticles = mainResponse.getArticles();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            allArticles = getArticlesFromDB(ctx);
        }

        Arrays.sort(allArticles.toArray());

        return allArticles;

    }

    private static List<Article> getArticlesFromDB(Context ctx){
        ArticleResponses articleResponses = AppDatabase.getDatabase(ctx)
                .articleResponsesDao()
                .getAllArticleResponses().get(0);

        NewsArticlesResponse savedResponse = getGsonBuilder()
                .fromJson(articleResponses.responseText, NewsArticlesResponse.class);

        return savedResponse.getArticles();
    }

    private static List<Category> getCategoriesFromDB(Context ctx){
        CategoryResponses response = AppDatabase.getDatabase(ctx)
                .categoryResponsesDao()
                .getAllCategoryResponses().get(0);

        CategoriesResponse savedResponse = getGsonBuilder()
                .fromJson(response.responseText, CategoriesResponse.class);

        return savedResponse.getCategories();
    }

    private static void addArticleToDB(NewsArticlesResponse newsArticlesResponse, Context ctx){
        ArticleResponses responseToSave = new ArticleResponses();
        responseToSave.responseText = getGsonBuilder().toJson(newsArticlesResponse, NewsArticlesResponse.class);
        responseToSave.dateInserted = Calendar.getInstance().getTime().toString();

        AppDatabase.getDatabase(ctx).articleResponsesDao().addArticleResponses(responseToSave);
    }
    private static void addCategoriesToDB(CategoriesResponse categoriesResponse, Context ctx){
        CategoryResponses responseToSave = new CategoryResponses();
        responseToSave.responseText = getGsonBuilder().toJson(categoriesResponse, CategoriesResponse.class);
        responseToSave.dateInserted = Calendar.getInstance().getTime().toString();

        AppDatabase.getDatabase(ctx).categoryResponsesDao().addCategoryResponses(responseToSave);
    }
    private static Gson getGsonBuilder(){
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

    private static List<Category> filterOutEmptyCategories(List<Category> allCategories, Context ctx){
        List<Category> filteredCategories = new ArrayList<>();
        HashSet<Integer> existingCategories = new HashSet<>();
        List<Article> articles = getNewsArticles(ctx);

        for (Article article : articles){
            for (Category cat : article.getCategoryList()){
                existingCategories.add(cat.getId());
            }
        }

        for (Category cat : allCategories){
            if (existingCategories.contains(cat.getId())){
                filteredCategories.add(cat);
            }
        }

        return filteredCategories;
    }
    public static class FetchNewsTask extends AsyncTask<Context,Void,List<Article>>{

        @Override
        protected List<Article> doInBackground(Context... contexts) {
            return getNewsArticles(contexts[0]);
        }


    }

    public static class FetchCategoriesTask extends AsyncTask<Context, Void, List<Category>>{

        @Override
        protected List<Category> doInBackground(Context... contexts) {
            return getCategories(contexts[0]);
        }

    }
}
