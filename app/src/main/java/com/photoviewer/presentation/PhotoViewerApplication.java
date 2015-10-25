package com.photoviewer.presentation;

import android.app.Application;

import com.photoviewer.presentation.di.modules.ModulesController;

public class PhotoViewerApplication extends Application {

    private ModulesController mModulesController;

    @Override
    public void onCreate() {
        super.onCreate();

        mModulesController = new ModulesController(this);
        mModulesController.onActivate();
    }
}
