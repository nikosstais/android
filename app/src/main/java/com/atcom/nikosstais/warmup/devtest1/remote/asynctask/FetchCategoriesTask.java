package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;
import com.atcom.nikosstais.warmup.devtest1.remote.helpers.ContentHelper;

import java.util.List;

@Deprecated
public class FetchCategoriesTask extends AsyncTask<Void, Void, List<Category>> {

    @Override
    protected List<Category> doInBackground(Void... contexts) {
        return ContentHelper.getInstance().getFilteredCategories();
    }
}
