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
    private static final String TAG = "ChatPresenter";

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
                    if (disposable == null) {
                        initListener();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Timber.tag(TAG).e("Error on send message: %s", e.getMessage());
                }
            });
        } else {
            Timber.tag(TAG).e("Message is empty: %s", message);
        }
    }

    private void initListener() {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        listener();
        Timber.tag(TAG).e("Start listening for new messagers.");
    }

    private void listener() {
        Timber.tag(TAG).e("Get chat: %s", view.getChatText());
        AtomicReference<String> textarea = new AtomicReference<>("");
        disposable.add(interactor.read()
            .subscribe(v -> {
                String text = view.getChatText();
                textarea.set(String.valueOf(text));
                textarea.set(textarea.get().concat(v).concat("\n"));
                Timber.tag(TAG).e("New message: %s", textarea.get());
                view.setText(textarea.get());
            },throwable -> Timber.tag(TAG).e("Error on get msg: %s", throwable.getMessage())));
    }

    @Override
    public void onExit() {
        handlerExit();
//        view.onFinish(ConstantApp.FINISH_ACTIVITY_CODE);
    }

    private void handlerExit() {
        interactor.exit()
            .subscribe(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    Timber.tag(TAG).e("onExit() onComplete");
                }

                @Override
                public void onError(Throwable e) {
                    Timber.tag(TAG).e("onExit() onError - %s", e.getMessage());
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
