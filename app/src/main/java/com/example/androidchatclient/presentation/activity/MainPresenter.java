package com.example.androidchatclient.presentation.activity;

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

public class MainPresenter implements IMainPresenter.Presenter {
    @Inject
    IMainPresenter.View view;
    @Inject
    IRouter router;
//    @Inject
//    IMainInteractor interactor;
    private CompositeDisposable disposable;

    @Inject
    public MainPresenter() {}

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (view != null) view = null;
    }

    @Override
    public void init() throws Throwable {
        router.transaction(ConstantApp.MY_FRAGMENT_LOGIN, null, "login_fragment", false);
    }

//    @Override
//    public Completable connect(String name) {
//        return null;
//    }

//    @Override
//    public void onExit() {
//        handlerExit();
//        view.onFinish(ConstantApp.FINISH_ACTIVITY_CODE);
//    }

//    private void handlerExit() {
//        if (interactor.getIsConnect() == 1) {
//            interactor.exit()
//                .subscribe(new DisposableCompletableObserver() {
//                    @Override
//                    public void onComplete() {
//                        Timber.e("onExit() onComplete");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e("onExit() onError - %s", e.getMessage());
//                    }
//                });
//        }
//    }
}
