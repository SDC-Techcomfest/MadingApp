package com.example.madingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.AuthRequest;
import com.example.madingapp.Models.Response.AuthResponse;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();
    private Helper helper;

    // Initialize Variable
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.topBarActivity));
        helper = new Helper(LoginActivity.this);

        // Assign Variable
        edtUsername = findViewById(R.id.edtUsername_Login);
        edtPassword = findViewById(R.id.edtPassword_Login);
        btnLogin = findViewById(R.id.btnLogin_Login);
        tvSignUp = findViewById(R.id.tvSignUp_Login);

        tvSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        btnLogin.setOnClickListener(view -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                helper.showMessage("Please insert Username & Password!");
            } else {
                loginApi(username, password);
            }
        });
    }

    private void loginApi(String username, String password) {
        helper.showProgressDialog(this);
        AuthRequest request = new AuthRequest();
        request.setUsername(username);
        request.setPassword(password);

        ApiService.endPoint().authLoginPost(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                helper.dismissProgressDialog();
                if (response.isSuccessful()) {
                    Helper.TOKEN = response.body().getData();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                        helper.showMessage("Invalid Username or Password!");
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                helper.dismissProgressDialog();
                helper.showMessage(t.getMessage());
            }
        });
    }
}