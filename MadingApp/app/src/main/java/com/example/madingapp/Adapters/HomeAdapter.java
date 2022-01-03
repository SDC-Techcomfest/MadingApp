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

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<MadingResponse.Data> responses;
    private onClickAdapter onClickAdapter;

    public HomeAdapter(List<MadingResponse.Data> responses, onClickAdapter onClickAdapter) {
        this.responses = responses;
        this.onClickAdapter = onClickAdapter;
    }

    public HomeAdapter(List<MadingResponse.Data> responses) {
        this.responses = responses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MadingResponse.Data response = responses.get(position);

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
        TextView tvTitle, tvDescription, tvAuthor, tvCreatedDate;
        ImageView imgMading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor_listHome);
            tvDescription = itemView.findViewById(R.id.tvDescription_listHome);
            tvTitle = itemView.findViewById(R.id.tvTitle_listHome);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate_listHome);
            imgMading = itemView.findViewById(R.id.imageView_listHome);
        }
    }

    public void setData(List<MadingResponse.Data> data) {
        responses.clear();
        responses.addAll(data);
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<MadingResponse.Data> filters) {
        responses = filters;
        notifyDataSetChanged();
    }

    public interface onClickAdapter {
        void onClick(MadingResponse.Data data);
    }
}
