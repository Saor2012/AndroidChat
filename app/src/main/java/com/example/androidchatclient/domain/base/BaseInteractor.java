package com.example.androidchatclient.domain.base;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseInteractor {
    private Scheduler jobThread;
    private Scheduler observeThread;

    public BaseInteractor() {
        jobThread = Schedulers.io();
        observeThread = AndroidSchedulers.mainThread();
    }


    protected <T> SingleTransformer<T, T> applySingleSchedulers() {
        return single -> single.subscribeOn(jobThread)
                .observeOn(observeThread);
    }

    protected <T> FlowableTransformer<T, T> applyFlowableSchedulers() {
        return flowable -> flowable.subscribeOn(jobThread)
                .observeOn(observeThread);
    }

    protected <T> ObservableTransformer<T, T> applyObservableSchedulers() {
        return observable -> observable.subscribeOn(jobThread)
                .observeOn(observeThread);
    }

    protected CompletableTransformer applyCompletableSchedulers() {
        return completable -> completable.subscribeOn(jobThread)
                .observeOn(observeThread);
    }
}
