package com.nekoid.smektuber.screen.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.app.ButtonTryAgain;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifNoInternet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifNoInternet extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private String mParam2;

    private ButtonTryAgain buttonTryAgain;

    private Button noInternet;

    public NotifNoInternet(ButtonTryAgain tryAgain) {
        this.buttonTryAgain = tryAgain;
    }

    public NotifNoInternet() {}

    public static NotifNoInternet newInstance(String param1, String param2) {
        NotifNoInternet fragment = new NotifNoInternet();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notif_no_internet, container, false);
        if (buttonTryAgain != null) {
            noInternet = view.findViewById(R.id.BtnNotifNoInternet);
            noInternet.setOnClickListener(v -> buttonTryAgain.onClickTryAgain(v));
        }
        return view;
    }
}