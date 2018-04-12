package com.atcom.nikosstais.warmup.devtest1.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atcom.nikosstais.warmup.devtest1.R;
import com.atcom.nikosstais.warmup.devtest1.activity.articles.ArticleListActivity;
import com.atcom.nikosstais.warmup.devtest1.database.AppDatabase;
import com.atcom.nikosstais.warmup.devtest1.presenters.MainPresenter;
import com.atcom.nikosstais.warmup.devtest1.remote.asynctask.FetchNewsTask;
import com.atcom.nikosstais.warmup.devtest1.system.network.ConnectivityReceiver;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
           if (!getIntent().getExtras().isEmpty()){
               TextView splashText = findViewById(R.id.splashText);
               splashText.
                       setText("Application will now exit because of no internet connection");
               sleep(3000);
               finish();
               return;
           }
        } catch (Exception e) {
            finish();
        }

        new Thread() {
            public void run() {
                try {
                    final Context ctx = getApplicationContext();
                    boolean notStoredDataFound = new FetchNewsTask().execute(ctx).get().isEmpty();

                    if (notStoredDataFound) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("NoData",0);
                        startActivity(i);
                        return;
                    }

                    sleep(1000);
                    Intent i = new Intent(MainActivity.this, ArticleListActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }.start();

    }

    @Override
    public void onResume(){
        super.onResume();


    }
}
