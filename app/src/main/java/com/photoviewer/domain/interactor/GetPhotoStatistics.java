package com.photoviewer.domain.interactor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.photoviewer.data.repository.PhotoStatisticsEntityRepository;
import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.presentation.di.modules.ApplicationModule;

import lombok.experimental.Accessors;
import roboguice.inject.ContextSingleton;
import rx.Observable;
import rx.Scheduler;

@ContextSingleton
@Accessors(prefix = "m")
public class GetPhotoStatistics extends UseCase<PhotoStatistics> {

    @Inject
    private PhotoStatisticsEntityRepository mPhotoStatisticsEntityRepository;
    @Inject
    private GetPhotoDetails mGetPhotoDetailsUseCase;

    @Inject
    public GetPhotoStatistics(@Named(ApplicationModule.BINDING_NAMED_SCHEDULER_COMPUTATION) Scheduler executionScheduler,
                              @Named(ApplicationModule.BINDING_NAMED_SCHEDULER_MAIN_THREAD) Scheduler observingScheduler) {
        super(executionScheduler, observingScheduler);
    }

    @Override
    protected Observable<PhotoStatistics> buildObservable() {
        return mPhotoStatisticsEntityRepository.readStatistics().
                switchMap(photoStatisticsEntity -> {
                    mGetPhotoDetailsUseCase.setPhotoId(photoStatisticsEntity.getLastOpenedPhotoId());
                    return mGetPhotoDetailsUseCase.buildObservable()
                            .map(photo -> {
                                PhotoStatistics merged = new PhotoStatistics();

                                merged.setLastOpenedPhoto(photo);
                                merged.setOpenedPhotosCount(photoStatisticsEntity.getOpenedPhotosCount());

                                return merged;
                            });
                });
    }
}
