package com.photoviewer.data.repository.datastore;

import com.photoviewer.data.entity.PhotoEntity;

import com.photoviewer.data.network.PhotoRestService;
import java.util.List;

import javax.inject.Inject;
import rx.Observable;

public class ServerPhotoEntityStore {

    private final PhotoRestService mService;

    @Inject
    public ServerPhotoEntityStore(PhotoRestService service) {
        mService = service;
    }

    public Observable<List<PhotoEntity>> photoEntityList() {
        return mService.photoEntityList();
    }
}
