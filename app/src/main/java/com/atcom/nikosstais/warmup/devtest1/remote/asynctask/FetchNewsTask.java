package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.data.models.Article;
import com.atcom.nikosstais.warmup.devtest1.remote.helpers.ContentHelper;

import java.util.List;


public class FetchNewsTask extends AsyncTask<Context, Void, List<Article>> {

    @Override
    protected List<Article> doInBackground(Context... contexts) {
        return ContentHelper.getNewsArticles(contexts[0]);
    }
}
