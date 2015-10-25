package com.photoviewer.domain.interactor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.photoviewer.data.repository.PhotoEntityRepository;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.photo.PhotoEntityToPhoto;
import com.photoviewer.presentation.di.modules.ApplicationModule;

import java.util.List;

import roboguice.inject.ContextSingleton;
import rx.Observable;
import rx.Scheduler;

@ContextSingleton
public class GetPhotosList extends UseCase<List<Photo>> {

    private PhotoEntityRepository mPhotoEntityRepository;
    private final PhotoEntityToPhoto mPhotoTransformer;

    @Inject
    public GetPhotosList(@Named(ApplicationModule.BINDING_NAMED_SCHEDULER_COMPUTATION) Scheduler executionScheduler,
                         @Named(ApplicationModule.BINDING_NAMED_SCHEDULER_MAIN_THREAD) Scheduler observingScheduler,
                         PhotoEntityRepository photoEntityRepository) {
        super(executionScheduler, observingScheduler);
        mPhotoTransformer = new PhotoEntityToPhoto();
        mPhotoEntityRepository = photoEntityRepository;
    }

    @Override
    protected Observable<List<Photo>> buildObservable() {
        return mPhotoEntityRepository.photos().map(mPhotoTransformer::transform);
    }
}

