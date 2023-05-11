package com.nekoid.smektuber.screen.home.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.screen.auth.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView tvFullname, tvUsername;

    public Account() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_account, container, false );
        // init
        tvFullname = view.findViewById( R.id.FullnameAccount );
        tvUsername = view.findViewById( R.id.UsernameAccount );
        Button btnUpdate = view.findViewById( R.id.ButtonUbahProfil );
        Button btnLogout = view.findViewById( R.id.ButtonKeluarAkun );
        ImageView ImageProfil1 = view.findViewById(R.id.ImageProfilAccount);


        ImageProfil1.setOnClickListener(v -> {
            Navigator.of( getActivity()).push(ViewZoomImage.class);
        });
        btnUpdate.setOnClickListener( v -> {
            Navigator.of( getActivity() ).push( ChangeDataAccount.class );
        } );
        btnLogout.setOnClickListener( v -> {
//            Navigator.of(getActivity()).push(Login.class);
            doLogout();
        } );
        init();
        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        // Get user data from SharedPreferences
        SharedPreferences userPref = getActivity().getSharedPreferences( "user", MODE_PRIVATE );
        String fullname = userPref.getString( "name", "" );
        String username = userPref.getString( "username", "" );

        // Set user data to views
        tvFullname.setText( fullname );
        tvUsername.setText( username );
    }

    private void doLogout() {
        //get Tokens
        SharedPreferences userPref = getActivity().getSharedPreferences( "user", MODE_PRIVATE );
        String accessToken = userPref.getString( "access_token", "" );

        // add access token to headers
        Map<String, String> headers = new HashMap<>();
        headers.put( "Authorization", "Bearer " + accessToken );

        // send request to server using Volley
        StringRequest request = new StringRequest( Request.Method.POST, UrlsApi.LOGOUT, response -> {
            try {
                JSONObject jsonObject = new JSONObject( response );
                if (jsonObject.getInt( "status" ) == 200) {
                    // remove user data from SharedPreferences
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.clear();
                    editor.apply();

                    Navigator.of( getActivity() ).pushReplacement( Login.class );
                } else {
//                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();

        } ) {
            // add headers to POST request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        // add request to Volley request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add( request );

    }
}
