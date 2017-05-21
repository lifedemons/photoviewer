package com.photoviewer.data.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.photoviewer.data.entity.PhotoEntity;

import java.sql.SQLException;
import javax.inject.Inject;

public class DatabaseManager {

    public static final String LOG_TAG_DB = "Photos:Database";
    public static final String DEFAULT_DATABASE_NAME = "photo_viewer";

    private final ConnectionSource mConnectionSource;

    //DAOs
    private Dao<PhotoEntity, Integer> mPhotosDao;

    @Inject
    public DatabaseManager(Context context) {
        this(context, DEFAULT_DATABASE_NAME);
    }

    public DatabaseManager(Context context, String databaseName) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, databaseName);
        mConnectionSource = databaseHelper.getConnectionSource(); // force database creation/upgrade eagerly
    }

    public Dao<PhotoEntity, Integer> getPhotosDao() {
        if (mPhotosDao == null) {
            createPhotoEntityDao();
        }

        return mPhotosDao;
    }

    private void createPhotoEntityDao() {
        try {
            mPhotosDao = DaoManager.createDao(mConnectionSource, PhotoEntity.class);
            mPhotosDao.setObjectCache(true);
        } catch (SQLException e) {
            Log.wtf(LOG_TAG_DB, "Creation of Dao<" + PhotoEntity.class + "> failed", e);
        }
    }
}
