package com.example.madingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.SignUpRequest;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private Helper helper;

    private Spinner spinnerGender;
    private ArrayAdapter<CharSequence> adapter;
    private String[] gender = {"Male", "Female", "Other"};

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btnlogin;
    private EditText etDate, etFirstname, etLastname, etUsername, etPassword, etConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setStatusBarColor(getResources().getColor(R.color.topBarActivity));
        helper = new Helper(SignUpActivity.this);

        etDate = (EditText) findViewById(R.id.date_SignUp);
        etFirstname = findViewById(R.id.fname_SignUp);
        etConfirmPass = findViewById(R.id.confirmpassword_SignUp);
        etLastname = findViewById(R.id.lname_SignUp);
        etPassword = findViewById(R.id.password_SignUp);
        etUsername = findViewById(R.id.username_Signup);
        etDate = findViewById(R.id.date_SignUp);
        btnlogin = findViewById(R.id.btnCreate_SignUp);
        spinnerGender = findViewById(R.id.gender_SignUp);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

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

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = etFirstname.getText().toString().trim();
                String lastname = etLastname.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String ConfirmPass = etConfirmPass.getText().toString().trim();
                String gender = spinnerGender.getSelectedItem().toString().trim();
                String dateOfBirth = etDate.getText().toString().trim();

                if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() || gender.isEmpty() || dateOfBirth.isEmpty()) {
                    helper.showMessage("Data cant be empty");
                } else if (!password.equals(ConfirmPass)) {
                    helper.showMessage("Password and confirm password must be same");
                } else {
                    Signup(firstname, lastname, username, password, gender, dateOfBirth);
                }
            }
        });
    }

    private void Signup(String firstname, String lastname, String username, String password, String gender, String dateOfBirth) {
        helper.showProgressDialog(this);
        SignUpRequest request = new SignUpRequest();
        String id = UUID.randomUUID().toString();
        request.setUserId(id);
        request.setFirstName(firstname);
        request.setLastName(lastname);
        request.setUsername(username);
        request.setPassword(password);
        request.setGender(gender);
        request.setDateOfBirth(dateOfBirth);

        ApiService.endPoint().authSignup(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                helper.dismissProgressDialog();
                int code = response.code();
                if (response.isSuccessful()) {
                    helper.showMessage("Success create account");
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                } else if (code == 409) {
                    helper.showMessage("User already exist!");
                } else if (code == 400) {
                    helper.showMessage("Bad request");
                } else {
                    helper.showMessage("failed to create account");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                helper.dismissProgressDialog();
                helper.showMessage(t.getMessage());
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