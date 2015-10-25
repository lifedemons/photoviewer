package com.photoviewer.data.repository.datastore;

import android.content.Context;

import com.google.inject.Inject;
import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.data.preferences.orm.PreferencesDao;

import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Subscriber;

@Accessors(prefix = "m")
public class PreferencesPhotoStatisticsEntityStore {

    @Setter
    private PreferencesDao<PhotoStatisticsEntity> mStatisticsDao;

    @Inject
    public PreferencesPhotoStatisticsEntityStore(Context context) {
        setStatisticsDao(new PreferencesDao<>(PhotoStatisticsEntity.class, context));
    }

    public Observable<PhotoStatisticsEntity> readStatistics() {
        return Observable.create(new Observable.OnSubscribe<PhotoStatisticsEntity>() {
            @Override
            public void call(Subscriber<? super PhotoStatisticsEntity> subscriber) {
                try {
                    PhotoStatisticsEntity statistics = mStatisticsDao.read();
                    subscriber.onNext(statistics);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<PhotoStatisticsEntity> updateStatistics(PhotoStatisticsEntity photoStatisticsEntity) {
        return Observable.create(new Observable.OnSubscribe<PhotoStatisticsEntity>() {
            @Override
            public void call(Subscriber<? super PhotoStatisticsEntity> subscriber) {
                try {
                    PhotoStatisticsEntity oldStatistics = mStatisticsDao.read();
                    mStatisticsDao.save(photoStatisticsEntity);
                    subscriber.onNext(oldStatistics);
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
