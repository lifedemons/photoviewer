package com.photoviewer.domain.interactor;

import com.photoviewer.data.repository.PhotoEntityRepository;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.photo.PhotoEntityToPhoto;
import com.photoviewer.presentation.di.modules.RxModule;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

public class GetPhotosList extends UseCase<List<Photo>> {

  private PhotoEntityRepository mPhotoEntityRepository;
  private final PhotoEntityToPhoto mPhotoTransformer;

  @Inject public GetPhotosList(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      PhotoEntityRepository photoEntityRepository) {
    super(executionScheduler, observingScheduler);
    mPhotoTransformer = new PhotoEntityToPhoto();
    mPhotoEntityRepository = photoEntityRepository;
  }

  @Override protected Observable<List<Photo>> buildObservable() {
    return mPhotoEntityRepository.photos().map(mPhotoTransformer::transform);
  }
}

