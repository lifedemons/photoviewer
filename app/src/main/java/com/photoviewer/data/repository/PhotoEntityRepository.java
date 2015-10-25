package com.photoviewer.data.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.repository.datastore.DatabasePhotoEntityStore;
import com.photoviewer.data.repository.datastore.ServerPhotoEntityStore;
import com.photoviewer.domain.repository.PhotoRepository;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

@Singleton
public class PhotoEntityRepository implements PhotoRepository {

    private DatabasePhotoEntityStore mDatabasePhotoEntityStore;
    private ServerPhotoEntityStore mServerPhotoEntityStore;

    @Inject
    public PhotoEntityRepository(DatabasePhotoEntityStore databasePhotoEntityStore, ServerPhotoEntityStore serverPhotoEntityStore) {
        mDatabasePhotoEntityStore = databasePhotoEntityStore;
        mServerPhotoEntityStore = serverPhotoEntityStore;
    }

    @Override
    public Observable<List<PhotoEntity>> photos() {
        return Observable.create(new Observable.OnSubscribe<List<PhotoEntity>>() {
            @Override
            public void call(Subscriber<? super List<PhotoEntity>> subscriber) {
                queryDatabaseForAll(subscriber);
            }
        });
    }

    @Override
    public Observable<List<PhotoEntity>> searchPhotosByTitle(String title) {
        return mDatabasePhotoEntityStore.queryForTitle(title);
    }

    @Override
    public Observable<PhotoEntity> photo(int photoId) {
        return mDatabasePhotoEntityStore.queryForId(photoId);
    }

    private void queryDatabaseForAll(final Subscriber<? super List<PhotoEntity>> subscriber) {
        mDatabasePhotoEntityStore.queryForAll().subscribe(new Observer<List<PhotoEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
                fetchServerForAll(subscriber);
            }

            @Override
            public void onNext(List<PhotoEntity> photoEntities) {
                if (photoEntities.size() != 0) {
                    subscriber.onNext(photoEntities);
                    subscriber.onCompleted();
                } else {
                    fetchServerForAll(subscriber);
                }
            }
        });
    }

    private void fetchServerForAll(Subscriber<? super List<PhotoEntity>> subscriber) {
        mServerPhotoEntityStore.photoEntityList().subscribe(new Observer<List<PhotoEntity>>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(List<PhotoEntity> photoEntities) {
                subscriber.onNext(photoEntities);
                mDatabasePhotoEntityStore.
                        saveAll(photoEntities).
                        subscribe();
            }
        });
    }
}
