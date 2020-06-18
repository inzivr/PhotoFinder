package com.projects.photofinder.pexelsapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PexelsApiCall {

    // Referred from site :- https://www.journaldev.com/20433/android-rxjava-retrofit
    //                       https://stackoverflow.com/questions/39330983/get-json-object-one-by-one-from-json-array-in-android-using-retrofit-and-rxjava/40187125

    public static final String BASE_URL = "https://api.pexels.com/v1/";

    public static Retrofit.Builder builder;
    public static Retrofit retrofit = null;

    public static Retrofit getApiCall()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null)
        {
            // Setting up RetroFit
            builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson));

            retrofit = builder.build();
        }
        return retrofit;
    }

}
