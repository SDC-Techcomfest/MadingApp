package com.example.madingapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madingapp.Adapters.ProfileBookMarksMadingAdapter;
import com.example.madingapp.Adapters.ProfileContentMadingAdapter;
import com.example.madingapp.Helper;
import com.example.madingapp.Models.Response.BookMarkResponse;
import com.example.madingapp.Models.Response.MadingResponse;
import com.example.madingapp.R;
import com.example.madingapp.Retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileContentMadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileContentMadingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private Helper helper;
    private ProfileContentMadingAdapter adapter;
    private List<MadingResponse.Data> madings = new ArrayList<>();

    public ProfileContentMadingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileContentMadingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileContentMadingFragment newInstance(String param1, String param2) {
        ProfileContentMadingFragment fragment = new ProfileContentMadingFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile_content_mading, container, false);
        recyclerView = view.findViewById(R.id.idRecyclerView_FragmentProfileContentMading);
        helper = new Helper(getContext());
        setUpAdapter();
        getMading();
        return view;
    }

    private void getMading() {
        helper.showProgressDialog(getContext());
        ApiService.endPoint().madingMyMadingsGet().enqueue(new Callback<MadingResponse>() {
            @Override
            public void onResponse(Call<MadingResponse> call, Response<MadingResponse> response) {
                helper.dismissProgressDialog();
                if (response.isSuccessful()) {
                    List<MadingResponse.Data> dataList = response.body().getData();
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
        adapter = new ProfileContentMadingAdapter(madings, new ProfileContentMadingAdapter.onClickAdapter() {
            @Override
            public void onClick(MadingResponse.Data data) {

            }
        });

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}