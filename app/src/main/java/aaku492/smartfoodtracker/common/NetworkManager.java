package aaku492.smartfoodtracker.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aaku492.smartfoodtracker.R;
import okhttp3.OkHttpClient;
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

        // This doesn't work. See: https://github.com/SmartFoodTracker/SmartFoodTrackerAndroid/issues/2
//        Interceptor rewriteCacheControlInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response originalResponse = chain.proceed(request);
//
//                if (!request.method().equals("GET")) {
//                    // Only attempt caching on GET requests
//                    return originalResponse;
//                }
//
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", isOnline(context) ? "public, max-age=" + maxAgeOnline :
//                                                                     "public, only-if-cached, max-stale=" + maxStaleOffline
//                        )
//                        .build();
//            }
//        };

//        File httpCacheDirectory = new File(context.getCacheDir(), cacheDir);
//        Cache cache = new Cache(httpCacheDirectory, cacheSizeBytes);

        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(rewriteCacheControlInterceptor)
//                .cache(cache)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat(context.getString(R.string.date_format_backend_api))
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
        return netInfo != null && netInfo.isConnected();
    }
}
