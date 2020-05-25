package com.example.androidchatclient.di;

import com.example.androidchatclient.domain.IMainInteractor;
import com.example.androidchatclient.domain.MainInteractor;
import com.example.androidchatclient.presentation.activity.IMainPresenter;
import com.example.androidchatclient.presentation.activity.MainActivity;
import com.example.androidchatclient.presentation.activity.MainPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {
    @Binds
    abstract IMainPresenter.View bindMainActivity(MainActivity view);
    @Binds
    abstract IMainPresenter.Presenter bindMainPresenter(MainPresenter presenter);
    @Binds
    abstract IMainInteractor bindInteractor(MainInteractor interactor);
}
