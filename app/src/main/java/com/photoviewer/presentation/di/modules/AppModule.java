package com.photoviewer.presentation.di.modules;

import android.content.Context;
import android.content.res.Resources;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(library = true, includes = {
    ApiModule.class, DataModule.class, UseCasesModule.class, RxModule.class,
    IntegrationTestsStaticModule.class
}) public class AppModule {
  private Context mContext;

  public AppModule(Context context) {
    mContext = context;
  }

  @Provides Context providesApplicationContext() {
    return mContext;
  }

  @Provides @Singleton Resources getApplicationResources() {
    return mContext.getResources();
  }
}
