package com.atcom.nikosstais.warmup.devtest1.remote.data;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by nikos on 06/04/18.
 */

public class Article implements Serializable, Comparable<Article> {
    //@SerializedName("id")
    private BigInteger id;

    //@SerializedName("title")
    private String title;

    //@SerializedName("summary")
    private String summary;

    //@SerializedName("content")
    private String content;

    @SerializedName("photo")
    private String photoUrl;

    @SerializedName("categories")
    private List<Category> categoryList;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int compareTo(@NonNull Article o) {
        return this.categoryList.get(0).getId();
    }
}
