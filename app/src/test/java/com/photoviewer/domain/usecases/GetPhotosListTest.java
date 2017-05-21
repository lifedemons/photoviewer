package com.photoviewer.domain.usecases;

import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.repository.PhotoEntityDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetPhotosListTest {

    private GetPhotosList mGetPhotosList;

    @Mock
    private PhotoEntityDataSource mMockPhotoEntityDataSource;
    @Mock
    private Scheduler mMockScheduler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetPhotosList = new GetPhotosList(mMockScheduler,
                mMockScheduler, mMockPhotoEntityDataSource);
    }

    @Test
    public void testGetPhotosList() {
        List<PhotoEntity> photos = new ArrayList<PhotoEntity>() {{
            add(new PhotoEntity());
        }};

        given(mMockPhotoEntityDataSource.photos()).willReturn(Observable.just(photos));

        mGetPhotosList.buildObservable();

        verify(mMockPhotoEntityDataSource).photos();
        verifyZeroInteractions(mMockScheduler);
    }
}


