package com.photoviewer.domain.interactor;


import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.data.repository.PhotoEntityRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class SearchByTitleTest {
    private static final String FAKE_PHOTO_TITLE = "Fake Title";

    private SearchByTitle mSearchByTitle;

    @Mock
    private PhotoEntityRepository mMockPhotoEntityRepository;
    @Mock
    private Scheduler mMockScheduler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSearchByTitle = new SearchByTitle(mMockScheduler,
                mMockScheduler, mMockPhotoEntityRepository);
    }

    @Test
    public void testSearchByTitle() {
        List<PhotoEntity> photos = new ArrayList<PhotoEntity>() {{
            add(new PhotoEntity());
        }};
        given(mMockPhotoEntityRepository.searchPhotosByTitle(FAKE_PHOTO_TITLE)).willReturn(Observable.just(photos));

        mSearchByTitle.setSearchedTitle(FAKE_PHOTO_TITLE);
        mSearchByTitle.buildObservable();

        verify(mMockPhotoEntityRepository).searchPhotosByTitle(FAKE_PHOTO_TITLE);
        verifyNoMoreInteractions(mMockPhotoEntityRepository);
        verifyZeroInteractions(mMockScheduler);
    }
}