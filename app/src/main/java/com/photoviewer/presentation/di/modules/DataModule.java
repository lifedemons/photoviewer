package com.photoviewer.presentation.di.modules;

import com.photoviewer.data.repository.PhotoEntityDataSource;
import com.photoviewer.data.repository.PhotoStatisticsEntityDataSource;
import com.photoviewer.domain.repository.PhotoRepository;
import com.photoviewer.domain.repository.PhotoStatisticsRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, complete = false) public class DataModule {

  @Provides @Singleton
  public PhotoRepository providesPhotoRepository(PhotoEntityDataSource repository) {
    return repository;
  }

  @Provides @Singleton public PhotoStatisticsRepository providesPhotoStatisticsRepository(
      PhotoStatisticsEntityDataSource repository) {
    return repository;
  }
}
