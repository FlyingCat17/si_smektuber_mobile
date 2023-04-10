package com.nekoid.smektuber.screen.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nekoid.smektuber.R;


public class Login extends Fragment {
    private View view;
    private Toolbar toolbar;
    private Button btnLogin;
    private TextView txtToRegister, txtToForgotPw;

    public Login() {
        // Required empty public constructor
    }


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate( R.layout.fragment_login,container, false );
            init();
            return view;
        }

        private void init(){
            // init component
            btnLogin = view.findViewById( R.id.btn_login );
            txtToForgotPw = view.findViewById( R.id.forgotPw );
            txtToRegister = view.findViewById( R.id.txtToRegister );

            // link to register
            txtToRegister.setOnClickListener( v ->{
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace( R.id.frameAuthContainer, new Register() )
                        .addToBackStack( null )
                        .commit();
            } );
            // link to forgot password
            txtToForgotPw.setOnClickListener( v -> {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace( R.id.frameAuthContainer, new ForgotPassword() )
                        .addToBackStack( null )
                        .commit();
            } );
            // Add Backpressed fragment
            toolbar = view.findViewById( R.id.backIcon );
            ((AppCompatActivity) requireActivity()).setSupportActionBar( toolbar );
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Add back
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requireActivity().onBackPressed();
//                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new Welcome()).commit();
                }
            });
        }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        requireActivity().onBackPressed();
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }


}