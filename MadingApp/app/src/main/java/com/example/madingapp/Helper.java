package com.example.madingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.madingapp.Models.Response.MadingResponse;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    // BASE URL API
    public static final int API_VERSION = 1;
    public static String BASE_URL = "http://172.22.2.36:5000/";

    // Initialize Variable
    public static String TOKEN = null;
    public static String firstNameUserLogin = null;
    public static String userId = null;
    public static String lastNameUserLogin = null;
    public static String imageProfileUserLogin = null;
    public  static  String userPasswordLogin = null;

    private Context context;
    private ProgressDialog progressDialog;

    public Helper(Context context) {
        this.context = context;
    }

    public static void refreshUserLogin() {
        firstNameUserLogin = null;
        lastNameUserLogin = null;
        userId = null;
        imageProfileUserLogin = null;
        TOKEN = null;
    }

    // Void Method
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(Context context) {
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true);
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
