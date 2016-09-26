package com.study.go.burlaka.showchannelsapp.retrofit;

/**
 * Created by Kostya on 14.09.2016.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Artur Vasilov
 */
public class ApiFactory {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;
    private static OkHttpClient sClient;
    private static final String TAG = "GetChannel";

    @NonNull
    public static ChannelService getChannelService() {

        return buildRetrofit().create(ChannelService.class);
    }


    @NonNull
    public static ProgramService getProgramService() {

        return buildRetrofit().create(ProgramService.class);
    }

    @NonNull
    public static CategoryService getCategoryService() {

        return buildRetrofit().create(CategoryService.class);
    }


    @NonNull
    private static Retrofit buildRetrofit() {
        Log.i(TAG, "onBuildRetrofitApiFactory");
        return new Retrofit.Builder()
                .baseUrl("https://t2dev.firebaseio.com/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
    }

}