package com.atcom.nikosstais.warmup.devtest1.remote.apis;

import com.atcom.nikosstais.warmup.devtest1.remote.data.models.CategoriesResponse;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.NewsArticlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nikos on 07/04/18.
 */

public interface ProtoThemaApiInterface {

    String BASE_URL = "http://mobileapps.atcom.gr/services/protoThema/";

    @GET("articlelist_gson.json")
    Call<NewsArticlesResponse> getArticles();

    @GET("categories_gson.json")
    Call<CategoriesResponse> getCategories();

}
