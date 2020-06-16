package com.example.androidchatclient.presentation.fragment.chat.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.data.model.Message;
import com.example.androidchatclient.databinding.CharrvItemBinding;
import com.example.androidchatclient.presentation.fragment.chat.IChatPresenter;

import javax.inject.Inject;

import timber.log.Timber;

public class ViewHolder extends RecyclerView.ViewHolder {
    @Inject
    IChatPresenter.Presenter presenter;
    private static final String TAG = "ViewHolder";
    private CharrvItemBinding binding;

    public ViewHolder(@NonNull View itemView, IChatPresenter.Presenter presenter) {
        super(itemView);
        if (this.presenter == null && presenter != null) this.presenter = presenter;
        binding = DataBindingUtil.bind(itemView);
        if (binding != null) binding.setEvent(presenter);
    }

    public void bind(Message item, int position) {
        if (item != null) {
            if (item.getOwner().equals("this")) {
                binding.itemLogin.setTextColor(binding.getRoot().getResources().getColor(R.color.colorOwnerLogin));
            }
            switch (parseMsgText(item)) {
                case 1: binding.itemLogin.setText(item.getLogin()); //login
                    binding.itemDate.setText(item.getDate());
                    binding.itemMessage.setText(item.getLogin().concat(" join"));
                    break;
                case 2: binding.itemLogin.setText(item.getLogin()); //exit
                    binding.itemDate.setText(item.getDate());
                    binding.itemMessage.setText(item.getLogin().concat(" leave"));
                    break;
                default: binding.itemLogin.setText(item.getLogin()); //msg
                    binding.itemDate.setText(item.getDate());
                    binding.itemMessage.setText(item.getMsg());
                    break;
            }
        }
    }

    private int parseMsgText(Message model) {
        int res = -1; //Перевірка чи вхід, повідомлення, вихід
        if (model.getCmd().concat(":").equals("join:")) {
            res = 1;
//            Timber.tag(TAG).e("Command join");
        } else if (model.getCmd().concat(":").equals(ConstantApp.TAG_MESSAGE)) {
            res = 0;
//            Timber.tag(TAG).e("Command msg");
        } else if (model.getCmd().concat(":").equals(ConstantApp.TAG_EXIT)) {
            res = 2;
//            Timber.tag(TAG).e("Command exit");
        } else {
            Timber.tag(TAG).e("Undefined command %s, result %s", model.getCmd(), res);
        }
        Timber.tag(TAG).e("Select case %s", res);
        return res;
    }
}
