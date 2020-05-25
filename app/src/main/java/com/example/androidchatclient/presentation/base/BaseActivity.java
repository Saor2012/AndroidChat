package com.example.androidchatclient.presentation.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.androidchatclient.R;
import com.example.androidchatclient.presentation.route.IRouter;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.DaggerAppCompatDialogFragment;
import dagger.android.support.DaggerFragment;
import io.reactivex.annotations.Nullable;

public abstract class BaseActivity<Binding extends ViewDataBinding> extends DaggerAppCompatActivity {
    private Binding binding;
    @Inject
    IRouter router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        initView();
    }

    protected abstract void initView();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onStart() {
        super.onStart();
        onStartView();
    }

    protected Binding getBinding(){
        return binding;
    }

    @Override
    public void onDestroy() {
        if (getPresenter() != null) {
            onDestroyView();
            getPresenter().onStop();
        }
        super.onDestroy();
    }

    protected abstract void onStartView();

    protected abstract void onDestroyView();

    protected abstract BasePresenter getPresenter();

    protected void transactionFragmentNoBackStack(DaggerFragment fragment, int container) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_center, R.anim.fragment_exit, R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
                .replace(container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    protected void transitionFragmentDialog(DaggerAppCompatDialogFragment fragment, int container) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    protected void closeFragmentDialog(DaggerAppCompatDialogFragment fragment) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }

    protected void closeFragment(DaggerFragment fragment) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }

    protected void removeAllFragment() {
        if ((getSupportFragmentManager() != null) && (getSupportFragmentManager().findFragmentById(R.id.main_content)) != null) {
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.main_content)))
                    .commit();
        }
    }

    protected void transactionFragmentWithBackStack(DaggerFragment fragment, int container) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    protected void transactionActivity(Class<?> activity, boolean cycleFinish) {
        if (activity != null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
            if (cycleFinish) {
                this.finish();
            }
        }
    }

    protected <T>void transactionActivity(Class<?> activity, boolean cycleFinish, T object) {
        if (activity != null) {
            Intent intent = new Intent(this, activity);
            if (object != null) {
                //Data model
            }
            startActivity(intent);
            if (cycleFinish) {
                this.finish();
            }
        }
    }

    protected void toast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
