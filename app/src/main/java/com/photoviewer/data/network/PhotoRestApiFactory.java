package com.photoviewer.data.network;

import retrofit.RestAdapter;

public class PhotoRestApiFactory {

    private static String API_BASE_URL = "http://jsonplaceholder.typicode.com";

    public static PhotoRestApi create() {
        RestAdapter.Builder builder= new RestAdapter.Builder()
                        .setRequestInterceptor(request -> {
                            request.addHeader("Accept", "application/json");
                        });

        RestAdapter restAdapter = builder
                        .setEndpoint(API_BASE_URL)
                        .build();

        return restAdapter.create(PhotoRestApi.class);
    }

}
