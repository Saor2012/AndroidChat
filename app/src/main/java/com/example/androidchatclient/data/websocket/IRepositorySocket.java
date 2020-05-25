package com.example.androidchatclient.data.websocket;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IRepositorySocket {
    Completable onConnect(String name);
    Completable onSend(String message);
    Observable<String> onRead();
    Completable onExit();
}
