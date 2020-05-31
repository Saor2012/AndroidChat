package com.example.androidchatclient.domain;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.domain.base.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import timber.log.Timber;

public class MainInteractor extends BaseInteractor implements IMainInteractor {
    @Inject
    IRepositorySocket repository;
    private int IsConnect = 0;
    private static final String TAG = "MainInteractor";
    @Inject
    public MainInteractor() {}


    @Override
    public Completable connect(String name) {
        if (name.contains(ConstantApp.TAG_LOGIN)) {
            return repository.onConnect(name)
                .doOnError(Timber::e)
                .doOnComplete(() -> {
                    IsConnect = 1;
                }).compose(applyCompletableSchedulers());
        } else {
            Timber.tag(TAG).e("Error login command");
            return Completable.defer(() ->
                Completable.error(new Throwable(ConstantApp.ERROR_LOGIN))
            );
        }
    }

    @Override
    public Completable send(String message) {
        return Completable.fromAction(() -> repository.onSend(message))
            .doOnError(Timber::e);
    }

    @Override
    public Observable<String> read() {
        return repository.onRead()
            .doOnError(Timber::e)
            .doOnNext(v -> {
                if (v.equals(ConstantApp.ERROR_READ)) {
                    Timber.e("Handle error: %s", ConstantApp.ERROR_READ);
                }
            });

    }

    @Override
    public Completable exit() {
        return Completable.fromAction(() -> repository.onExit())
            .doOnError(Timber::e);
    }

    @Override
    public int getIsConnect() {
        return IsConnect;
    }
}
