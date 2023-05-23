package com.nekoid.smektuber.screen.home.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseFragment;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.models.UserModel;
import com.nekoid.smektuber.network.Http;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView tvFullName, tvUsername;

    private ShapeableImageView imageView;

    private UserModel userModel;

    public Account() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        tvFullName = view.findViewById(R.id.FullnameAccount);
        tvUsername = view.findViewById(R.id.UsernameAccount);
        Button btnUpdate = view.findViewById(R.id.ButtonUbahProfil);
        Button btnLogout = view.findViewById(R.id.ButtonKeluarAkun);
        btnUpdate.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(ChangeDataAccount.class);
        });
        btnLogout.setOnClickListener(v -> {
            Http.post(Endpoint.LOGOUT.getUrl(), PublicApi.getHeaders(), this::doLogout);
        });
        init();
        return view;
    }

    private void init() {
        // Get user data from State
        userModel = State.getUserModel();

        // Set user data to views
        tvFullName.setText(userModel.name);
        tvUsername.setText(userModel.username);
        if (userModel.avatar != null && !userModel.avatar.isEmpty()) {
            if (userModel.avatar.startsWith("http://") || userModel.avatar.startsWith("https://"))
                Http.loadImage(userModel.avatar, imageView);
        }
    }
}
