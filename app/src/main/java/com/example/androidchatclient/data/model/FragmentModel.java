package com.example.androidchatclient.data.model;

public class FragmentModel extends BaseModel {
    public FragmentModel(String name, String connect, String date) {
        setIsComnect(connect);
        setName(name);
        setLastDate(date);
    }
}
