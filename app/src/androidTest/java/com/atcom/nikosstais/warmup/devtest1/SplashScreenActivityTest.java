package com.atcom.nikosstais.warmup.devtest1;

import android.os.Bundle;

import com.atcom.nikosstais.warmup.devtest1.presenters.MainPresenter;

import org.junit.Test;

/**
 * Created by nikos on 06/04/18.
 */
public class SplashScreenActivityTest {

    @Test
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);
    }

    @Test
    protected void onResume() {
        //super.onResume();
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.fetchAllArticles();
    }

    @Test
    protected void onStop() {

        //super.onStop();
    }

}