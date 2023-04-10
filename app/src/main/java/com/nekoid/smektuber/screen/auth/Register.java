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

public class Register extends Fragment {

   private View view;
   private Toolbar toolbar;
   private Button btnRegister;
   private TextView txtToLogin;
    public Register() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.fragment_register ,container, false);
        init();
        return view;
    }

        private void init(){
            // init component
            txtToLogin = view.findViewById( R.id.txtToLogin );
            btnRegister = view.findViewById( R.id.btn_register );

            // link to login
            txtToLogin.setOnClickListener( v->{
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace( R.id.frameAuthContainer, new Login() )
                        .addToBackStack( null )
                        .commit();
            } );

            // Add backPressed fragment
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