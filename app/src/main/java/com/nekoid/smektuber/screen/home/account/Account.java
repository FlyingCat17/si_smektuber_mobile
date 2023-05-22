package com.nekoid.smektuber.screen.home.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseFragment;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.PpdbModel;
import com.nekoid.smektuber.models.UserModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.screen.auth.Login;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    boolean withAnimation = true;
    private String mParam1;
    private String mParam2;
    private TextView tvFullName, tvUsername;
    ShimmerFrameLayout shimmerFrameLayout;
    private ShapeableImageView imageView;
    RelativeLayout layoutProfil;
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
        startShimmer();
        tvFullName = view.findViewById(R.id.FullnameAccount);
        tvUsername = view.findViewById(R.id.UsernameAccount);
        imageView = view.findViewById(R.id.ImageProfil);
        shimmerFrameLayout = view.findViewById(R.id.ShimmerAccount);
        layoutProfil = view.findViewById(R.id.LayoutProfil);
        Button btnUpdate = view.findViewById(R.id.ButtonUbahProfil);
        Button btnLogout = view.findViewById(R.id.ButtonKeluarAkun);
        btnUpdate.setOnClickListener(v -> {
            Navigator.of(getActivity()).push(ChangeDataAccount.class);
        });
        btnLogout.setOnClickListener(v -> {
            Http.post(Endpoint.LOGOUT.getUrl(), PublicApi.getHeaders(), this::doLogout);
        });
        init();
        if (State.userModel != null) {
            withAnimation = false;
            userModel = State.userModel;
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
        layoutProfil.setVisibility(View.VISIBLE);
        if (withAnimation) layoutProfil.setAnimation(Utils.animation());
    }

    private void setModelToView() {
        tvFullName.setText(userModel.name);
        tvUsername.setText(userModel.username);
        stopShimmer();
    }
    private void openRequest() {
        Http.get(Endpoint.PPDB.getUrl(), PublicApi.getHeaders(), this::onResponse);
    }

    private void onResponse(Response response) {
        if (response.statusCode != 200) {
            return;
        }
        try {
            JSONObject body = new JSONObject(response.body.toString());
            userModel = UserModel.fromJson(body.getJSONObject("data"));
            State.userModel = userModel;
            loadModel();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadModel() {
        if (userModel != null && userModel.avatar != null && !userModel.avatar.isEmpty() && !userModel.avatar.equals("null")) {
            Http.loadImage(userModel.avatar, imageView, this::setModelToView);
            return;
        }
        setModelToView();
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
        setModelToView();
    }
}
