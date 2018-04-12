package com.atcom.nikosstais.warmup.devtest1.remote.data.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nikos on 06/04/18.
 */

public class Category implements Serializable, Comparable {

    @SerializedName("order")
    private int order;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("icon_android")
    private String iconUrl;

    @SerializedName("date_modified")
    private Date dateModified;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if (this.id == ((Category) o).id)
            return 0;
        else if (this.id > ((Category) o).id) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Category) obj).id;
    }
}
