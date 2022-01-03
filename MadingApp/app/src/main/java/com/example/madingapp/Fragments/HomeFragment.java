package com.example.madingapp.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madingapp.Activities.HomeActivity;
import com.example.madingapp.Activities.LoginActivity;
import com.example.madingapp.Adapters.HomeAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Helper helper;

    // Initialize Variable
    private ImageView imgUser;
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private EditText edtSearch;
    private TextView tvUserName, tvHello, tvLogin;


    private List<MadingResponse.Data> madingLists = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        helper = new Helper(getContext());

        // Assign Variable
        imgUser = (ImageView) view.findViewById(R.id.imgView_Home);
        tvUserName = (TextView) view.findViewById(R.id.tvName_Home);
        tvLogin = (TextView) view.findViewById(R.id.tvLogin_Home);
        tvHello = (TextView) view.findViewById(R.id.tvHello_Home);

        if (Helper.TOKEN == null) {
            tvHello.setVisibility(View.GONE);
            tvUserName.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Helper.TOKEN == null) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            });
        } else {
            tvHello.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            getUserImage();
            getUserProfile();
        }
        edtSearch = (EditText) view.findViewById(R.id.edtSearch_Home);
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerView_Home);

        setUpAdapter();
        getMading();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    private void getMading() {
        helper.showProgressDialog(getContext());
        ApiService.endPoint().madingGet().enqueue(new Callback<MadingResponse>() {
            @Override
            public void onResponse(Call<MadingResponse> call, Response<MadingResponse> response) {
                helper.dismissProgressDialog();
                if (response.isSuccessful()) {
                    List<MadingResponse.Data> dataList = response.body().getData();
                    Helper.searchMadings = dataList;
                    adapter.setData(dataList);
                }
            }

            @Override
            public void onFailure(Call<MadingResponse> call, Throwable t) {
                helper.dismissProgressDialog();
                helper.showMessage(t.getMessage());
            }
        });
    }

    private void setUpAdapter() {
        adapter = new HomeAdapter(madingLists, new HomeAdapter.onClickAdapter() {
            @Override
            public void onClick(MadingResponse.Data data) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.putExtra("ID", data.getMadingId());
                i.putExtra("TITLE", data.getTitle());
                i.putExtra("DESCRIPTION", data.getDescription());
                i.putExtra("MADING_IMAGE", data.getMadingImage());
                i.putExtra("CREATED_DATE", data.getCreatedDate());
                startActivity(i);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getUserProfile() {
        ApiService.endPoint().userGetMeGet().enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                MeResponse.Data us = response.body().getData();
                tvUserName.setText(us.getFirstName());
                Helper.firstNameUserLogin = us.firstName;
                Helper.lastNameUserLogin = us.lastName;
                Helper.imageProfileUserLogin = us.userImage;
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                helper.showMessage(t.getMessage());
            }
        });
    }

    private void getUserImage() {
        Call<ResponseBody> bodyCall = ApiService.endPoint().userImageGet();
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                imgUser.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                helper.dismissProgressDialog();
                helper.showMessage(t.getMessage());
            }
        });

    }

    private void filter(String text) {
        ArrayList<MadingResponse.Data> filters = new ArrayList<>();

        for (MadingResponse.Data item : madingLists) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filters.add(item);
            }

            adapter.filterList(filters);
        }
    }
}