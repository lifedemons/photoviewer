package com.photoviewer.presentation.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApplicationModule extends AbstractModule {

    public static final String BINDING_NAMED_SCHEDULER_COMPUTATION = "computation";
    public static final String BINDING_NAMED_SCHEDULER_MAIN_THREAD = "main_thread";

    @Override
    protected void configure() {
        bind(Scheduler.class).
                annotatedWith(Names.named(BINDING_NAMED_SCHEDULER_COMPUTATION)).
                toInstance(Schedulers.computation());
        bind(Scheduler.class).
                annotatedWith(Names.named(BINDING_NAMED_SCHEDULER_MAIN_THREAD)).
                toInstance(AndroidSchedulers.mainThread());

    }
}
