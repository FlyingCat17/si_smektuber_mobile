package com.nekoid.smektuber.screen.home.ppdb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.PpdbModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ppdb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ppdb extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ShimmerFrameLayout shimmerFrameLayout;

    PpdbModel ppdbModel;

    ShapeableImageView posterImage;

    TextView description;

    ScrollView layoutPpdb;

    boolean withAnimation = true;

    public Ppdb() {
        // Required empty public constructor
    }

    public static Ppdb newInstance(String param1, String param2) {
        Ppdb fragment = new Ppdb();
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
        View view = inflater.inflate(R.layout.fragment_ppdb, container, false);
        setVar(view);
        startShimmer();
        if (State.PpdbModel != null) {
            withAnimation = false;
            ppdbModel = State.PpdbModel;
            setModelToView();
            openRequest();
        } else {
            withAnimation = true;
            openRequest();
        }
        // Inflate the layout for this fragment
        return view;
    }

    private void startShimmer() {

    }

    public void stopShimmer() {
        shimmerFrameLayout.setVisibility(View.GONE);
        layoutPpdb.setVisibility(View.VISIBLE);
        if (withAnimation) layoutPpdb.setAnimation(Utils.animation());
    }

    private void setVar(View view) {
        posterImage = view.findViewById(R.id.ImagePPDB);
        description = view.findViewById(R.id.TxtPPDB);
        shimmerFrameLayout = view.findViewById(R.id.ShimmerInformasiPPDB);
        layoutPpdb = view.findViewById(R.id.layoutPpdb);

        Button btn = view.findViewById(R.id.BtnDaftarSiswa);
        btn.setOnClickListener(v -> Navigator.of(getActivity()).push(DaftarPPDB.class));
    }

    private void openRequest() {
        Http.get(Endpoint.PPDB.getUrl(), PublicApi.getHeaders(), this::onResponse);
    }

    private void loadModel() {
        if (ppdbModel != null && ppdbModel.poster != null && !ppdbModel.poster.isEmpty() && !ppdbModel.poster.equals("null")) {
            Http.loadImage(ppdbModel.poster, posterImage, this::setModelToView);
            return;
        }
        setModelToView();
    }

    private void setModelToView() {
        description.setText(ppdbModel.description);
        stopShimmer();
    }

    private void onResponse(Response response) {
        if (response.statusCode != 200) {
            return;
        }
        try {
            JSONObject body = new JSONObject(response.body.toString());
            ppdbModel = PpdbModel.fromJson(body.getJSONObject("data"));
            State.PpdbModel = ppdbModel;
            loadModel();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}