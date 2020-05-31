package com.example.androidchatclient.presentation.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.databinding.FragmentChatBinding;
import com.example.androidchatclient.databinding.FragmentLoginBinding;
import com.example.androidchatclient.presentation.route.IRouter;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class ChatFragment extends DaggerFragment implements IChatPresenter.View {
    @Inject
    IRouter router;
    @Inject
    IChatPresenter.Presenter presenter;
    @Inject
    IRepositorySocket repository;
    private FragmentChatBinding binding;

    private static final String TAG = "ChatFragment";
    private static ChatFragment fragm;

    @Inject
    public ChatFragment() {}

    public static ChatFragment newInstance(String param1, String param2) {
        fragm = new ChatFragment();
        return fragm;
    }

    public static ChatFragment getInstance() {
        return fragm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        if (binding != null && presenter != null) binding.setEvent(presenter);
        else Timber.tag(TAG).e("Null object binding");
        presenter.init();
        return binding.getRoot();
    }

    @Override
    public String getText() {
        return String.valueOf(binding.msgOutput.getText());
    }

    @Override
    public String getChatText() {
        return String.valueOf(binding.msgOutput.getText())  ;
    }

    @Override
    public void onFinish(int code) {
        if (code == ConstantApp.FINISH_ACTIVITY_CODE) {
            Timber.tag(TAG).e("Finish main activity");
            Objects.requireNonNull(getActivity()).finish();
        } else {
            Timber.tag(TAG).e("Invalid code to finish main activity");
        }
    }

    @Override
    public void setText(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
