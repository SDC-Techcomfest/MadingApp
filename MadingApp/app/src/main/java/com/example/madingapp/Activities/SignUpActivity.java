package com.example.madingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.madingapp.Helper;
import com.example.madingapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private Helper helper;

    private Spinner spinnerGender;
    private ArrayAdapter<CharSequence> adapter;
    private String[] gender = {"Male", "Female", "Other"};

    private EditText etDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setStatusBarColor(getResources().getColor(R.color.topBarActivity));
        helper = new Helper(SignUpActivity.this);

        etDate = (EditText) findViewById(R.id.date_SignUp);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        spinnerGender = findViewById(R.id.gender_SignUp);
        spinnerGender.setOnItemSelectedListener(this);

        adapter = new ArrayAdapter(this, R.layout.spinner_text, gender);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerGender.setAdapter(adapter);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}