package com.photoviewer.presentation.di.modules;

import android.app.Application;

import roboguice.RoboGuice;

public class ModulesController {

    private Application mApplication;

    public ModulesController(Application application) {
        mApplication = application;
    }

    public void onActivate() {
        ApplicationModule applicationModule = new ApplicationModule();
        RoboGuice.getOrCreateBaseApplicationInjector(mApplication, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(mApplication), applicationModule);
    }
}

