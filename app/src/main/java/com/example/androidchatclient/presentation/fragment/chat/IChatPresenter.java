package com.example.androidchatclient.presentation.fragment.chat;

import com.example.androidchatclient.presentation.base.BasePresenter;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IChatPresenter {
    interface View {
        String getText();
        String getChatText();
        void onFinish(int code);
        void setText(String message);
    }
    interface Presenter extends BasePresenter {
        void init();
        void connect(String name);
        Observable<String> read();
        void onSend();
        void onExit();
    }
}
