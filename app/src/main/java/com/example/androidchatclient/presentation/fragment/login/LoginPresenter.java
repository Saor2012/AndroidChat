package com.example.androidchatclient.presentation.fragment.login;

import android.annotation.SuppressLint;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.data.model.FragmentModel;
import com.example.androidchatclient.domain.IMainInteractor;
import com.example.androidchatclient.presentation.route.IRouter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
//    @SuppressLint("SimpleDateFormat")
//    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantApp.TIME_TEMPLETE);
//    FragmentModel model = new FragmentModel(ConstantApp.MY_FRAGMENT_LOGIN, "0", Objects.requireNonNull(simpleDateFormat).format(new Date()));
    private FragmentModel model;

    @Inject
    public LoginPresenter() {}

    @Override
    public void onStart() {}

    @Override
    public void onStop() {
        if (view != null) view = null;
    }

    @Override
    public void init(FragmentModel viewModel) {
        model = viewModel;
    }

    @Override
    public void init() {
        //Перевірка мережі інтернет???
    }

    @Override
    public void onLogin() {
        String name = view.getName();
        Timber.tag(TAG).e("Select user login: %s", name);
        if (!name.isEmpty()) {
            connect(name);
            Timber.tag(TAG).e("Connect state %s", interactor.getIsConnect());
        } else {
            view.setResponse("Fill login field");
            Timber.tag(TAG).e("Response: Fill login field");
        }
    }

    @Override
    public void onExit() {
        interactor.exit()
            .subscribe(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    if (interactor.getIsConnect() == 0) {
                        Timber.tag(TAG).e("Success exit, finish app");
                        view.onFinish(ConstantApp.FINISH_ACTIVITY_CODE);
                    } else Timber.tag(TAG).e("Server maybe still work, try again");
                }

                @Override
                public void onError(Throwable e) {
                    Timber.tag(TAG).e("Error: %s", e.getMessage());
                }
            });
    }

    private void connect(String name) {
        if (!name.equals("") && name.length() > 2) {
            interactor.connect(ConstantApp.TAG_LOGIN.concat(name), model)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.e("Success connect (%s)", name);
                        Timber.tag(TAG).e("Is connect: %s", interactor.getIsConnect());
                        try {
                            router.transaction(ConstantApp.MY_FRAGMENT_CHAT, interactor.getModel(), name, false);
                        } catch (Throwable throwable) {
                            Timber.tag(TAG).e("Error transaction between fragment and activity to chat. Error: %s", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag(TAG).e("Error: %s. Disconnect", e.getMessage());
                        view.toast("Disconnect. Try again");
                    }
                });
        }
    }

}

