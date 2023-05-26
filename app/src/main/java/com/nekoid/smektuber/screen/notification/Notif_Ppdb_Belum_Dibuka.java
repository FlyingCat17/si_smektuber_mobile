package com.nekoid.smektuber.screen.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.screen.home.ppdb.Ppdb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notif_Ppdb_Belum_Dibuka#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notif_Ppdb_Belum_Dibuka extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button BtnData;
    public Notif_Ppdb_Belum_Dibuka() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notif_Ppdb_Belum_Dibuka.
     */
    // TODO: Rename and change types and number of parameters
    public static Notif_Ppdb_Belum_Dibuka newInstance(String param1, String param2) {
        Notif_Ppdb_Belum_Dibuka fragment = new Notif_Ppdb_Belum_Dibuka();
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
        View view = inflater.inflate(R.layout.fragment_notif__ppdb__belum__dibuka, container, false);
        BtnData = view.findViewById(R.id.BtnPPDBBelumDibuka);
        BtnData.setOnClickListener(v -> {
            replaceFragment(new Ppdb());
        });
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.r, fragment)
                    .commit();
        }
    }
}