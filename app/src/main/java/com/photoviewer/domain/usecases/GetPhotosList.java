package com.photoviewer.domain.usecases;

import com.photoviewer.data.repository.PhotoEntityDataSource;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.photo.PhotoEntityToPhoto;
import com.photoviewer.presentation.di.modules.RxModule;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

public class GetPhotosList extends UseCase<List<Photo>> {

  private PhotoEntityDataSource mPhotoEntityDataSource;
  private final PhotoEntityToPhoto mPhotoTransformer;

  @Inject public GetPhotosList(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      PhotoEntityDataSource photoEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mPhotoTransformer = new PhotoEntityToPhoto();
    mPhotoEntityDataSource = photoEntityDataSource;
  }

  @Override protected Observable<List<Photo>> call() {
    return mPhotoEntityDataSource.photos().map(mPhotoTransformer::transform);
  }
}

