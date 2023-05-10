package com.nekoid.smektuber.screen.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;

public class DaftarPPDB extends AppCompatActivity {
    private String[] items =  {"TKJ (Teknik Komputer dan Jaringan)","MM (Multi Media)"};
    private AutoCompleteTextView autoCompleteTxt;
    private ArrayAdapter<String> adapterItems;

    private EditText birthDay;
    private Button register;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ppdb);
        init();
        setToolbar();
    }
    private void init(){
        autoCompleteTxt = findViewById(R.id.Dp_Jurusan);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        birthDay = findViewById(R.id.Dp_Tanggal_Lahir);
        register = findViewById(R.id.BtnDaftarPPDB);
        register.setOnClickListener(v -> doRegister());

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });


    }
    private void setToolbar(){
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }

    protected void doRegister() {
        if (register != null) {
            if (validateBirthday()) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validateBirthday() {
        if (birthDay != null) {
            String day = birthDay.getText().toString();
            String yearsNow = String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
            if (day.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                String[] split = day.split("/");
                if (split.length == 3) {
                    int dayInt = Integer.parseInt(split[0]);
                    int monthInt = Integer.parseInt(split[1]);
                    int yearsInt = Integer.parseInt(split[2]);

                    if (yearsInt >= Integer.parseInt(yearsNow) - 17 && yearsInt <= Integer.parseInt(yearsNow) - 13) {
                        if (monthInt >= 1 && monthInt <= 12) {
                            if (dayInt >= 1 && dayInt <= 31) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
}