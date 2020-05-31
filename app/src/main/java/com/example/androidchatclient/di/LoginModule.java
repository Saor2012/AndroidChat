package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.fragment.login.ILoginPresenter;
import com.example.androidchatclient.presentation.fragment.login.LoginFragment;
import com.example.androidchatclient.presentation.fragment.login.LoginPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginModule {
    @Binds
    abstract ILoginPresenter.View bindLoginFragment(LoginFragment view);
    @Binds
    abstract ILoginPresenter.Presenter bindLoginPresenter(LoginPresenter presenter);
}
