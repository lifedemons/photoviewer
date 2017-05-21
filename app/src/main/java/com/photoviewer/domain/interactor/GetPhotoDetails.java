package com.photoviewer.domain.interactor;

import com.photoviewer.data.repository.PhotoEntityRepository;
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

  private PhotoEntityRepository mPhotoEntityRepository;

  @Inject public GetPhotoDetails(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      PhotoEntityRepository photoEntityRepository) {
    super(executionScheduler, observingScheduler);
    mPhotoTransformer = new PhotoEntityToPhoto();
    mPhotoEntityRepository = photoEntityRepository;
  }

  @Override protected Observable<Photo> buildObservable() {
    return this.mPhotoEntityRepository.photo(mPhotoId).map(mPhotoTransformer::transform);
  }
}
