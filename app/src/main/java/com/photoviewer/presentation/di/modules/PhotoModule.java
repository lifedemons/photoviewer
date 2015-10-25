package com.photoviewer.presentation.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.photoviewer.domain.interactor.GetPhotoDetails;
import com.photoviewer.domain.interactor.GetPhotosList;
import com.photoviewer.domain.interactor.UseCase;

public class PhotoModule extends AbstractModule {
    @Override
    protected void configure() {
//        bind(UseCase.class).
//                annotatedWith(Names.named("photoList")).
//                to(GetPhotosList.class);
//        bind(UseCase.class).
//                annotatedWith(Names.named("photoDetails")).
//                to(GetPhotoDetails.class);
    }
}
