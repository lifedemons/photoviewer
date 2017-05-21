package com.photoviewer.domain.interactor;

import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.repository.PhotoEntityDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Scheduler;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetPhotoDetailsTest {
    private static final int FAKE_PHOTO_ID = 123;

    private GetPhotoDetails mGetPhotoDetails;

    @Mock
    private PhotoEntityDataSource mMockPhotoEntityDataSource;
    @Mock
    private Scheduler mMockScheduler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetPhotoDetails = new GetPhotoDetails(mMockScheduler,
                mMockScheduler, mMockPhotoEntityDataSource);
    }

    @Test
    public void testGetPhotoDetails() {
        given(mMockPhotoEntityDataSource.photo(anyInt())).willReturn(Observable.just(new PhotoEntity()));

        mGetPhotoDetails.setPhotoId(FAKE_PHOTO_ID);
        mGetPhotoDetails.buildObservable();

        verify(mMockPhotoEntityDataSource).photo(FAKE_PHOTO_ID);
        verifyNoMoreInteractions(mMockPhotoEntityDataSource);
        verifyZeroInteractions(mMockScheduler);
    }
}
