package com.example.madingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madingapp.Models.Response.MadingResponse;
import com.example.madingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotTopicsAdapter extends RecyclerView.Adapter<HotTopicsAdapter.ViewHolder> {

    private List<MadingResponse.Data> responses;
    private onClickAdapter onClickAdapter;

    public HotTopicsAdapter(List<MadingResponse.Data> responses, HotTopicsAdapter.onClickAdapter onClickAdapter) {
        this.responses = responses;
        this.onClickAdapter = onClickAdapter;
    }

    public HotTopicsAdapter(List<MadingResponse.Data> responses) {
        this.responses = responses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hot_topics, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MadingResponse.Data response = responses.get(position);
        holder.tvTopRank.setText("#" + (position + 1));
        holder.tvTitle.setText(response.getTitle());
        holder.tvCreatedDate.setText(response.getCreatedDate());
        holder.tvDescription.setText(response.getDescription());
        holder.tvAuthor.setText("Author: " + response.getAuthors());

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
        TextView tvTitle, tvDescription, tvAuthor, tvCreatedDate, tvTopRank;
        ImageView imgMading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopRank = itemView.findViewById(R.id.tvRankHotTopics_listHotTopics);
            tvAuthor = itemView.findViewById(R.id.tvAuthor_listHotTopics);
            tvDescription = itemView.findViewById(R.id.tvDescription_listHotTopics);
            tvTitle = itemView.findViewById(R.id.tvTitle_listHotTopics);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate_listHotTopics);
            imgMading = itemView.findViewById(R.id.imageView_listHotTopics);
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
