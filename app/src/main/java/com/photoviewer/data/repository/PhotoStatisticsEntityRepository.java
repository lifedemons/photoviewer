package com.photoviewer.data.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.data.repository.datastore.PreferencesPhotoStatisticsEntityStore;
import com.photoviewer.domain.repository.PhotoStatisticsRepository;

import rx.Observable;

@Singleton
public class PhotoStatisticsEntityRepository implements PhotoStatisticsRepository {

    @Inject
    private PreferencesPhotoStatisticsEntityStore mStatisticsEntityStore;

    @Override
    public Observable<PhotoStatisticsEntity> readStatistics() {
        return mStatisticsEntityStore.readStatistics();
    }

    @Override
    public Observable<PhotoStatisticsEntity> updateStatistics(PhotoStatisticsEntity photoStatisticsEntity) {
        return mStatisticsEntityStore.updateStatistics(photoStatisticsEntity);
    }
}
