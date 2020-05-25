package com.example.androidchatclient.di;

import android.content.Context;

import com.example.androidchatclient.app.App;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.data.websocket.RepositorySocket;
import com.example.androidchatclient.presentation.route.IRouter;
import com.example.androidchatclient.presentation.route.Router;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Route;

@Module
public abstract class AppModule {
    @Singleton
    @Binds
    abstract Context provideContext(App app);

    @Singleton
    @Provides
    static IRepositorySocket provideRepository() {
        return new RepositorySocket();
    }

    @Singleton
    @Provides
    static IRouter provideRouter() {
        return new Router();
    }
}
