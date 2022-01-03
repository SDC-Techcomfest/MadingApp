package com.example.madingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madingapp.Models.Response.MadingResponse;
import com.example.madingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileContentMadingAdapter extends RecyclerView.Adapter<ProfileContentMadingAdapter.ViewHolder> {

    private List<MadingResponse.Data> responses;
    private onClickAdapter onClickAdapter;

    public ProfileContentMadingAdapter(List<MadingResponse.Data> responses, onClickAdapter onClickAdapter) {
        this.responses = responses;
        this.onClickAdapter = onClickAdapter;
    }

    @NonNull
    @Override
    public ProfileContentMadingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileContentMadingAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_profile_content_mading, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileContentMadingAdapter.ViewHolder holder, int position) {
        MadingResponse.Data response = responses.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdapter.onClick(response);
            }
        });

        Picasso.get()
                .load(response.getMadingImage())
                .into(holder.imgMading);
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMading = itemView.findViewById(R.id.imgContentMading_Profile);
        }
    }

    public void setData(List<MadingResponse.Data> data) {
        responses.clear();
        responses.addAll(data);
        notifyDataSetChanged();
    }

    public interface onClickAdapter {
        void onClick(MadingResponse.Data data);
    }
}
