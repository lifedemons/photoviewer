package com.photoviewer.data.repository;

import com.photoviewer.ApplicationTestCase;
import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.repository.datastore.DatabasePhotoEntityStore;
import com.photoviewer.data.repository.datastore.ServerPhotoEntityStore;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.usecases.SimpleSubscriber;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class PhotoEntityDataSourceTest extends ApplicationTestCase {

    private static final int FAKE_PHOTO_ID = 31;

    private PhotoEntityDataSource mPhotoEntityDataSource;

    @Mock
    private DatabasePhotoEntityStore mMockDatabasePhotoEntityStore;
    @Mock
    private ServerPhotoEntityStore mMockServerPhotoEntityStore;
    @Mock
    private PhotoEntity mMockPhotoEntity;
    @Mock
    private Photo mMockPhoto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPhotoEntityDataSource = new PhotoEntityDataSource(mMockDatabasePhotoEntityStore, mMockServerPhotoEntityStore);
    }

    @Test
    public void testGetPhotosFromServerPositiveCase() {
        List<PhotoEntity> photosList = new ArrayList<>();
        photosList.add(new PhotoEntity());
        given(mMockDatabasePhotoEntityStore.queryForAll()).willReturn(Observable.just(new ArrayList<PhotoEntity>()));
        given(mMockServerPhotoEntityStore.photoEntityList()).willReturn(Observable.just(photosList));

        mPhotoEntityDataSource.photos().subscribe(new SimpleSubscriber<List<PhotoEntity>>());

        verify(mMockDatabasePhotoEntityStore).queryForAll();
        verify(mMockServerPhotoEntityStore).photoEntityList();
        verify(mMockDatabasePhotoEntityStore).saveAll(photosList);
    }

    @Test
    public void testGetPhotosFromDBPositiveCase() {
        List<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(new PhotoEntity());
        given(mMockDatabasePhotoEntityStore.queryForAll()).willReturn(Observable.just(photoEntities));
        given(mMockServerPhotoEntityStore.photoEntityList()).willReturn(Observable.just(photoEntities));

        mPhotoEntityDataSource.photos().subscribe();

        verify(mMockDatabasePhotoEntityStore).queryForAll();
        verifyZeroInteractions(mMockServerPhotoEntityStore);
    }

    @Test
    public void testGetPhotoPositiveCase() {
        PhotoEntity photoEntity = new PhotoEntity();
        given(mMockDatabasePhotoEntityStore.queryForId(FAKE_PHOTO_ID)).willReturn(Observable.just(photoEntity));

        mPhotoEntityDataSource.photo(FAKE_PHOTO_ID).subscribe();

        verify(mMockDatabasePhotoEntityStore).queryForId(FAKE_PHOTO_ID);
    }
}
