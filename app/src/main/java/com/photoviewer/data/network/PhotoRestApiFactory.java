package com.photoviewer.data.network;

import com.photoviewer.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoRestApiFactory {

  private static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

  public static PhotoRestApi create() {
    OkHttpClient client = buildOkHttpClient();
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .baseUrl(API_BASE_URL)
        .client(client)
        .build()
        .create(PhotoRestApi.class);
  }

  private static OkHttpClient buildOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor interceptor =
          new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(interceptor);
    }
    return builder.build();
  }
}
