package com.photoviewer.domain.interactor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.photoviewer.data.repository.PhotoEntityRepository;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.photo.PhotoEntityToPhoto;
import com.photoviewer.presentation.di.modules.ApplicationModule;

import lombok.Setter;
import lombok.experimental.Accessors;
import roboguice.inject.ContextSingleton;
import rx.Observable;
import rx.Scheduler;

@ContextSingleton
@Accessors(prefix = "m")
public class GetPhotoDetails extends UseCase<Photo> {

    @Setter
    private int mPhotoId;
    private final PhotoEntityToPhoto mPhotoTransformer;

    private PhotoEntityRepository mPhotoEntityRepository;

    @Inject
    public GetPhotoDetails(@Named(ApplicationModule.BINDING_NAMED_SCHEDULER_COMPUTATION) Scheduler executionScheduler,
                           @Named(ApplicationModule.BINDING_NAMED_SCHEDULER_MAIN_THREAD) Scheduler observingScheduler,
                           PhotoEntityRepository photoEntityRepository) {
        super(executionScheduler, observingScheduler);
        mPhotoTransformer = new PhotoEntityToPhoto();
        mPhotoEntityRepository = photoEntityRepository;
    }

    @Override
    protected Observable<Photo> buildObservable() {
        return this.mPhotoEntityRepository.photo(mPhotoId).map(mPhotoTransformer::transform);
    }
}
