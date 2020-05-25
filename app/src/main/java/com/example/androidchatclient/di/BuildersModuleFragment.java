package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.activity.IMainPresenter;
import com.example.androidchatclient.presentation.activity.MainActivity;
import com.example.androidchatclient.presentation.activity.MainPresenter;
import com.example.androidchatclient.presentation.fragment.ILoginPresenter;
import com.example.androidchatclient.presentation.fragment.LoginFragment;
import com.example.androidchatclient.presentation.fragment.LoginPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModuleFragment {
    //LoginFragment
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginFragment bingLoginFragment();
}
