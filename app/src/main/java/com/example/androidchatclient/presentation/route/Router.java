package com.example.androidchatclient.presentation.route;


import com.example.androidchatclient.ConstantApp;
import com.example.androidchatclient.R;
import com.example.androidchatclient.data.model.BaseModel;
import com.example.androidchatclient.presentation.base.ActivityView;
import com.example.androidchatclient.presentation.fragment.chat.ChatFragment;
import com.example.androidchatclient.presentation.fragment.login.ILoginPresenter;
import com.example.androidchatclient.presentation.fragment.login.LoginFragment;

import javax.inject.Inject;

public class Router implements IRouter{
    private ActivityView view;
    @Inject ILoginPresenter.Presenter loginfragment;

    @Inject
    public Router() {
    }

    @Override
    public void onStart(ActivityView view) {
        this.view = view;
    }

    @Override
    public void onStop() {
        if (this.view != null)
            view = null;
    }

    @Override
    public void transaction(String routeFragment, BaseModel object, String jsonObject, boolean isBackStack) throws Throwable {
        switch (routeFragment) {
            case ConstantApp.MY_FRAGMENT_LOGIN: view.transitionFragment(LoginFragment.newInstance(jsonObject, null), getResourseLayout(ConstantApp.MAIN_RES));
                break;
            case ConstantApp.MY_FRAGMENT_CHAT : view.transitionFragment(ChatFragment.newInstance(jsonObject, null), getResourseLayout(ConstantApp.MAIN_RES));
                break;
//            case ConstantApp.MY_MAIN_RES : view.closeFragment(LoginFragment.getInstance());
//                break;
            //New fragments
            default: //handle exception
                throw new Throwable("Error ?");

        }
    }

    private int getResourseLayout(int resLayout) {
        switch (resLayout) {
            case ConstantApp.MAIN_RES : return R.id.content;
            default: new Throwable("Error activty");
                return 0;
        }
    }

    @Override
    public void back() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void titleBar(String value) {

    }
}
