package com.nekoid.smektuber.screen.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.button.MaterialButton;

import com.nekoid.smektuber.R;


public class Welcome extends Fragment {

    private View view;
    private Button btnToLogin, btnToRegister;
    public Welcome() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_welcome,container, false );
        init();
        return view;
    }

    private void init(){
        btnToLogin = view.findViewById( R.id.btnToSignIn );
        btnToRegister= view.findViewById( R.id.btnToSignUp );

        btnToLogin.setOnClickListener( v -> {
//            getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace( R.id.frameAuthContainer,new Login() )
//                    .addToBackStack( null )
//                    .commit();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in ,R.anim.fade_out);
            transaction.replace(R.id.frameAuthContainer, new Login());
            transaction.addToBackStack(null);
            transaction.commit();
        } );
        btnToRegister.setOnClickListener( v ->{
            // change fragment register
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.frameAuthContainer, new Register() )
                    .addToBackStack( null )
                    .commit();
        } );
    }
}