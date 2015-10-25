package com.photoviewer.domain.interactor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.data.repository.PhotoStatisticsEntityRepository;
import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.domain.mapper.photostatisctics.PhotoStatisticsToPhotoStatisticsEntity;
import com.photoviewer.presentation.di.modules.ApplicationModule;

import lombok.Setter;
import lombok.experimental.Accessors;
import roboguice.inject.ContextSingleton;
import rx.Observable;
import rx.Scheduler;

@ContextSingleton
@Accessors(prefix = "m")
public class SavePhotoStatistics extends UseCase<Void> {
    private final PhotoStatisticsToPhotoStatisticsEntity mPhotoStatisticsToPhotoStatisticsEntityTransformer;

    @Setter
    private PhotoStatistics mPhotoStatistics;

    @Inject
    private PhotoStatisticsEntityRepository mPhotoStatisticsEntityRepository;

    @Inject
    public SavePhotoStatistics(@Named(ApplicationModule.BINDING_NAMED_SCHEDULER_COMPUTATION) Scheduler executionScheduler,
                              @Named(ApplicationModule.BINDING_NAMED_SCHEDULER_MAIN_THREAD) Scheduler observingScheduler) {
        super(executionScheduler, observingScheduler);
        mPhotoStatisticsToPhotoStatisticsEntityTransformer = new PhotoStatisticsToPhotoStatisticsEntity();
    }

    @Override
    protected Observable<Void> buildObservable() {
        PhotoStatisticsEntity photoStatisticsEntity = mPhotoStatisticsToPhotoStatisticsEntityTransformer.transform(mPhotoStatistics);
        return mPhotoStatisticsEntityRepository.updateStatistics(photoStatisticsEntity).map(photoStatisticsEntity1 -> null);
    }
}