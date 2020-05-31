package com.example.androidchatclient.presentation.fragment.login;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.databinding.FragmentLoginBinding;
import com.example.androidchatclient.presentation.route.IRouter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class LoginFragment extends DaggerFragment implements ILoginPresenter.View {
    @Inject
    IRouter router;
    @Inject
    ILoginPresenter.Presenter presenter;
    @Inject
    IRepositorySocket repository;
    private FragmentLoginBinding binding;

    private static final String TAG = "LoginFragment";
    private static LoginFragment fragm;

    @Inject
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        fragm = new LoginFragment();
        return fragm;
    }

    public static LoginFragment getInstance() {
        return fragm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        if (binding != null && presenter != null) binding.setEvent(presenter);
        else Timber.tag(TAG).e("Null object binding");
        presenter.init();
        return binding.getRoot();
    }

    @Override
    public void login() {
        try {
            router.transaction(ConstantApp.MY_FRAGMENT_CHAT, null, "", false);
        } catch (Throwable throwable) {
            Timber.tag(TAG).e("Error transaction between fragment and activity");
            throwable.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return String.valueOf(binding.loginEditText);
    }
}
