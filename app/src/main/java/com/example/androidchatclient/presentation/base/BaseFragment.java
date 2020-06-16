package com.example.androidchatclient.presentation.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<Binding extends ViewDataBinding> extends DaggerFragment {
    private Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        initView();
        return binding.getRoot();
    }

    protected abstract void initView();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onStart() {
        super.onStart();
        onStartFragmentView();
    }

    protected Binding getBinding(){
        return binding;
    }

    @Override
    public void onDestroy() {
        if (getPresenter() != null) {
            onDestroyFragmentView();
            getPresenter().onStop();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        onDetachFragment();
        super.onDetach();
    }

    @Override
    public void onStop() {
        onStopFragment();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeFragment();
    }

    protected abstract void onStartFragmentView();

    protected abstract void onDestroyFragmentView();

    protected abstract void onDetachFragment();

    protected abstract void onStopFragment();

    protected abstract void onResumeFragment();

    protected abstract BasePresenter getPresenter();
}
