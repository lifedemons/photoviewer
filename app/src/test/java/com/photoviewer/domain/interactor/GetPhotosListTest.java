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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetPhotosListTest {

    private GetPhotosList mGetPhotosList;

    @Mock
    private PhotoEntityRepository mMockPhotoEntityRepository;
    @Mock
    private Scheduler mMockScheduler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mGetPhotosList = new GetPhotosList(mMockScheduler,
                mMockScheduler, mMockPhotoEntityRepository);
    }

    @Test
    public void testGetPhotosList() {
        List<PhotoEntity> photos = new ArrayList<PhotoEntity>() {{
            add(new PhotoEntity());
        }};

        given(mMockPhotoEntityRepository.photos()).willReturn(Observable.just(photos));

        mGetPhotosList.buildObservable();

        verify(mMockPhotoEntityRepository).photos();
        verifyZeroInteractions(mMockScheduler);
    }
}


