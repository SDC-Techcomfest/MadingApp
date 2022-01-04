package com.example.madingapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madingapp.Adapters.HotTopicsAdapter;
import com.example.madingapp.Helper;
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
 * Use the {@link HotTopicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotTopicsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Helper helper;
    private RecyclerView recyclerView;
    private HotTopicsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<MadingResponse.Data> madingLists = new ArrayList<>();

    public HotTopicsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotTopicsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotTopicsFragment newInstance(String param1, String param2) {
        HotTopicsFragment fragment = new HotTopicsFragment();
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
        View view = inflater.inflate(R.layout.fragment_hot_topics, container, false);
        helper = new Helper(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerView_HotTopics);
        swipeRefreshLayout = view.findViewById(R.id.idSwipeRefresh_HotTopics);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                setUpAdapter();
                getMading();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpAdapter();
        getMading();
    }

    private void getMading() {
        helper.showProgressDialog(getContext());
        ApiService.endPoint().madingHotTopicsGet().enqueue(new Callback<MadingResponse>() {
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
        adapter = new HotTopicsAdapter(madingLists);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}