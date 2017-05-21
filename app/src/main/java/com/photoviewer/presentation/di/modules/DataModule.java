package com.photoviewer.presentation.di.modules;

import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.data.repository.PhotoEntityRepository;
import com.photoviewer.data.repository.PhotoStatisticsEntityRepository;
import com.photoviewer.domain.repository.PhotoRepository;
import com.photoviewer.domain.repository.PhotoStatisticsRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class DataModule {

  @Provides @Singleton
  public PhotoRepository providesPhotoRepository(PhotoEntityRepository repository) {
    return repository;
  }

  @Provides @Singleton public PhotoStatisticsRepository providesPhotoStatisticsRepository(
      PhotoStatisticsEntityRepository repository) {
    return repository;
  }
}
