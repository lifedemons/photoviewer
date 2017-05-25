package com.photoviewer.di;

import com.photoviewer.common.steps.PageObject;
import com.photoviewer.pages.PhotosListPage;
import com.photoviewer.presentation.di.modules.AppModule;
import dagger.Module;

@Module(injects = { PageObject.class, PhotosListPage.class }, addsTo = AppModule.class)
public class TestsModule {
}
