package com.photoviewer.presentation.di.modules;

import android.content.Context;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.photoviewer.BuildConfig;
import com.photoviewer.data.network.PhotoRestService;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(library = true, complete = false) public class ApiModule {

  private static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

  @Provides @Singleton PhotoRestService provideOrderRatingService(OkHttpClient httpClient) {
    return create(httpClient);
  }

  public static PhotoRestService create(OkHttpClient client) {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .baseUrl(API_BASE_URL)
        .client(client)
        .build()
        .create(PhotoRestService.class);
  }

  @Provides @Singleton OkHttpClient provideHttpClient() {
    return createOkHttpClient();
  }

  private static OkHttpClient createOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor interceptor =
          new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(interceptor);
    }
    return builder.build();
  }

  @Provides @Singleton Picasso providePicasso(OkHttpClient httpClient, Context context) {

    Picasso.Builder builder = new Picasso.Builder(context);
    Picasso picasso = builder.downloader(new OkHttp3Downloader(httpClient)).build();

    return picasso;
  }
}
