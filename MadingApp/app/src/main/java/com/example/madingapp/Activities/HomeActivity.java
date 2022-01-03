package com.example.madingapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madingapp.Adapters.HomeAdapter;
import com.example.madingapp.Fragments.HomeFragment;
import com.example.madingapp.Fragments.HotTopicsFragment;
import com.example.madingapp.Fragments.ProfileFragment;
import com.example.madingapp.Helper;
import com.example.madingapp.Models.Response.MadingResponse;
import com.example.madingapp.Models.Response.MeResponse;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private Helper helper;

    private AnimatedBottomBar bottomBar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        helper = new Helper(HomeActivity.this);

        getWindow().setStatusBarColor(getResources().getColor(R.color.topBarHome));

        bottomBar = (AnimatedBottomBar) findViewById(R.id.bottomNavigation_Home);

        // Saat ke HomeActivity Button Home langsung Ke pilih
        if (savedInstanceState == null) {
            bottomBar.selectTabById(R.id.homeBottomMenu, true);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        // OnClick
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.homeBottomMenu:
                        fragment = new HomeFragment();
                        break;
                    case R.id.hotTopicsBottomMenu:
                        fragment = new HotTopicsFragment();
                        break;
                    case R.id.profileBottomMenu:
                        fragment = new ProfileFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Toast.makeText(HomeActivity.this, "Error Fragment...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
}