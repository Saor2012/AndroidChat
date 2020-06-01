package com.example.androidchatclient.presentation.fragment.login;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.domain.IMainInteractor;
import com.example.androidchatclient.presentation.route.IRouter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import timber.log.Timber;

public class LoginPresenter implements ILoginPresenter.Presenter {
    @Inject
    ILoginPresenter.View view;
    @Inject
    IMainInteractor interactor;
    @Inject
    IRouter router;
    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter() {}

    @Override
    public void onStart() {}

    @Override
    public void onStop() {
        if (view != null) view = null;
    }

    @Override
    public void init() {

    }

    @Override
    public void onLogin() {
        connect(view.getName());
    }

    private void connect(String name) {
        if (!name.equals("") && name.length() > 2) {
            interactor.connect(ConstantApp.TAG_LOGIN.concat(name))
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.e("Success connect");
                        view.login();
//                        router.transaction(ConstantApp.MY_MAIN_RES, null, "", false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        Timber.e("Disconnect");
//                        if (e.getMessage().equals("out miss object")) {
                              //Підключити знову?
//                        }
                    }
                });
        }
    }
}
