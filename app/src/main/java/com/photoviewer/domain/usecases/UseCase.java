package com.photoviewer.domain.usecases;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    private Subscription subscription = Subscriptions.empty();
    private final Scheduler executionScheduler;
    private final Scheduler observingScheduler;

    protected UseCase(Scheduler executionScheduler,
                      Scheduler observingScheduler) {
        this.executionScheduler = executionScheduler;
        this.observingScheduler = observingScheduler;
    }

    protected abstract Observable<T> call();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber UseCaseSubscriber) {
        this.subscription = this.call()
                .subscribeOn(executionScheduler)
                .observeOn(observingScheduler)
                .subscribe(UseCaseSubscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
