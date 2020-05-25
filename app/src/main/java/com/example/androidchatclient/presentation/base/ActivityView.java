package com.example.androidchatclient.presentation.base;

import com.example.androidchatclient.presentation.fragment.LoginFragment;

import dagger.android.support.DaggerAppCompatDialogFragment;
import dagger.android.support.DaggerFragment;

public interface ActivityView {
    <T>void transactionActivity(Class<?> activity, T object);
    void transactionActivity(Class<?> activity);
    void transitionFragment(DaggerFragment fragment, int container);
    void transitionFragmentDialog(DaggerAppCompatDialogFragment fragment, int container);
    void closeFragment(DaggerFragment fragment);
    void back();
    void restart();
}
