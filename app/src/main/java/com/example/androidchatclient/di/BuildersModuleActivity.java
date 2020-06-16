package com.example.androidchatclient.di;

import com.example.androidchatclient.presentation.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModuleActivity {
    @MainScope
    @ContributesAndroidInjector(modules = {MainModule.class, BuildersModuleFragment.class})
    abstract MainActivity bindMainActivity();
}
