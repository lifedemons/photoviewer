package com.photoviewer.domain.usecases;

import com.photoviewer.data.repository.PhotoEntityDataSource;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.photo.PhotoEntityToPhoto;
import com.photoviewer.presentation.di.modules.RxModule;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class GetPhotoDetails extends UseCase<Photo> {

  @Setter private int mPhotoId;
  private final PhotoEntityToPhoto mPhotoTransformer;

  private PhotoEntityDataSource mPhotoEntityDataSource;

  @Inject public GetPhotoDetails(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      PhotoEntityDataSource photoEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mPhotoTransformer = new PhotoEntityToPhoto();
    mPhotoEntityDataSource = photoEntityDataSource;
  }

  @Override protected Observable<Photo> buildObservable() {
    return this.mPhotoEntityDataSource.photo(mPhotoId).map(mPhotoTransformer::transform);
  }
}
