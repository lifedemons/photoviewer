package com.photoviewer.domain.usecases;

import com.photoviewer.data.repository.PhotoEntityDataSource;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.photo.PhotoEntityToPhoto;

import com.photoviewer.presentation.di.modules.RxModule;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Scheduler;

@Accessors(prefix = "m") public class SearchByTitle extends UseCase<List<Photo>> {

  @Setter private String mSearchedTitle;
  private PhotoEntityDataSource mPhotoEntityDataSource;
  private final PhotoEntityToPhoto photoTransformer;

  @Inject public SearchByTitle(@Named(RxModule.COMPUTATION) Scheduler executionScheduler,
      @Named(RxModule.MAIN_THREAD) Scheduler observingScheduler,
      PhotoEntityDataSource photoEntityDataSource) {
    super(executionScheduler, observingScheduler);
    mPhotoEntityDataSource = photoEntityDataSource;
    photoTransformer = new PhotoEntityToPhoto();
  }

  @Override protected Observable<List<Photo>> call() {
    return this.mPhotoEntityDataSource.searchPhotosByTitle(mSearchedTitle)
        .map(photoTransformer::transform);
  }
}

