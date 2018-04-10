package com.atcom.nikosstais.warmup.devtest1.remote.tools;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageLoader;
//import com.android.volley.toolbox.Volley;

/**
 * Created by nikos on 06/04/18.
 */

public class HttpConnectionPool {
//    private static HttpConnectionPool mInstance;
//    private RequestQueue mRequestQueue;
//    private ImageLoader mImageLoader;
//    private Context mCtx;
//
//    private HttpConnectionPool(Context context) {
//        mCtx = context;
//        mRequestQueue = getRequestQueue();
//
//        mImageLoader = new ImageLoader(mRequestQueue,
//                new ImageLoader.ImageCache() {
//                    private final LruCache<String, Bitmap>
//                            cache = new LruCache<String, Bitmap>(20);
//
//                    @Override
//                    public Bitmap getBitmap(String url) {
//                        return cache.get(url);
//                    }
//
//                    @Override
//                    public void putBitmap(String url, Bitmap bitmap) {
//                        cache.put(url, bitmap);
//                    }
//                });
//    }
//
//    public static synchronized HttpConnectionPool getInstance(Context context) {
//        if (mInstance == null) {
//            mInstance = new HttpConnectionPool(context);
//        }
//        return mInstance;
//    }
//
//    private RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            // getApplicationContext() is key, it keeps you from leaking the
//            // Activity or BroadcastReceiver if someone passes one in.
//            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
//        }
//        return mRequestQueue;
//    }
//
//    public <T> void addToRequestQueue(Request<T> req) {
//        getRequestQueue().add(req);
//    }
//
//    public ImageLoader getImageLoader() {
//        return mImageLoader;
//    }
}