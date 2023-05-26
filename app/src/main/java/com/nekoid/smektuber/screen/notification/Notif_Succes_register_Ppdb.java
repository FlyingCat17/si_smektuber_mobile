package com.nekoid.smektuber.screen.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nekoid.smektuber.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notif_Succes_register_Ppdb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notif_Succes_register_Ppdb extends Fragment {

    private Button btnback;

    public Notif_Succes_register_Ppdb() {
        // Required empty public constructor
    }

    public static Notif_Succes_register_Ppdb newInstance() {
        return new Notif_Succes_register_Ppdb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notif__succes_register__ppdb, container, false);
        btnback = view.findViewById(R.id.BtnSuccesRegisterPPDB);

        btnback.setOnClickListener(v-> {
            getActivity().onBackPressed();
        });

        return view;
    }
}
