package com.photoviewer.data.repository.datasource;

import com.j256.ormlite.dao.Dao;
import com.photoviewer.ApplicationTestCase;
import com.photoviewer.data.db.DatabaseManager;
import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.repository.datastore.DatabasePhotoEntityStore;
import com.photoviewer.domain.interactor.SimpleSubscriber;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabasePhotoEntityStoreTest extends ApplicationTestCase {

    public static final String TEST_TITLE = "Test title";
    private DatabasePhotoEntityStore mDatabasePhotoEntityStore;

    //Passed null as Database Name for using in Memory DB
    private DatabaseManager mMockDatabaseManager;

    private Dao<PhotoEntity, Integer> mPhotosDao;
    private PhotoEntity mPhotoEntity;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        //Passed null as Database Name for using in Memory DB
        mMockDatabaseManager = new DatabaseManager(RuntimeEnvironment.application, null);
        mPhotosDao = mMockDatabaseManager.getPhotosDao();
        mDatabasePhotoEntityStore = new DatabasePhotoEntityStore(mMockDatabaseManager);

        initTestData();
    }

    private void initTestData() throws SQLException {
        mPhotoEntity = new PhotoEntity();
        mPhotoEntity.setId(1);
        mPhotoEntity.setTitle(TEST_TITLE);
        mPhotosDao.createOrUpdate(mPhotoEntity);
    }

    @Test
    public void testQueryForAll() throws SQLException {
        final boolean[] contains = {false};
        mDatabasePhotoEntityStore.queryForAll().subscribe(new SimpleSubscriber<List<PhotoEntity>>() {
            @Override
            public void onNext(List<PhotoEntity> photoEntities) {
                contains[0] = photoEntities.contains(mPhotoEntity);
            }
        });
        Assert.assertTrue(contains[0]);
    }

    @Test
    public void testQueryForId() {
        final boolean[] contains = {false};
        mDatabasePhotoEntityStore.queryForId(mPhotoEntity.getId()).subscribe(new SimpleSubscriber<PhotoEntity>() {
            @Override
            public void onNext(PhotoEntity photoEntity) {
                contains[0] = photoEntity.equals(mPhotoEntity);
            }
        });
        Assert.assertTrue(contains[0]);
    }

    @Test
    public void testQueryForTitle() {
        final boolean[] contains = {false};
        mDatabasePhotoEntityStore.queryForTitle(TEST_TITLE).subscribe(new SimpleSubscriber<List<PhotoEntity>>() {
            @Override
            public void onNext(List<PhotoEntity> photoEntities) {
                contains[0] = photoEntities.contains(mPhotoEntity);
            }
        });
        Assert.assertTrue(contains[0]);
    }

    @Test
    public void testSaveAll() throws SQLException {
        PhotoEntity savedPhotoEntity = new PhotoEntity();
        savedPhotoEntity.setId(2);
        savedPhotoEntity.setTitle("Test title 2");
        List<PhotoEntity> list = new ArrayList<PhotoEntity>(){{
            add(savedPhotoEntity);
        }};

        mDatabasePhotoEntityStore.saveAll(list).subscribe();

        List items  = mPhotosDao.queryForAll();
        Assert.assertTrue(items.contains(savedPhotoEntity));
    }
}
