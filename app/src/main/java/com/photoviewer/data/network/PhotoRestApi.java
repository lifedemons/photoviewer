package com.photoviewer.data.network;

import com.photoviewer.data.entity.PhotoEntity;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface PhotoRestApi {

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link PhotoEntity}.
     */
    @GET("/photos")
    public Observable<List<PhotoEntity>> photoEntityList();
}
