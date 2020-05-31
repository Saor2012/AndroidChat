package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.fragment.chat.ChatFragment;
import com.example.androidchatclient.presentation.fragment.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModuleFragment {
    //LoginFragment
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginFragment bingLoginFragment();

    @ContributesAndroidInjector(modules = ChatModule.class)
    abstract ChatFragment bingChatFragment();
}
