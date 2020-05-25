package com.example.androidchatclient.domain;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IMainInteractor {
    Completable connect(String name);
    Completable send(String message);
    Observable<String> read();
    Completable exit();
}
