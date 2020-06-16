package com.example.androidchatclient.presentation.fragment.chat;

import com.example.androidchatclient.data.model.Message;
import com.example.androidchatclient.presentation.base.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

public interface IChatPresenter {
    interface View {
        String getText();
        void setNullSendMsg();
        String getChatText();
        void onFinish(int code);
        void updateAdapter(Message model);
        void updateAdapter(List<Message> list);
        void setText(Message message);
        void toast(String message);
    }
    interface Presenter extends BasePresenter {
        void init(String login);
        void connect(String name);
        Observable<String> read();
        void onSend();
        void onExit();
        void onLongExit();
        void onRestart();
    }
}
