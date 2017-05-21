package com.photoviewer.presentation.di.modules;

import com.photoviewer.presentation.view.activity.DiAppCompatActivity;
import com.photoviewer.presentation.view.activity.PhotoDetailsActivity;
import com.photoviewer.presentation.view.activity.PhotosListActivity;
import dagger.Module;

@Module(injects = {
    DiAppCompatActivity.class, PhotosListActivity.class, PhotoDetailsActivity.class
}, addsTo = AppModule.class) public class DiActivityModule {
}
