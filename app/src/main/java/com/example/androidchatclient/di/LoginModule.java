package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.fragment.ILoginPresenter;
import com.example.androidchatclient.presentation.fragment.LoginFragment;
import com.example.androidchatclient.presentation.fragment.LoginPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginModule {
    @Binds
    abstract ILoginPresenter.View bindLoginFragment(LoginFragment view);
    @Binds
    abstract ILoginPresenter.Presenter bindLoginPresenter(LoginPresenter presenter);
}
