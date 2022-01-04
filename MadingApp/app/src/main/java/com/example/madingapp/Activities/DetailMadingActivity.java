package com.example.madingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.resources.Compatibility;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madingapp.Helper;
import com.example.madingapp.Models.Request.MadingRequest;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMadingActivity extends AppCompatActivity {
    private Helper helper;
    private String id, title, description, madingImage, createdDate, author, authorImage;
    private int like, dislike;

    private CircleImageView circleImageAuthor, circleImaeComment;
    private ImageView imgMading;
    private ImageButton imgBack, btnReport, imgLike, imgDislike;
    private TextView tvAuthor, tvCreatedDate, tvTitle, tvDesc, tvLikes, tvDislikes, tvShowAllComment;
    private EditText edtAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mading);
        helper = new Helper(DetailMadingActivity.this);

        id = getIntent().getStringExtra("ID");
        title = getIntent().getStringExtra("TITLE");
        description = getIntent().getStringExtra("DESCRIPTION");
        madingImage = getIntent().getStringExtra("MADING_IMAGE");
        authorImage = getIntent().getStringExtra("AUTHOR_IMAGE");
        createdDate = getIntent().getStringExtra("CREATED_DATE");
        author = getIntent().getStringExtra("AUTHOR");
        like = getIntent().getIntExtra("LIKE", 0);
        dislike = getIntent().getIntExtra("DISLIKE", 0);

        tvTitle = (TextView) findViewById(R.id.title_DetailMading);
        tvDesc = (TextView) findViewById(R.id.tvDescription_DetailMading);
        tvLikes = (TextView) findViewById(R.id.totlike_DetailMading);
        tvDislikes = (TextView) findViewById(R.id.totdislike_DetailMading);
        tvCreatedDate = (TextView) findViewById(R.id.createdDate_DetailMading);
        tvAuthor = (TextView) findViewById(R.id.author_DetailMading);
        circleImaeComment = (CircleImageView) findViewById(R.id.imageComment_DetailMading);
        circleImageAuthor = (CircleImageView) findViewById(R.id.imageAuthor_DetailMading);
        imgMading = (ImageView) findViewById(R.id.imageView_listHome);
        imgBack = (ImageButton) findViewById(R.id.back_DetailMading);
        edtAddComment = (EditText) findViewById(R.id.edtAddComment_DetailMading);
        btnReport = (ImageButton) findViewById(R.id.report_DetailMading);
        imgLike = (ImageButton) findViewById(R.id.like_DetailMading);
        imgDislike = (ImageButton) findViewById(R.id.dislike_DetailMading);
        tvShowAllComment = (TextView) findViewById(R.id.tvShowAllComment_DetailMading);

        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvDesc.setText(description);
        tvCreatedDate.setText(createdDate);
        tvLikes.setText(String.valueOf(like));
        tvDislikes.setText(String.valueOf(dislike));

        Picasso.get()
                .load(madingImage)
                .into(imgMading);
        Picasso.get()
                .load(authorImage)
                .into(circleImageAuthor);
        Picasso.get()
                .load(Helper.imageProfileUserLogin)
                .into(circleImaeComment);

        imgBack.setOnClickListener(v -> {
            finish();
        });

        edtAddComment.setOnClickListener(v -> {
            if (Helper.TOKEN == null) {
                helper.showMessage("Please login first!");
            } else {

            }
        });

        btnReport.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Report Bug")
                    .setMessage("Do you want to report this Bug?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            helper.showMessage("Successfully Report!");
                        }
                    })
                    .setNegativeButton("No", null)
                    .create();
            alertDialog.show();
        });

        imgLike.setOnClickListener(v -> {
            likeMading();
        });

        imgDislike.setOnClickListener(v -> {
            dislikeMading();
        });

        tvShowAllComment.setOnClickListener(v -> {
            startActivity(new Intent(DetailMadingActivity.this, ShowAllCommentsActivity.class));
        });
    }

    private void dislikeMading() {
        try {
            MadingRequest request = new MadingRequest();
            request.setMadingId(id);
            request.setDislikes(1);
            ApiService.endPoint().madingPut(id, request).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int total = dislike - 1;
                    tvDislikes.setText(String.valueOf(total));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    helper.showMessage(t.getMessage());
                }
            });
        } catch (Exception ex) {
            helper.showMessage(ex.getMessage());
        }
    }

    private void likeMading() {
        try {
            MadingRequest request = new MadingRequest();
            request.setMadingId(id);
            request.setLikes(1);
            ApiService.endPoint().madingPut(id, request).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int total = like + 1;
                    tvLikes.setText(String.valueOf(total));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    helper.showMessage(t.getMessage());
                }
            });
        } catch (Exception ex) {
            helper.showMessage(ex.getMessage());
        }
    }
}