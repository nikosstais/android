package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.data.helpers.ContentHelper;
import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;

import java.util.List;

@Deprecated
class FetchNewsTask extends AsyncTask<Void, Void, List<Article>> {

    @Override
    protected List<Article> doInBackground(Void... contexts) {
        return ContentHelper.getInstance().getNewsArticles();
    }
}
