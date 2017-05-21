package com.photoviewer.presentation.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module(library = true, complete = false) public class RxModule {

  public static final String COMPUTATION = "computation";
  public static final String IO = "io";
  public static final String MAIN_THREAD = "main_thread";

  @Provides @Singleton @Named(IO) Scheduler provideIoScheduler() {
    return Schedulers.io();
  }

  @Provides @Singleton @Named(MAIN_THREAD) Scheduler provideMainScheduler() {
    return AndroidSchedulers.mainThread();
  }

  @Provides @Singleton @Named(COMPUTATION) Scheduler provideComputationScheduler() {
    return Schedulers.computation();
  }
}
