package com.example.androidchatclient.data.websocket;

import android.annotation.SuppressLint;


import com.example.androidchatclient.BuildConfig;
import com.example.androidchatclient.ConstantApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RepositorySocket implements IRepositorySocket {
    private final static String TAG = "RepositorySocket";
    protected final String adress = "10.0.2.2";
    protected final int port = 5599;
    private DataOutputStream out;
    private DataInputStream in;
    private Socket cs;
    private Observable<String> response;

    @Inject
    public RepositorySocket() {}

    @SuppressLint("CheckResult")
    @Override
    public Completable onConnect(String name) {
        return Completable.defer(() ->
            Completable.fromAction(() -> {
                try {
                    cs = new Socket(adress, port);
                    out = new DataOutputStream(cs.getOutputStream());
                    in = new DataInputStream(cs.getInputStream());
                } catch (SocketException e) {
                    Timber.e("Socket catch exception(SocketException) - %s", e.getMessage());
                    Completable.error(new Exception("Socket got exception(SocketException)"));
                    return;
                }
                if (cs != null && in != null && out != null) {
                    writeUTF(name);
                    Timber.e("User join: %s", name);
                } else {
                    Timber.e("Check socket connection");
                    Completable.error(new Exception("Socket is null"));
                }
        }))
//                .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
        .onErrorResumeNext(throwable -> {
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
                writeUTF(ConstantApp.TAG_MESSAGE.concat(message));
            })
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<String> onRead() {
        if (response == null) getResponse();
        return response.filter(v -> v != null && !v.isEmpty())
            .observeOn(AndroidSchedulers.mainThread())
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
                Timber.tag(TAG).e("User disconnected from server");
            })
        ).subscribeOn(Schedulers.io())
        .onErrorResumeNext(throwable -> {
            Timber.tag(TAG).e("Handle error at close client connaction: %s", ConstantApp.ERROR_READ);
            return Completable.complete();
        });
    }

    private void getResponse() {
        response = Observable.interval(50, TimeUnit.MILLISECONDS, Schedulers.io())
            .flatMap(f -> Observable.just(in.available())
            .doOnError(throwable -> {
                Timber.tag(TAG).e("repository disconnect from server %s", throwable.getMessage());
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
        out.writeUTF(str);
        out.flush();
    }
}
