package com.example.androidchatclient.presentation.fragment.login;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.data.model.FragmentModel;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.databinding.FragmentLoginBinding;
import com.example.androidchatclient.presentation.base.BaseFragment;
import com.example.androidchatclient.presentation.base.BasePresenter;
import com.example.androidchatclient.presentation.route.IRouter;

import java.text.SimpleDateFormat;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> implements ILoginPresenter.View {
    @Inject
    ILoginPresenter.Presenter presenter;
    @Inject
    IRepositorySocket repository;
//    private FragmentLoginBinding binding;
    private static FragmentModel model;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantApp.TIME_TEMPLETE);

    private static final String TAG = "LoginFragment";

    @Inject
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, BaseModel newModel) {
//        if (newModel == null) {
//            model.setName(ConstantApp.MY_FRAGMENT_LOGIN);
//            model.setIsComnect("0");
//            model.setLastDate();
//        }
        return new LoginFragment();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
//        if (binding != null && presenter != null) binding.setEvent(presenter);
//        else Timber.tag(TAG).e("Null object binding");
//        presenter.init();
//        return binding.getRoot();
//    }

    @Override
    protected void initView() {
        if (presenter != null) presenter.init(model);
        else Timber.tag(TAG).e("Null object presenter");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onStartFragmentView() {
        if (getBinding() != null && presenter != null) getBinding().setEvent(presenter);
        else Timber.tag(TAG).e("Null object binding");
    }

    @Override
    protected void onDestroyFragmentView() {}

    @Override
    protected void onDetachFragment() {
        if (presenter != null) {
            presenter = null;
        } else Timber.tag(TAG).e("Presenter have null object");
    }

    @Override
    protected void onStopFragment() {

    }

    @Override
    protected void onResumeFragment() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void toast(String message) {
        if (!message.equals("") && message.length() > 1) {
            Toast.makeText(getBinding().getRoot().getContext(), message, Toast.LENGTH_SHORT).show();
            Timber.tag(TAG).e("Toast message: %s", message);
        } else {
            Timber.tag(TAG).e("Toast message invalid: %s", message);
        }
    }

    @Override
    public void onFinish(int code) {
        if (code == ConstantApp.FINISH_ACTIVITY_CODE) {
            Timber.tag(TAG).e("Finish main activity app");
            Objects.requireNonNull(getActivity()).finish();
        } else {
            Timber.tag(TAG).e("Invalid code to finish main activity");
        }
    }

    @Override
    public String getName() {
        return String.valueOf(getBinding().loginEditText.getText());
    }

    @Override
    public void setResponse(String text) {
        getBinding().loginTextView.setText(text);
    }
}
