package com.nekoid.smektuber.screen.home.ppdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.navigation.Navigator;
//import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.screen.notification.LoadingDialog;
import com.nekoid.smektuber.screen.notification.NotifNoInternet;
import com.nekoid.smektuber.screen.notification.Notif_Succes_register_Ppdb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DaftarPPDB extends BaseActivity {
//    String[] items = {};
    ArrayList<String> items = new ArrayList<>();
    private MajorModel selectedMajor, selectedMajor2;
    private List<MajorModel> majorList = new ArrayList<>();
    AutoCompleteTextView autoCompleteTxt, autoCompleteTxt02;
//    private EditText TanggalLahir, TahunLulus;
    ArrayAdapter<String> adapterItems;
    Toolbar toolbar;
    private Button btnRegisterPpdb;
    private TextInputLayout txtLayoutNisn, txtLayoutName, txtLayoutPlaceBirth,txtLayoutDateBirth, txtLayoutAddress,txtLayoutNoHp, txtLayoutNameFather, txtLayoutNameMother,txtLayoutGraduationYear,txtLayoutSchoolOrigin, txtLayoutMajor1, txtLayoutMajor2;
    private TextInputEditText et_nisn, et_name,et_placeBirth, et_address, et_Nohp, et_father, et_mother, et_guardian,et_schoolOrigin,TanggalLahir, TahunLulus;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ppdb);
        init();
        setToolbar();
    }
    private void init(){
        autoCompleteTxt = findViewById(R.id.Dp_Jurusan);
        autoCompleteTxt02 = findViewById( R.id.Dp_Jurusan02 );
//        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, new ArrayList<>(items));
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt02.setAdapter( adapterItems );
        txtLayoutNisn = findViewById( R.id.txtLayoutNisn );
        txtLayoutName = findViewById( R.id.txtLayoutName );
        txtLayoutPlaceBirth = findViewById( R.id.txtLayoutPlaceBirth );
        txtLayoutDateBirth = findViewById( R.id.txtLayoutDateBirth );
        txtLayoutAddress = findViewById( R.id.txtLayoutAddress );
        txtLayoutNoHp = findViewById( R.id.txtLayoutPhone );
        txtLayoutNameFather = findViewById( R.id.txtLayoutFather );
        txtLayoutNameMother = findViewById( R.id.txtLayoutMother );
        txtLayoutSchoolOrigin = findViewById( R.id.txtLayoutSchoolOrigin );
        txtLayoutGraduationYear = findViewById( R.id.txtLayoutGraduationYear );
        txtLayoutMajor1 = findViewById( R.id.txtLayoutMajor1 );
        txtLayoutMajor2 = findViewById( R.id.txtLayoutMajor2 );
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
        loadingDialog = new LoadingDialog( DaftarPPDB.this );
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
        autoCompleteTxt02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedMajor2 = majorList.get(position);
            }
        });

        btnRegisterPpdb.setOnClickListener( v->{
            if (!Utils.isNetworkAvailable()){
                fragmentNoInternet();
            } else if (isFromValid()){
                loadingDialog.startLoading();
                Http.post( Endpoint.PPDB.getUrl(), PublicApi.getHeaders(),getBody(),this::doRegisterPpdb );
            }
        } );
        setupFormValidation();
    }

    public void fragmentNoInternet() {
        findViewById(R.id.content).setVisibility(View.INVISIBLE);
        findViewById(R.id.daftarPPDBFragment).setVisibility(View.VISIBLE);
        replaceFragment(R.id.daftarPPDBFragment, new NotifNoInternet(view -> {
            if (Utils.isNetworkAvailable()){
                findViewById(R.id.content).setVisibility(View.VISIBLE);
                findViewById(R.id.daftarPPDBFragment).setVisibility(View.INVISIBLE);

            } else {
                Toast.makeText(this, "Please connect to internet, and try again", Toast.LENGTH_SHORT).show();
            }
        }));
    }
    private boolean isFromValid(){
        boolean isValid = true;

        isValid = validateField( et_nisn,txtLayoutNisn,"Mohon isi NISN anda" )&& isValid;
        isValid = validateField( et_name, txtLayoutName, "Mohon isi nama anda" )&& isValid;
        isValid = validateField( et_placeBirth, txtLayoutPlaceBirth, "Mohon isi tempat lahir anda" )&& isValid;
        isValid = validateField( TanggalLahir, txtLayoutDateBirth, "Mohon isi tanggal lahir anda" )&& isValid;
        isValid = validateField( et_address, txtLayoutAddress, "Mohon isi alamat anda" )&& isValid;
        isValid = validateField( et_Nohp, txtLayoutNoHp, "Mohon isi no handphone anda" )&& isValid;
        isValid = validateField( et_father, txtLayoutNameFather, "Mohon isi nama ayah anda" )&& isValid;
        isValid = validateField( et_mother, txtLayoutNameMother, "Mohon isi nama ibu anda" )&& isValid;
        isValid = validateField( et_schoolOrigin, txtLayoutSchoolOrigin, "Mohon isi asal sekolah anda" )&& isValid;
        isValid = validateField( TahunLulus, txtLayoutGraduationYear, "Mohon isi tahun lulus anda" )&& isValid;
        isValid = validateMajorSelection( autoCompleteTxt, txtLayoutMajor1,"Mohon pilih jurusan 1 yang anda minati" )&& isValid;
        isValid = validateMajorSelection( autoCompleteTxt02, txtLayoutMajor2,"Mohon pilih jurusan 2 yang anda minati" )&& isValid;

        return isValid;
    }
    private boolean validateField(TextInputEditText editText, TextInputLayout textInputLayout, String errorMessage) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            textInputLayout.setError(errorMessage);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }
    private boolean validateMajorSelection(AutoCompleteTextView autoCompleteTextView, TextInputLayout textInputLayout, String errorMessage) {
        String selectedMajorText = autoCompleteTextView.getText().toString().trim();
        if (TextUtils.isEmpty(selectedMajorText)) {
            textInputLayout.setError(errorMessage);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }
    private void setupFormValidation() {
        et_nisn.addTextChangedListener(createTextWatcher(et_nisn, txtLayoutNisn));
        et_name.addTextChangedListener(createTextWatcher(et_name, txtLayoutName));
        et_placeBirth.addTextChangedListener(createTextWatcher(et_placeBirth, txtLayoutPlaceBirth));
        TanggalLahir.addTextChangedListener(createTextWatcher(TanggalLahir, txtLayoutDateBirth));
        et_address.addTextChangedListener(createTextWatcher(et_address, txtLayoutAddress));
        et_Nohp.addTextChangedListener(createTextWatcher(et_Nohp, txtLayoutNoHp));
        et_father.addTextChangedListener(createTextWatcher(et_father, txtLayoutNameFather));
        et_mother.addTextChangedListener(createTextWatcher(et_mother, txtLayoutNameMother));
        et_schoolOrigin.addTextChangedListener(createTextWatcher(et_schoolOrigin, txtLayoutSchoolOrigin));
        TahunLulus.addTextChangedListener(createTextWatcher(TahunLulus, txtLayoutGraduationYear));
        autoCompleteTxt.addTextChangedListener(createTextWatcher(autoCompleteTxt, txtLayoutMajor1));
        autoCompleteTxt02.addTextChangedListener(createTextWatcher(autoCompleteTxt02, txtLayoutMajor2));

    }
    private TextWatcher createTextWatcher(final TextInputEditText editText, final TextInputLayout textInputLayout) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateField(editText, textInputLayout, null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
    private TextWatcher createTextWatcher(final AutoCompleteTextView autoCompleteTextView, final TextInputLayout textInputLayout) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String selectedMajorText = autoCompleteTextView.getText().toString().trim();
                if (!TextUtils.isEmpty(selectedMajorText)) {
                    textInputLayout.setError(null);
                }
            }
        };
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
        if (response.statusCode != 200) {
            return;
        }
        try {
            JSONObject rawBody = new JSONObject(response.body.toString());
            if (rawBody.getInt("status") != 200) {
                return;
            }
            JSONArray jsonArray = rawBody.getJSONArray("data");
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
        if (selectedMajor != null) {
            return selectedMajor.id;
        }
        return null;
    }
    private Integer majorId2(){
//        return selectedMajor.id;
        if (selectedMajor != null) {
            return selectedMajor2.id;
        }
        return null;
    }
    private Map<String, String> getBody(){
        HashMap<String, String> body = new HashMap<>();
        body.put("nisn", nisn());
        body.put("place_birth", placeBirth());
        body.put("full_name", fullName());
//        body.put("dateBirth", dateBirth());
        body.put("date_birth", convertToDate(dateBirth()));
        body.put("address", address());
        body.put("phone", phone());
        body.put("father_name", fatherName());
        body.put("mother_name", motherName());
        body.put("guardian_name", guardianName());
        body.put("school_origin", schoolOrigin());
//        body.put("graduationYear", graduationYear());
        body.put("graduation_year", convertToYear(graduationYear()));
//        body.put("majorId1", "1");
//        body.put("majorId2", String.valueOf(majorId2()));
        Integer major1 = majorId1();
        if (major1 != null) {
            body.put("major_id_1", String.valueOf(major1));
        }
        Integer major2 = majorId2();
        if (major2 != null) {
            body.put("major_id_2", String.valueOf(major2));
        }

        return body;
    }
    private String convertToDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String convertToYear(String yearString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
            Date date = inputFormat.parse(yearString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
private void doRegisterPpdb(Response response) {
    if (response.statusCode != 200) {
        String errorMessage = "";
        try {
            JSONObject errorBody = new JSONObject(response.body.toString());
            if (errorBody.has("message")) {
                errorMessage = errorBody.getString("message");
            } else if (errorBody.has("errors")) {
                JSONObject errors = errorBody.getJSONObject("errors");
                Iterator<String> keys = errors.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String error = errors.getJSONArray(key).getString(0);
                    errorMessage += key + ": " + error + "\n";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (errorMessage.isEmpty()) {
            errorMessage = "Terjadi kesalahan saat mengirimkan data pendaftaran. Silakan coba lagi.";
        }
        Toast.makeText(this, "Pendaftaran Gagal: " + errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("Pendaftaran Gagal", errorMessage);
        loadingDialog.isDismiss();
        return;
    }
    loadingDialog.isDismiss();
    Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
//    Navigator.of(this).push(Notif_Succes_register_Ppdb.class);
    Fragment notifFragment = new Notif_Succes_register_Ppdb();
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, notifFragment)
            .addToBackStack(null)
            .commit();
}

}