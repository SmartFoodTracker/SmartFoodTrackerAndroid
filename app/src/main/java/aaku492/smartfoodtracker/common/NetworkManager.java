package aaku492.smartfoodtracker.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-29.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class NetworkManager<T> {
    private final T dataProvider;

    public NetworkManager(final Context context,
                          int cacheSizeBytes,
                          final int maxAgeOnline,
                          final int maxStaleOffline,
                          String cacheDir,
                          String baseUrl,
                          Class<T> dataProviderClass) {
        // Source: http://stackoverflow.com/questions/23429046/can-retrofit-with-okhttp-use-cache-data-when-offline
        // This looks like magic to me; not sure how this works. Make sure the @Headers in DataProvider are correct
        // for this caching to work.
        Interceptor rewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                if (isOnline(context)) {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAgeOnline)
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStaleOffline)
                            .build();
                }
            }
        };

        File httpCacheDirectory = new File(context.getCacheDir(), cacheDir);
        Cache cache = new Cache(httpCacheDirectory, cacheSizeBytes);

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dataProvider = retrofit.create(dataProviderClass);
    }

    public T getDataProvider() {
        return dataProvider;
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
