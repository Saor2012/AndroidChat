package com.example.androidchatclient.presentation.activity;

import com.example.androidchatclient.presentation.base.ActivityView;
import com.example.androidchatclient.presentation.base.BasePresenter;

import io.reactivex.Completable;

public interface IMainPresenter {
    interface View extends ActivityView {
//        void onFinish(int code);
    }
    interface Presenter extends BasePresenter {
        void init() throws Throwable;
//        Completable connect(String name);
//        void onExit();
    }
}
