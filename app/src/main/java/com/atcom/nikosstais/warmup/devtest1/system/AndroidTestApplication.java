package com.atcom.nikosstais.warmup.devtest1.system;

import android.support.multidex.MultiDexApplication;

import com.atcom.nikosstais.warmup.devtest1.database.AppDatabase;
import com.atcom.nikosstais.warmup.devtest1.system.network.ConnectivityReceiver;

/**
 * Created by nikos on 12/04/18.
 */

public class AndroidTestApplication extends MultiDexApplication {
    private static AndroidTestApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        AppDatabase.getDatabase();
    }

    public static synchronized AndroidTestApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppDatabase.destroyInstance();
    }
}
