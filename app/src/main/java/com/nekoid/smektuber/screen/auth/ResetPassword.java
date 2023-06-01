package com.nekoid.smektuber.screen.auth;

import static com.nekoid.smektuber.helpers.utils.Utils.replaceFragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.helpers.widget.Style;
import com.nekoid.smektuber.screen.notification.NotifNoInternet;

public class ResetPassword extends AppCompatActivity {
    TextInputLayout txtLayoutPw, txtLayoutPwConfirm;
    TextInputEditText et_Password, et_PasswordConfirm;
    Toolbar toolbar;
    private Button btnResetPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reset_password );
        setToolbar();
    }

    private void init(){
        txtLayoutPw = findViewById( R.id.txtLayoutPassword );
        txtLayoutPwConfirm = findViewById( R.id.txtLayoutPasswordConfirm );
        et_Password = findViewById( R.id.et_password );
        et_PasswordConfirm = findViewById( R.id.et_passwordConfirm );
        btnResetPw = findViewById( R.id.btnSendResetPw );
        btnResetPw.setText( "Daftar" );
        btnResetPw.setOnClickListener( v->{
            if (!Utils.isNetworkAvailable()){
                fragmentNoInternet();
                return;
            }
            // todo : reset pw
            doResetPassword();
        } );

    }
    public void fragmentNoInternet() {
        findViewById(R.id.resetPasswordFragment).setVisibility(View.VISIBLE);
        findViewById(R.id.resetPasswordRelative).setVisibility(View.INVISIBLE);
        replaceFragment(R.id.resetPasswordFragment, new NotifNoInternet(view -> {
            if (Utils.isNetworkAvailable()){
                findViewById(R.id.resetPasswordFragment).setVisibility(View.INVISIBLE);
                findViewById(R.id.resetPasswordRelative).setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Please connect to internet, and try again", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void replaceFragment(@IdRes int containerViewId, @NonNull androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commitAllowingStateLoss();
    }

    private void doResetPassword(){

    }
    private void setToolbar(){
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of( this ).pop();
        return true;
    }
}