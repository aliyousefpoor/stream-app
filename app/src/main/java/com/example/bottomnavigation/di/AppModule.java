package com.example.bottomnavigation.di;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.bottomnavigation.utils.AppConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AppModule {
    private static final String TAG = "AppModule";

    private Retrofit retrofit = null;

    public Retrofit provideRetrofit() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.addInterceptor(new TokenInterceptor());

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(AppConstants.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        }
        return retrofit;
    }
}
