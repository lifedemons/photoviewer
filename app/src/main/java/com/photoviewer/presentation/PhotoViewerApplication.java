package com.photoviewer.presentation;

import android.app.Application;
import com.photoviewer.presentation.di.modules.AppModule;
import dagger.ObjectGraph;

public class PhotoViewerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        sObjectGraph = ObjectGraph.create(new AppModule(this));
    }

    private static ObjectGraph sObjectGraph;

    public static ObjectGraph getScopedGraph(Object... modules) {
        return sObjectGraph.plus(modules);
    }
}
