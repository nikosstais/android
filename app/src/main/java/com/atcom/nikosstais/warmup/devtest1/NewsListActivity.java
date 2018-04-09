package com.atcom.nikosstais.warmup.devtest1;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atcom.nikosstais.warmup.devtest1.remote.managers.ContentManager;


/**
 * Created by nikos on 09/04/18.
 */

public class NewsListActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO remove StrictMode-investigate
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setTheme(R.style.AppTheme);//replaces splash-theme

        setContentView(R.layout.activity_news_list);

        recyclerView = findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RefreshNews();
    }

    @Override
    protected void onResume(){
        super.onResume();
        RefreshNews();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    private void RefreshNews(){
        mAdapter = new NewsListAdapter(ContentManager.getNewsArticles());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
