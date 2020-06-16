package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.fragment.chat.ChatFragment;
import com.example.androidchatclient.presentation.fragment.chat.ChatPresenter;
import com.example.androidchatclient.presentation.fragment.chat.IChatPresenter;
import com.example.androidchatclient.presentation.fragment.chat.adapter.IRVAdapter;
import com.example.androidchatclient.presentation.fragment.chat.adapter.RVAdapter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ChatModule {
    @Binds
    abstract IChatPresenter.View bindLoginFragment(ChatFragment view);
    //@ChatScope - для 2 положень екранів 1 презентер
    @Binds
    abstract IChatPresenter.Presenter bindLoginPresenter(ChatPresenter presenter);
    @Binds
    abstract IRVAdapter bindAdapter(RVAdapter adapter);
}
