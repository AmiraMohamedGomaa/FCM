package com.example.notificationapp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){
        if(retrofit==null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
             retrofit = new Retrofit.Builder()
                    .baseUrl( "https://fcm.googleapis.com/" )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .client(httpClient.build()).build();
        }
        return retrofit;
    }
}
