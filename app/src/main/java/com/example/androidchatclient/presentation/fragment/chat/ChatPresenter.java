package com.example.androidchatclient.presentation.fragment.chat;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.data.model.FragmentModel;
import com.example.androidchatclient.data.model.Message;
import com.example.androidchatclient.domain.IMainInteractor;
import com.example.androidchatclient.presentation.base.BaseFragment;
import com.example.androidchatclient.presentation.route.IRouter;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import timber.log.Timber;

public class ChatPresenter implements IChatPresenter.Presenter  {
    @Inject
    IChatPresenter.View view;
    @Inject
    IRouter router;
    @Inject
    IMainInteractor interactor;
    private CompositeDisposable disposable;
    private static final String TAG = "ChatPresenter";
    private String userName;

    @Inject
    public ChatPresenter() {}
//    @Override
//    public void init(FragmentModel viewModel) {
////        model = viewModel;
//        Timber.tag(TAG).e("Is connect: %s", interactor.getIsConnect());
//        initListener();
//    }
    @Override
    public void init(String login) {
        Timber.tag(TAG).e("Is connect: %s", interactor.getIsConnect());
        this.userName = login;
        initListener();
    }

    @Override
    public void connect(String name) {
        //TODO NOT USED
    }

    @Override
    public Observable<String> read() {
        //TODO NOT USED
        return null;
    }

    @Override
    public void onSend() {
        handlerSend(view.getText());
    }

    private void handlerSend(String message) {
        if (!message.equals("") && message.length() > 0) {
            if (message.length() < 151) {
                view.setNullSendMsg();
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
                view.toast(ConstantApp.WARNING_SEND_TEXT.concat("Message length: ").concat(String.valueOf(message.length())));
                Timber.tag(TAG).e(ConstantApp.WARNING_SEND);
            }
        } else {
            Timber.tag(TAG).e("Message is empty: %s", message);
        }
    }

    private void initListener() {
        if (disposable == null) { disposable = new CompositeDisposable(); }
        listener();
        Timber.tag(TAG).e("Start listening for new messagers.");
    }

    private void listener() {
        Timber.tag(TAG).e("Get chat: %s", view.getChatText());
//        AtomicReference<String> textarea = new AtomicReference<>("");
        disposable.add(interactor.read()
            .subscribe(v -> {
                Timber.tag(TAG).e("New message: %s", v);
                String[] text = parseResponse(v);
                Message response = new Message(text[2], text[3], text[1], text[4]);
                response.setOwner(text[5]);
                view.updateAdapter(response);
//                view.setText(textarea.get());
            }, throwable -> Timber.tag(TAG).e("Error on get msg: %s", throwable.getMessage()))); //<- Помилка при отримані повідомлення
    }

    private String[] parseResponse(String input) {
        String[] output = null;
        String[] text = input.split(", "), text2, text3;
        String cmd, owner;
        text2 = text[1].split(" login ");
        if (text2[1].contains("User join")) {
            text3 = text2[1].split(" User ");
            cmd = "join";
            Timber.tag(TAG).e("Type message: login");
        } else if (text2[1].contains("User exit")) {
            text3 = text2[1].split(" User exit");
            cmd = "exit";
            Timber.tag(TAG).e("Type message: exit");
        } else if (text2[1].contains("tell msg")) {
            text3 = text2[1].split(" tell msg ");
            cmd = "msg";
            Timber.tag(TAG).e("Type message: msg");
        } else {
            text3 = new String[] {"undefined", "undefined"};
            cmd = "undefind";
            Timber.tag(TAG).e("Type message: undefined");
        }

        if (text2[0].equals(userName)) {
            owner = "this";
        } else {
            owner = "any";
        }

        output = new String[] {text[0], text2[0], text3[0], text3[1], cmd, owner};
//                Message item = new Message();
//                item.setLogin(v);
//                item.setDate(v);
//                item.setMsg(v);
//                item.setCmd(v);

//                textarea.set(String.valueOf(text));
//                textarea.set(textarea.get().concat(v).concat("\n"));
        Timber.tag(TAG).e("Split message: day %s / date %s / login %s / message %s / command %s", output[0], output[1], output[2], output[3], output[4]);
        return output;
    }

    @Override
    public void onExit() {
        handlerExit();
//        onTransaction(null, "login_fragment"); //Обробка події закриття без виходу з додатку
    }

    private void onTransaction(BaseModel obj, String str) {
        try {
            router.transaction(ConstantApp.MY_FRAGMENT_LOGIN, null, str, false);
        } catch (Throwable throwable) {
            Timber.tag(TAG).e("Error transaction between fragment and activity to login");
            throwable.printStackTrace();
        }
    }

    @Override
    public void onLongExit() {
        handlerExit();
        view.onFinish(ConstantApp.FINISH_ACTIVITY_CODE);
    }

    @Override
    public void onRestart() {
//        if (interactor.getIsConnect() == 0) {
//            Timber.tag(TAG).e("onResume(): transaction to login");
//            onTransaction(null, "");
//        }
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
                    Timber.tag(TAG).e("onExit(), Error: %s", e.getMessage());
                }
            });
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {
        if (view != null) view = null;
    }
}
