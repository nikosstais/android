package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.data.helpers.ContentHelper;


public class CategoryNameTask extends AsyncTask<Context, Void, String> {

    private Integer categoryId;

    public CategoryNameTask(int categoryId){
        this.categoryId = categoryId;
    }

    @Override
    protected String doInBackground(Context... params) {
        return ContentHelper.getCategoryNameById(params[0], categoryId);
    }
}
