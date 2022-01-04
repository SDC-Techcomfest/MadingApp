package com.example.madingapp.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madingapp.Activities.LoginActivity;
import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.ChangePasswordRequest;
import com.example.madingapp.Models.Response.MeResponse;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();
    private Helper helper = new Helper(this);

    private Button btnConfirm;
    private EditText edtOldpass, edtNewpass, edtConfpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnConfirm = findViewById(R.id.btnConfirmation_changepass);
        edtOldpass = findViewById((R.id.edtOldPassword_changepass));
        edtNewpass = findViewById((R.id.edtNewPassword_changepass));
        edtConfpass = findViewById((R.id.edtConfirmPassword_changepass));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = edtOldpass.getText().toString().trim();
                String newPassword = edtNewpass.getText().toString().trim();
                String confirmPassword = edtConfpass.getText().toString().trim();

                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    helper.showMessage("Please Insert the password!");
                } else if (!newPassword.equals(confirmPassword)) {
                    helper.showMessage("New password and confirm password must be same!");
                } else {
                    changePass(oldPassword, newPassword);
                }
            }
        });
    }

    public void changePass(String OldPassword, String NewPassword) {
        helper.showProgressDialog(this);

        ChangePasswordRequest cr = new ChangePasswordRequest();
        cr.setUserId(Helper.userId);
        cr.setOldPassword(OldPassword);
        cr.setNewPassWord(NewPassword);

        ApiService.endPoint().changePasswordPut(cr).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                helper.dismissProgressDialog();

                if (response.isSuccessful()) {
                    helper.showMessage("Success change password");
                    startActivity(new Intent(ChangePasswordActivity.this, HomeActivity.class));
                    finish();
                } else {
                    helper.showMessage("Change Password Failed, please check your old password");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                helper.dismissProgressDialog();
                helper.showMessage(t.getMessage());
            }
        });
    }
}