package com.example.madingapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madingapp.Activities.CreateMadingActivity;
import com.example.madingapp.Activities.EditProfileActivity;
import com.example.madingapp.Helper;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Helper helper;
    private String mParam1;
    private String mParam2;
    private ImageView imgMading;
    private TextView tvName;
    private Button btnSignOut, btnEdit, btnCreateMading;
    private ImageButton imgBtnContentMading, imgBtnBookmarks;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        helper = new Helper(getContext());
        imgMading = view.findViewById(R.id.imgBookmarks_ProfileFragment);
        tvName = view.findViewById(R.id.tvName_Profile);
        btnEdit = view.findViewById(R.id.btnEdit_Profile);
        btnSignOut = view.findViewById(R.id.btnLogout_Profile);
        imgBtnBookmarks = view.findViewById(R.id.imgButtonBookmarks_Profile);
        imgBtnContentMading = view.findViewById(R.id.imgButtonContentMading_Profile);
        btnCreateMading = view.findViewById(R.id.btnCreateMading_Profile);

        setUserProfile();

        btnEdit.setOnClickListener(view1 -> {
            if (Helper.TOKEN == null) {
                helper.showMessage("Please Login first!");
            } else {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        btnCreateMading.setOnClickListener(v -> {
            if (Helper.TOKEN == null) {
                helper.showMessage("Please Login first!");
            } else {
                startActivity(new Intent(getContext(), CreateMadingActivity.class));
            }
        });

        btnSignOut.setOnClickListener(view1 -> {
            if (Helper.TOKEN == null) {
                helper.showMessage("Please Login first!");
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Sign Out")
                        .setMessage("Do you want to Sign Out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logoutApi();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                alertDialog.show();
            }
        });

        if (savedInstanceState == null) {
            fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentProfile_container, new ProfileContentMadingFragment()).commit();
        }

        imgBtnContentMading.setOnClickListener(view1 -> {
            fragment = new ProfileContentMadingFragment();
            fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentProfile_container, fragment).commit();
        });

        imgBtnBookmarks.setOnClickListener(view1 -> {
            fragment = new ProfileBookmarksMadingFragment();
            fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentProfile_container, fragment).commit();
        });

        return view;
    }

    private void logoutApi() {
        helper.showProgressDialog(getContext());
        ApiService.endPoint().authLogoutGet().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                helper.dismissProgressDialog();
                Helper.refreshUserLogin();
                helper.showMessage("Successfully Sign Out!");
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                helper.dismissProgressDialog();
                helper.showMessage(t.getMessage());
            }
        });
    }

    private void setUserProfile() {
        if (Helper.TOKEN != null) {
            tvName.setText(Helper.firstNameUserLogin + " " + Helper.lastNameUserLogin);
            Picasso.get()
                    .load(Helper.imageProfileUserLogin)
                    .into(imgMading);
        }
    }
}