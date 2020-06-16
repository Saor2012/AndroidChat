package com.example.androidchatclient.presentation.fragment.chat.adapter;

import com.example.androidchatclient.data.model.Message;

public interface IRVAdapter {
    void addList(Message item);
    int getListSize();
}
