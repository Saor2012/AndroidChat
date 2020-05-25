package com.example.androidchatclient.presentation.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends DaggerFragment implements ILoginPresenter.View {
    @Inject
    IRouter router;
    @Inject
    ILoginPresenter.Presenter presenter;
    @Inject
    IRepositorySocket repository;
    private FragmentLoginBinding binding;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "LoginFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static LoginFragment fragm;

    @Inject
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragm = fragment;
        return fragment;
    }

    public static LoginFragment getInstance() {
        return fragm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        binding = DataBindingUtil.bind(view);
        if (binding != null && presenter != null) binding.setEvent(presenter);
        else Timber.tag("LoginFragment").e("Null object binding");
        return view;
    }

    @Override
    public void login() {
        try {
            router.transaction(ConstantApp.MY_MAIN_RES, null, "", false);
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
