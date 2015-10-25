package com.photoviewer.data.db.migrations;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.db.DatabaseManager;

import java.sql.SQLException;

public class CreatePhotosTable extends Migration {
    @Override
    public void up(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PhotoEntity.class);
        } catch (SQLException e) {
            Log.wtf(DatabaseManager.LOG_TAG_DB, "Creation of PhotosTable failed", e);
        }
    }
}
