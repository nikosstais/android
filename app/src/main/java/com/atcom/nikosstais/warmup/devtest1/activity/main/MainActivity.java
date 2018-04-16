package com.atcom.nikosstais.warmup.devtest1.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.impl.ArticleListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Intent i = new Intent(MainActivity.this, ArticleListActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        }.start();

    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
