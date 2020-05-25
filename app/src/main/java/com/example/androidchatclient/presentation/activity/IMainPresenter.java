package com.example.androidchatclient.presentation.activity;

import com.example.androidchatclient.presentation.base.ActivityView;
import com.example.androidchatclient.presentation.base.BasePresenter;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IMainPresenter {
    interface View extends ActivityView {
        String getText();
        void onFinish(int code);
        void setText(String message);
    }
    interface Presenter extends BasePresenter {
        void init() throws Throwable;
        Completable connect(String name);
        Completable send(String message);
        Observable<String> read();
        Completable exit();
        void onSend();
        void onExit();
    }
}
