package com.nekoid.smektuber.screen.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.nekoid.smektuber.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DaftarPPDB extends AppCompatActivity {
    String[] items =  {"TKJ (Teknik Komputer dan Jaringan)","MM (Multi Media)"};
    AutoCompleteTextView autoCompleteTxt;
    private EditText TanggalLahir, TahunLulus;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ppdb);
        autoCompleteTxt = findViewById(R.id.Dp_Jurusan);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        TanggalLahir = findViewById(R.id.Dp_Tanggal_Lahir);
        TahunLulus = findViewById(R.id.Dp_tahun_lulus);

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
            }
        });
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
                        TanggalLahir.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}