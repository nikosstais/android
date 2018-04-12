package com.atcom.nikosstais.warmup.devtest1.system;

import android.app.Application;

import com.atcom.nikosstais.warmup.devtest1.system.network.ConnectivityReceiver;

/**
 * Created by nikos on 12/04/18.
 */

public class AndroidTestApplication extends Application {
    private static AndroidTestApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized AndroidTestApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
