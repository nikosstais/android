package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.data.helpers.ContentHelper;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Category;

import java.util.Collections;
import java.util.List;

@Deprecated
class FetchCategoriesTask extends AsyncTask<Void, Void, List<Category>> {

    @Override
    protected List<Category> doInBackground(Void... contexts) {
        //return ContentHelper.getInstance().getFilteredCategories();
        return Collections.emptyList();
    }
}
