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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class SearchByTitleTest {
    private static final String FAKE_PHOTO_TITLE = "Fake Title";

    private SearchByTitle mSearchByTitle;

    @Mock
    private PhotoEntityDataSource mMockPhotoEntityDataSource;
    @Mock
    private Scheduler mMockScheduler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSearchByTitle = new SearchByTitle(mMockScheduler,
                mMockScheduler, mMockPhotoEntityDataSource);
    }

    @Test
    public void testSearchByTitle() {
        List<PhotoEntity> photos = new ArrayList<PhotoEntity>() {{
            add(new PhotoEntity());
        }};
        given(mMockPhotoEntityDataSource.searchPhotosByTitle(FAKE_PHOTO_TITLE)).willReturn(Observable.just(photos));

        mSearchByTitle.setSearchedTitle(FAKE_PHOTO_TITLE);
        mSearchByTitle.buildObservable();

        verify(mMockPhotoEntityDataSource).searchPhotosByTitle(FAKE_PHOTO_TITLE);
        verifyNoMoreInteractions(mMockPhotoEntityDataSource);
        verifyZeroInteractions(mMockScheduler);
    }
}