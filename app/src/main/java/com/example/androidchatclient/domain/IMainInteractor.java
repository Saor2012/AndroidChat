package com.example.androidchatclient.domain;

import com.example.androidchatclient.data.model.BaseModel;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IMainInteractor {
    Completable connect(String name, BaseModel model);
    Completable send(String message);
    Observable<String> read();
    Completable exit();
    int getIsConnect();
    BaseModel getModel();
}
