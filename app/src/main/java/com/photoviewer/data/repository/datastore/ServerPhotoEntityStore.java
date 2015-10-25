package com.photoviewer.data.repository.datastore;

import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.network.PhotoRestApiFactory;

import java.util.List;

import rx.Observable;

public class ServerPhotoEntityStore {
    public Observable<List<PhotoEntity>> photoEntityList() {
        return PhotoRestApiFactory.create().photoEntityList();
    }
}
