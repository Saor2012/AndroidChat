package com.example.androidchatclient.presentation.fragment.chat;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.domain.IMainInteractor;
import com.example.androidchatclient.presentation.route.IRouter;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import timber.log.Timber;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChatPresenter implements IChatPresenter.Presenter  {
    @Inject
    IChatPresenter.View view;
    @Inject
    IRouter router;
    @Inject
    IMainInteractor interactor;
    private CompositeDisposable disposable;

    @Inject
    public ChatPresenter() {}

    @Override
    public void init() {
        initListener();
    }

    @Override
    public void connect(String name) {

    }

    @Override
    public Observable<String> read() {
        return null;
    }

    @Override
    public void onSend() {
        handlerSend(view.getText());
    }

    private void handlerSend(String message) {
        if (!message.equals("") && message.length() > 0) {
            interactor.send(message).subscribe(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    if (disposable != null) {
                        initListener();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Timber.tag(TAG).e("Error on send message: %s", e.getMessage());
                }
            });
        }
    }

    private void initListener() {
        disposable = new CompositeDisposable();
        listener();
        Timber.tag(TAG).e("Start listening for new messagers.");
    }

    private void listener() {
        String text = view.getChatText();
        AtomicReference<String> textarea = new AtomicReference<>("");
        disposable.add(interactor.read()
            .subscribe(v -> {
                Timber.tag(TAG).e("Recieved message: %s", v);
                textarea.set(String.valueOf(text));
                textarea.get().concat(v);
            }));
        view.setText(textarea.get());
    }

    @Override
    public void onExit() {
        handlerExit();
        view.onFinish(ConstantApp.FINISH_ACTIVITY_CODE);
    }

    private void handlerExit() {
        interactor.exit()
            .subscribe(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    Timber.e("onExit() onComplete");
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e("onExit() onError - %s", e.getMessage());
                }
            });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (view != null) view = null;
    }
}
