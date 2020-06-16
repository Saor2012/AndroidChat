package com.example.androidchatclient.presentation.fragment.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.data.model.FragmentModel;
import com.example.androidchatclient.data.model.Message;
import com.example.androidchatclient.data.websocket.IRepositorySocket;
import com.example.androidchatclient.databinding.FragmentChatBinding;
import com.example.androidchatclient.databinding.FragmentLoginBinding;
import com.example.androidchatclient.presentation.base.BaseFragment;
import com.example.androidchatclient.presentation.base.BasePresenter;
import com.example.androidchatclient.presentation.fragment.chat.adapter.IRVAdapter;
import com.example.androidchatclient.presentation.fragment.chat.adapter.RVAdapter;
import com.example.androidchatclient.presentation.route.IRouter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class ChatFragment extends BaseFragment<FragmentChatBinding> implements IChatPresenter.View {
    @Inject
    IRouter router;
    @Inject
    IChatPresenter.Presenter presenter;
    @Inject
    RVAdapter adapter;
    @Inject
    IRepositorySocket repository;
    private static final String TAG = "ChatFragment";
    private static String userName;
//    @SuppressLint("SimpleDateFormat")
//    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantApp.TIME_TEMPLETE);

    @Inject
    public ChatFragment() {}

    public static ChatFragment newInstance(String login, BaseModel newModel) {
//        model.setName(newModel.getName().equals(ConstantApp.MY_FRAGMENT_LOGIN) ? ConstantApp.MY_FRAGMENT_CHAT : "undefind");
//        model.setIsComnect(newModel.getIsComnect());
        userName = login;
        return new ChatFragment();
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
//        if (binding != null && presenter != null) binding.setEvent(presenter);
//        else Timber.tag(TAG).e("Null object binding");
//        presenter.init();
//        binding.btnExit.setOnLongClickListener(v -> {
//            presenter.onLongExit();
//            return false;
//        });
//        return binding.getRoot();
//    }s

    @Override
    protected void initView() {
//        model.setLastDate(Objects.requireNonNull(simpleDateFormat).format(new Date()));
//        if (presenter != null) presenter.init(model);
//        else Timber.tag(TAG).e("Null object presenter");
        if (presenter != null) presenter.init(userName);
        else Timber.tag(TAG).e("Null object presenter");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void onStartFragmentView() {
        if (getBinding() != null && presenter != null) {
            getBinding().setEvent(presenter);
            getBinding().btnExit.setOnLongClickListener(v -> {
                presenter.onLongExit();
                return false;
            });
            initAdapter();
        } else Timber.tag(TAG).e("Null object binding");
    }

    @Override
    protected void onDestroyFragmentView() {
        if (presenter != null) {
            presenter.onExit();
        } else Timber.tag(TAG).e("Presenter have null object, error to disconnect");
    }

    @Override
    protected void onDetachFragment() {
        if (presenter != null) {
            presenter = null;
        } else Timber.tag(TAG).e("Presenter have null object");
    }

    @Override
    protected void onStopFragment() {
        presenter.onExit();
    }

    @Override
    protected void onResumeFragment() {
        presenter.onRestart(); // Після виходу та повернення до додатку - перехід до реїстрації
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
    public String getText() {
        return String.valueOf(getBinding().msgOutput.getText());
    }

    @Override
    public void setNullSendMsg() {
        if (!(String.valueOf(getBinding().msgOutput.getText())).isEmpty()) {
            getBinding().msgOutput.setText("");
        }
    }

    @Override
    public String getChatText() {
        return null;
//      return String.valueOf(getBinding().textInput.getText());
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
    public void updateAdapter(Message model) {
        if (adapter != null) {
            adapter.addList(model);
            getBinding().rvInput.setAdapter(adapter);
        } else {
          Timber.tag(TAG).e("Null object adapter");
          initAdapter();
        }
    }

    @Override
    public void updateAdapter(List<Message> list) {
        if (adapter != null) {
            adapter.setList(list);
            getBinding().rvInput.setAdapter(adapter);
        } else {
            Timber.tag(TAG).e("Null object adapter");
            initAdapter();
        }
    }

    public void initAdapter() {
        if (presenter != null) {
            getBinding().rvInput.setLayoutManager(new LinearLayoutManager(getBinding().getRoot().getContext(), LinearLayoutManager.VERTICAL, false));
            if (adapter != null) {
                if (adapter.getList() == null) {
                    List<Message> list = new ArrayList<Message>();
                    adapter.setList(list);
                }
                getBinding().rvInput.setAdapter(adapter);
            }
        }
    }

    @Override
    public void setText(Message message) {
        if (message != null) {
//            getBinding().textInput.setText(message);
            //Rv set item
            updateAdapter(message);
            Timber.tag(TAG).e("Set up new message: %s", message.toString());
//            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            Timber.tag(TAG).e("New message is empty: %s", message);
        }
    }
}
