package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.fragment.chat.ChatFragment;
import com.example.androidchatclient.presentation.fragment.chat.ChatPresenter;
import com.example.androidchatclient.presentation.fragment.chat.IChatPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ChatModule {
    @Binds
    abstract IChatPresenter.View bindLoginFragment(ChatFragment view);
    @Binds
    abstract IChatPresenter.Presenter bindLoginPresenter(ChatPresenter presenter);
}
