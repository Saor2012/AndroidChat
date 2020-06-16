package com.example.androidchatclient.domain;

import android.annotation.SuppressLint;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.di.MainScope;
import com.example.androidchatclient.domain.base.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import timber.log.Timber;

@MainScope
public class MainInteractor extends BaseInteractor implements IMainInteractor {
    @Inject
    IRepositorySocket repository;
    private int IsConnect = 0;
    private static final String TAG = "MainInteractor";
    private String userName;
    @Inject
    public MainInteractor() {}


    @SuppressLint("CheckResult")
    @Override
    public Completable connect(String name, BaseModel model) {
        if (name.contains(ConstantApp.TAG_LOGIN)) {
            this.IsConnect = 1;
            this.userName = name;
            return repository.onConnect(name)
                .compose(applyCompletableSchedulers())
                .doOnError(Timber::e)
                .doOnComplete(() -> {
                    this.IsConnect = 1;
                    Timber.tag(TAG).e("Connect: %s", IsConnect);
                    Timber.tag(TAG).e("User login: %s", name);
                });
        } else {
            this.IsConnect = 0;
            Timber.tag(TAG).e("Error login command");
            return Completable.defer(() -> {
                    Timber.tag(TAG).e("Is connect: %s", IsConnect);
                    return Completable.error(new Throwable(ConstantApp.ERROR_LOGIN));
                }
            );
        }
    }

    @Override
    public Completable send(String message) {
        return repository.onSend(message)
            .compose(applyCompletableSchedulers())
            .doOnError(Timber::e);
    }

    @Override
    public Observable<String> read() {
        return repository.onRead()
            .compose(applyObservableSchedulers())
            .doOnError(Timber::e)
            .doOnNext(v -> {
                Timber.tag(TAG).e("Is connect: %s", IsConnect);
                if (v.equals(ConstantApp.ERROR_READ)) {
                    Timber.e("Handle error: %s", ConstantApp.ERROR_READ);
                }
            });

    }

    @Override
    public Completable exit() {
        return repository.onExit()
            .compose(applyCompletableSchedulers())
            .doOnError(Timber::e)
            .doOnComplete(() -> {
                this.IsConnect = 0;
                Timber.tag(TAG).e("Exit: %s", IsConnect);
            });
    }

    @Override
    public int getIsConnect() {
        return IsConnect;
    }

    @Override
    public BaseModel getModel() {
        return null;
    }
}
