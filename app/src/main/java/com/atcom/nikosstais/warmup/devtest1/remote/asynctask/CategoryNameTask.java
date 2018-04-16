package com.atcom.nikosstais.warmup.devtest1.remote.asynctask;

import android.os.AsyncTask;

import com.atcom.nikosstais.warmup.devtest1.remote.helpers.ContentHelper;

@Deprecated
class CategoryNameTask extends AsyncTask<Void, Void, String> {

    private Integer categoryId;

    public CategoryNameTask(int categoryId){
        this.categoryId = categoryId;
    }

    @Override
    protected String doInBackground(Void... params) {
        return ContentHelper.getInstance().getCategoryNameById(categoryId);
    }
}
