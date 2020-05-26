package com.example.androidchatclient.presentation.fragment;

import com.example.androidchatclient.presentation.base.ActivityView;
import com.example.androidchatclient.presentation.base.BasePresenter;

public interface ILoginPresenter {
    interface View {
        void login();
        String getName();
    }
    interface Presenter extends BasePresenter {
        void init();
        void onLogin();
    }
}
