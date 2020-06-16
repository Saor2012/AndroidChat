package com.example.androidchatclient.presentation.fragment.login;

import com.example.androidchatclient.data.model.FragmentModel;
import com.example.androidchatclient.presentation.base.ActivityView;
import com.example.androidchatclient.presentation.base.BasePresenter;

public interface ILoginPresenter {
    interface View {
        void onFinish(int code);
        String getName();
        void setResponse(String text);
        void toast(String message);
    }
    interface Presenter extends BasePresenter {
        void init(FragmentModel viewModel);
        void init();
        void onLogin();
        void onExit();
    }
}
