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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madingapp.Helper;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMadingActivity extends AppCompatActivity {

    private Helper helper;

    private ImageButton imgBack;
    private Button btnSelectPhoto, btnSave;
    private ImageView imgCreateMading;
    private EditText edtTitle, edtDesc;
    private Spinner spinner;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    String file_path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mading);
        helper = new Helper(CreateMadingActivity.this);

        imgBack = findViewById(R.id.btnCancel_Post);
        btnSelectPhoto = findViewById(R.id.btnUploadImage_Post);
        imgCreateMading = findViewById(R.id.imgPost_Post);
        edtTitle = findViewById(R.id.edtTitle_Post);
        spinner = findViewById(R.id.idSpinner_Post);
        edtDesc = findViewById(R.id.edtDescription_Post);
        btnSave = findViewById(R.id.btnSave_Post);

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

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String desc = edtDesc.getText().toString().trim();
            if (title.isEmpty() || desc.isEmpty() || file_path.isEmpty()) {
                helper.showMessage("Please fill the field!");
            } else {
                UploadFile(title, desc);
            }
        });
    }

    private void UploadFile(String title, String desc) {
        try {
            File file = new File(file_path);
            RequestBody reqTitle = RequestBody.create(MediaType.parse("multipart/form-data"), title);
            RequestBody reqDesc = RequestBody.create(MediaType.parse("multipart/form-data"), desc);
            RequestBody reqImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            ApiService.endPoint().PostMading(reqTitle, reqDesc, reqImage).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(CreateMadingActivity.this, "Successfully Create Mading", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(CreateMadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(CreateMadingActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            String filePath = getRealPathFromUri(data.getData(), CreateMadingActivity.this);
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
            imgCreateMading.setImageBitmap(bitmap);
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
        int result = ContextCompat.checkSelfPermission(CreateMadingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void filePicker() {
        Toast.makeText(CreateMadingActivity.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        Intent opengallery = new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery, REQUEST_GALLERY);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CreateMadingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(CreateMadingActivity.this, "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(CreateMadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}