package com.example.alvaf1.changes2018;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingletone  {
    private static Context context;
    private static VolleySingletone mInstance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingletone(Context context){

        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    public static VolleySingletone getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingletone(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){ return this.requestQueue; }

    public ImageLoader getImageLoader(){
        return this.imageLoader;
    }
}
