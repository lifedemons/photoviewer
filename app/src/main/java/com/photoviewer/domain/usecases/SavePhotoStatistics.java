package com.photoviewer.domain.usecases;

import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.data.repository.PhotoStatisticsEntityDataSource;
import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.domain.mapper.photostatisctics.PhotoStatisticsToPhotoStatisticsEntity;

import com.photoviewer.presentation.di.modules.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m")
public class SavePhotoStatistics extends UseCase<Void> {
    private final PhotoStatisticsToPhotoStatisticsEntity mPhotoStatisticsToPhotoStatisticsEntityTransformer;

    @Setter
    private PhotoStatistics mPhotoStatistics;

    @Inject PhotoStatisticsEntityDataSource mPhotoStatisticsEntityDataSource;

    @Inject public SavePhotoStatistics(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
        @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler) {
        super(executionScheduler, observingScheduler);
        mPhotoStatisticsToPhotoStatisticsEntityTransformer =
            new PhotoStatisticsToPhotoStatisticsEntity();
    }

    @Override
    protected Observable<Void> buildObservable() {
        PhotoStatisticsEntity photoStatisticsEntity = mPhotoStatisticsToPhotoStatisticsEntityTransformer.transform(mPhotoStatistics);
        return mPhotoStatisticsEntityDataSource.updateStatistics(photoStatisticsEntity).map(photoStatisticsEntity1 -> null);
    }
}