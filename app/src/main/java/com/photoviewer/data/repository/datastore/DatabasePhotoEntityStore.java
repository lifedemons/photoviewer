package com.photoviewer.data.repository.datastore;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.db.DatabaseManager;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class DatabasePhotoEntityStore {

    private static final String LOG_TAG = DatabasePhotoEntityStore.class.getSimpleName();
    private static final String PERCENT = "%";

    private Dao<PhotoEntity, Integer> mPhotosDao;

    //Search prepared fields, for faster search
    private PreparedQuery<PhotoEntity> mSearchByTitleQuery;
    private SelectArg mSearchByTitleQuerySelectArg;

    @Inject
    public DatabasePhotoEntityStore(DatabaseManager databaseManager) {
        mPhotosDao = databaseManager.getPhotosDao();
        prepareSearchByTitleQuery();
    }

    public Observable<List<PhotoEntity>> queryForAll() {
        return Observable.create(new Observable.OnSubscribe<List<PhotoEntity>>() {
            @Override
            public void call(Subscriber<? super List<PhotoEntity>> subscriber) {
                try {
                    List<PhotoEntity> photos = mPhotosDao.queryForAll();
                    subscriber.onNext(photos);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<Void> saveAll(final Collection<PhotoEntity> entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    saveAllSynchronous(entities);
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void saveAllSynchronous(final Collection<PhotoEntity> entities) throws SQLException {
        TransactionManager.callInTransaction(mPhotosDao.getConnectionSource(),
                () -> {
                    for (PhotoEntity photoEntity : entities) {
                        mPhotosDao.createOrUpdate(photoEntity);
                    }
                    return null;
                });
    }

    public Observable<PhotoEntity> queryForId(int photoId) {
        return Observable.create(new Observable.OnSubscribe<PhotoEntity>() {
            @Override
            public void call(Subscriber<? super PhotoEntity> subscriber) {
                try {
                    PhotoEntity photo = mPhotosDao.queryForId(photoId);
                    subscriber.onNext(photo);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<List<PhotoEntity>> queryForTitle(String title) {
        return Observable.create(new Observable.OnSubscribe<List<PhotoEntity>>() {
            @Override
            public void call(Subscriber<? super List<PhotoEntity>> subscriber) {
                try {
                    mSearchByTitleQuerySelectArg.setValue(PERCENT + title + PERCENT);
                    List<PhotoEntity> photos = mPhotosDao.query(mSearchByTitleQuery);
                    subscriber.onNext(photos);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private void prepareSearchByTitleQuery() {
        try {
            QueryBuilder<PhotoEntity, Integer> queryBuilder = mPhotosDao.queryBuilder();
            mSearchByTitleQuerySelectArg = new SelectArg();
            queryBuilder.where().like(PhotoEntity.Fields.TITLE, mSearchByTitleQuerySelectArg);
            mSearchByTitleQuery = queryBuilder.prepare();
        } catch (SQLException e) {
            Log.wtf(LOG_TAG, "Preparing of SearchByTitleQuery failed", e);
        }
    }
}
