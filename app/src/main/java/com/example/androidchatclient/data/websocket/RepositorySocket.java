package com.example.androidchatclient.data.websocket;

import android.annotation.SuppressLint;


import com.example.androidchatclient.BuildConfig;
import com.example.androidchatclient.ConstantApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import timber.log.Timber;

public class RepositorySocket implements IRepositorySocket {
    private final static String TAG = "RepositorySocket";
    private DataOutputStream out;
    private DataInputStream in;
    private Socket cs;
    private Observable<String> response;
    private String userName;

    @Inject
    public RepositorySocket() {}

    @SuppressLint("CheckResult")
    @Override
    public Completable onConnect(String name) {
        return Completable.defer(() ->
            Completable.fromAction(() -> {
                /*try {
//                    cs = new Socket(adress, port);
                    cs = new Socket(BuildConfig.BASE_URL , BuildConfig.SERVER_PORT);
                    out = new DataOutputStream(cs.getOutputStream());
                    in = new DataInputStream(cs.getInputStream());
                } catch (SocketException e) {
                    Timber.e("Socket catch exception(SocketException) - %s", e.getMessage());
                    Completable.error(new Exception("Socket got exception(SocketException)"));
                    return;
                }
                if (cs != null && out != null) {
                    writeUTF(name);
                    userName = name;
                    Timber.e("User join: %s", name);
                } else {
                    Timber.e("Check socket connection");
                    Completable.error(new Exception("Socket is null"));
                }*/
                cs = new Socket(BuildConfig.BASE_URL , BuildConfig.SERVER_PORT);
                out = new DataOutputStream(cs.getOutputStream());
                in = new DataInputStream(cs.getInputStream());

                if (cs != null && out != null) {
                    writeUTF(name);
                    userName = name;
                    Timber.e("User join: %s", name);
                } else {
                    Timber.e("Check socket connection");
//                    Completable.error(new Exception("Socket is null"));
                }
        })).onErrorResumeNext(throwable -> {
           Timber.tag(TAG).e("Handle catched error: %s", ConstantApp.ERROR_READ);
           return Completable.complete();
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Completable onSend(String message) {
        return Completable.defer(() ->
            Completable.fromAction(() -> {
                if (out == null) {
                    Timber.tag(TAG).e("DataOutputStream out is null");
                    Completable.error(new Throwable("missed out object"));
                }
                Timber.tag(TAG).e("Message: %s", message);
                writeUTF(ConstantApp.TAG_MESSAGE.concat(message));
            })
        ).onErrorResumeNext(throwable -> {
            Timber.tag(TAG).e("Handle catched error: %s", ConstantApp.ERROR_SEND);
            return Completable.complete();
        });
    }

    @Override
    public Observable<String> onRead() {
        if (response == null) getResponse();
        return response.filter(v -> v != null && !v.isEmpty())
            .doOnError(Timber::e)
            .onErrorResumeNext(throwable -> {
                Timber.tag(TAG).e("Handle error at read server message: %s", ConstantApp.ERROR_READ);
                return Observable.just(ConstantApp.ERROR_READ);
            });
    }

    @Override
    public Completable onExit() {
        return Completable.defer(() ->
            Completable.fromAction(() -> {
                writeUTF("exit");
                cs.close();
                in.close();
                out.close();
                cs = null;
                in = null;
                out = null;
                Timber.tag(TAG).e("User disconnected from server %s", userName);
            })
        ).onErrorResumeNext(throwable -> {
            Timber.tag(TAG).e("Handle error at close client connaction: %s", ConstantApp.ERROR_READ);
            return Completable.complete();
        });
    }

    private void getResponse() {
        response = Observable.interval(50, TimeUnit.MILLISECONDS)
            .flatMap(f -> Observable.just(in.available())
            .doOnError(throwable -> {
                Timber.tag(TAG).e("Repository disconnect from server %s", throwable.getMessage());
                onExit();
            }).filter(v -> v > 0))
            .flatMap(v -> {
                String msg = in.readUTF();
                return Observable.just(msg);
            })
            .doOnNext(v -> Timber.tag(TAG).e("Response item - %s", v));
    }

    protected void writeUTF(String str) throws IOException {
        if (out == null) {
            Timber.tag(TAG).e("DataOutputStream is null");
            //Спробувати підключення знову або вихід
//            onExit();
            return;
        }
        Timber.tag(TAG).e("Request: %s", str);
        out.writeUTF(str);
        out.flush();
    }
}
