package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.data.helpers.ContentHelper;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;

import java.util.List;

public class FetchCategoriesTask extends AsyncTask<Context, Void, List<Category>> {

    @Override
    protected List<Category> doInBackground(Context... contexts) {
        return ContentHelper.getFilteredCategories(contexts[0]);
    }
}
