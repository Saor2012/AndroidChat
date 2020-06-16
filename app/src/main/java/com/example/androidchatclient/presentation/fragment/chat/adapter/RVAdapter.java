package com.example.androidchatclient.presentation.fragment.chat.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidchatclient.R;
import com.example.androidchatclient.data.model.Message;
import com.example.androidchatclient.presentation.fragment.chat.IChatPresenter;

import java.util.List;

import javax.inject.Inject;

public class RVAdapter extends RecyclerView.Adapter<ViewHolder> implements IRVAdapter {
    @Inject
    IChatPresenter.Presenter presenter;
    private List<Message> list;

    @Inject
    public RVAdapter() {}

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    @Override
    public void addList(Message item) {
        if (list != null) list.add(item);
    }

    @Override
    public int getListSize() {
        return getItemCount();
    }

    public RVAdapter(IChatPresenter.Presenter presenter, List<Message> list) {
        if (this.presenter == null && presenter != null) this.presenter = presenter;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.charrv_item, parent, false), presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size() != 0) holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
