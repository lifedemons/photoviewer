package com.photoviewer.domain.usecases;

import com.photoviewer.data.repository.PhotoStatisticsEntityDataSource;
import com.photoviewer.domain.PhotoStatistics;

import com.photoviewer.presentation.di.modules.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class GetPhotoStatistics extends UseCase<PhotoStatistics> {

  @Inject PhotoStatisticsEntityDataSource mPhotoStatisticsEntityDataSource;
  @Inject GetPhotoDetails mGetPhotoDetailsUseCase;

  @Inject public GetPhotoStatistics(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler) {
    super(executionScheduler, observingScheduler);
  }

  @Override protected Observable<PhotoStatistics> buildObservable() {
    return mPhotoStatisticsEntityDataSource.readStatistics().
        switchMap(photoStatisticsEntity -> {
          mGetPhotoDetailsUseCase.setPhotoId(photoStatisticsEntity.getLastOpenedPhotoId());
          return mGetPhotoDetailsUseCase.buildObservable().map(photo -> {
            PhotoStatistics merged = new PhotoStatistics();

            merged.setLastOpenedPhoto(photo);
            merged.setOpenedPhotosCount(photoStatisticsEntity.getOpenedPhotosCount());

            return merged;
          });
        });
  }
}
