package com.nekoid.smektuber.screen.home.ppdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.screen.notification.Notif_Succes_register_Ppdb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DaftarPPDB extends BaseActivity {
    String[] items = {};
    private MajorModel selectedMajor;
    private List<MajorModel> majorList = new ArrayList<>();
    AutoCompleteTextView autoCompleteTxt;
    private EditText TanggalLahir, TahunLulus;
    ArrayAdapter<String> adapterItems;
    Toolbar toolbar;
    private Button btnRegisterPpdb;
    private TextInputLayout txtLayoutNisn, txtLayoutName, txtLayoutPlaceBirth, txtLayoutAddress,txtLayoutNoHp, txtLayoutNameFather, txtLayoutNameMother,txtLayoutSchoolOrigin, txtLayoutMajor;
    private TextInputEditText et_nisn, et_name,et_placeBirth, et_address, et_Nohp, et_father, et_mother, et_guardian,et_schoolOrigin,et_major;
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
        et_nisn = findViewById( R.id.Dp_NISN );
        et_name = findViewById( R.id.Dp_Name_Lengkap );
        et_placeBirth = findViewById( R.id.Dp_TempatLahir);
        TanggalLahir = findViewById(R.id.Dp_Tanggal_Lahir);
        et_address = findViewById( R.id.Dp_Alamat );
        et_Nohp = findViewById( R.id.Dp_no_hp );
        et_father = findViewById( R.id.Dp_nama_ayah );
        et_mother = findViewById( R.id.Dp_Nama_Ibu );
        et_guardian = findViewById( R.id.Dp_Nama_Wali );
        et_schoolOrigin = findViewById( R.id.Dp_Asal_sekolah );
        TahunLulus = findViewById(R.id.Dp_tahun_lulus);
        btnRegisterPpdb = findViewById( R.id.BtnDaftarPPDB );
        fetchMajors();
        TahunLulus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTahun();
            }
        });

        TanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedMajor = majorList.get(position);
            }
        });
        btnRegisterPpdb.setOnClickListener( v->{
            Http.post( Endpoint.PPDB.getUrl(), PublicApi.getHeaders(),getBody(),this::doRegisterPpdb );
        } );

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

    private void showTahun() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        final String[] years = generateYears(currentYear - 50, currentYear + 50); // Range tahun yang ingin ditampilkan

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Tahun")
                .setItems(years, (dialog, which) -> {
                    String selectedYear = years[which];
                    TahunLulus.setText(selectedYear);
                })
                .setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String[] generateYears(int startYear, int endYear) {
        int size = endYear - startYear + 1;
        String[] years = new String[size];
        for (int i = 0; i < size; i++) {
            years[i] = String.valueOf(startYear + i);
        }
        return years;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
                        String selectedDate = sdf.format(calendar.getTime());
                        if (validateBirthday(year, month, dayOfMonth)) {
                            Toast.makeText(DaftarPPDB.this, "Tanggal Lahir : " + selectedDate, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DaftarPPDB.this, "Umur minimal 13 tahun dan maksimal 17 tahun", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        TanggalLahir.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    protected boolean validateBirthday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -17);
        if (calendar.get(Calendar.YEAR) > year) {
            return false;
        }
        calendar.add(Calendar.YEAR, +4);
        if (calendar.get(Calendar.YEAR) < year) {
            return false;
        }
        return true;
    }
    private void fetchMajors(){
        Http.get(Endpoint.LIST_MAJOR.getUrl(), PublicApi.getHeaders(), this::onFetchMajors);
    }

    private void onFetchMajors(Response response) {
        try {
            JSONArray jsonArray = new JSONArray(response.body);
            majorList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMajor = jsonArray.getJSONObject(i);
                MajorModel major = MajorModel.fromJson(jsonMajor);
                majorList.add(major);
            }

            List<String> majorNames = new ArrayList<>();
            for (MajorModel major : majorList) {
                majorNames.add(major.majorName);
            }

            adapterItems.clear();
            adapterItems.addAll(majorNames);
            adapterItems.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String nisn(){
        return et_nisn.getText().toString();
    }
    private String fullName(){
        return  et_name.getText().toString();
    }
    private String placeBirth(){
        return et_placeBirth.getText().toString();
    }
    private String dateBirth(){
        return TanggalLahir.getText().toString();
    }
    private String address(){
        return et_address.getText().toString();
    }
    private String phone(){
        return et_Nohp.getText().toString();
    }
    private String fatherName(){
        return et_father.getText().toString();
    }
    private String motherName(){
        return et_mother.getText().toString();
    }
    private String guardianName(){
        return et_guardian.getText().toString();
    }
    private String schoolOrigin(){
        return et_schoolOrigin.getText().toString();
    }
    private String graduationYear(){
        return TahunLulus.getText().toString();
    }
    private Integer majorId1(){
        return selectedMajor.id;
    }
    private Integer MajorId2(){
        return selectedMajor.id;
    }
    private Map<String, String> getBody(){
        HashMap<String, String> body = new HashMap<>();
        body.put("nisn", nisn());
        body.put("placeBirth", placeBirth());
        body.put("fullName", fullName());
        body.put("dateBirth", dateBirth());
        body.put("address", address());
        body.put("phone", phone());
        body.put("fatherName", fatherName());
        body.put("motherName", motherName());
        body.put("guardianName", guardianName());
        body.put("schoolOrigin", schoolOrigin());
        body.put("graduationYear", graduationYear());
        body.put("majorId1", String.valueOf(majorId1()));
//        body.put("majorId2", String.valueOf(majorId2()));

        return body;
    }
    private void doRegisterPpdb(Response response){
        if (response.statusCode != 200){
            Toast.makeText( this, "Pendaftaran Gagal", Toast.LENGTH_SHORT ).show();
            return;
        }
        Toast.makeText( this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT ).show();
        Navigator.of( this ).push( Notif_Succes_register_Ppdb.class );

    }
}