package com.example.androidchatclient.presentation.route;

import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.presentation.base.ActivityView;
import com.example.androidchatclient.presentation.base.BaseActivity;

public interface IRouter{
    void onStart(ActivityView view);
    void onStop();
    void transaction(String routeFragment, BaseModel object, String jsonObject, boolean isBackStack) throws Throwable;
    void back();
    void restart();
    void titleBar(String value);

}
