package com.example.madingapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.AuthRequest;
import com.example.madingapp.Models.Response.AuthResponse;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private Helper helper;
    private CircleImageView circleImageView;
    private Button btnSelectPhoto, btnChangePassword, btnSave;
    private EditText edtFirstName, edtLastName;
    String firstName;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    String file_path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        helper = new Helper(EditProfileActivity.this);

        circleImageView = findViewById(R.id.imageprofile_EditProfile);
        btnSelectPhoto = findViewById(R.id.btnSelectImage_EditProfile);
        btnChangePassword = findViewById(R.id.changePassword_EditProfile);
        btnSave = findViewById(R.id.Save_EditProfile);
        edtFirstName = findViewById(R.id.fname_EditProfile);
        edtLastName = findViewById(R.id.lname_EditProfile);
        btnChangePassword  = findViewById(R.id.changePassword_EditProfile);
       // edtUsername = findViewById(R.id.username_EditProfile);

        Picasso.get()
                .load(Helper.imageProfileUserLogin)
                .into(circleImageView);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        filePicker();
                    } else {
                        requestPermission();
                    }
                } else {
                    filePicker();
                }
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ChangePasswordActivity.class));
                finish();
            }
        });

        btnSave.setOnClickListener(v -> {
            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();

                UploadFile(firstName, lastName);

        });
    }

    private void UploadFile(String firstName, String lastName) {
        try {
            File file = new File(file_path);
            RequestBody requserId = RequestBody.create(MediaType.parse("multipart/form-data"), Helper.userId);
            RequestBody reqlastName = RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
            RequestBody reqfirstName = RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
            RequestBody reqImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            ApiService.endPoint().userPut(requserId, reqlastName, reqfirstName, reqImage).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    helper.showMessage("Successfully edit Profile!");
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    helper.showMessage(t.getMessage());
                }
            });
        } catch (Exception ex) {
            Toast.makeText(EditProfileActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            String filePath = getRealPathFromUri(data.getData(), EditProfileActivity.this);
            Log.d("File Path : ", " " + filePath);
            this.file_path = filePath;
            File file = new File(filePath);
            Uri uri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            circleImageView.setImageBitmap(bitmap);
        }
    }

    public String getRealPathFromUri(Uri uri, Activity activity) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int id = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void filePicker() {
        Toast.makeText(EditProfileActivity.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        Intent opengallery = new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery, REQUEST_GALLERY);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(EditProfileActivity.this, "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}