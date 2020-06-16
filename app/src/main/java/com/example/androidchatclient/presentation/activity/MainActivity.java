package com.example.androidchatclient.presentation.activity;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.databinding.ActivityMainBinding;
import com.example.androidchatclient.presentation.base.BaseActivity;
import com.example.androidchatclient.presentation.base.BasePresenter;
import com.example.androidchatclient.presentation.route.IRouter;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatDialogFragment;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements IMainPresenter.View {
    private static final String TAG = "MainActivity";
    @Inject
    IMainPresenter.Presenter presenter;
    @Inject
    IRouter router;

    @Override
    protected void initView() {
        router.onStart(this);
        try {
            presenter.init();
        } catch (Throwable throwable) {
            Timber.tag(TAG).e("Error: %s", throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStartView() {
        getBinding().setEvent(presenter);
    }

    @Override
    protected void onDestroyView() {
        if(presenter != null) presenter = null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public <T> void transactionActivity(Class<?> activity, T object) {
        //TODO NOT USED
    }

    @Override
    public void transactionActivity(Class<?> activity) {
        super.transactionActivity(activity, true);
    }

    @Override
    public void transitionFragment(DaggerFragment fragment, int container) {
        super.transactionFragmentNoBackStack(fragment, container);
    }

    @Override
    public void transitionFragmentDialog(DaggerAppCompatDialogFragment fragment, int container) {
        //TODO NOT USED
    }

    @Override
    public void closeFragment(DaggerFragment fragment) {
        super.closeFragment(fragment);
    }

    @Override
    public void back() {

    }

    @Override
    public void restart() {

    }

//    @Override
//    public void onFinish(int code) {
//        if (code == ConstantApp.FINISH_ACTIVITY_CODE) {
//            Timber.tag(TAG).e("Finish main activity");
//            finish();
//        } else {
//            Timber.tag(TAG).e("Invalid code to finish main activity");
//        }
//    }
}
