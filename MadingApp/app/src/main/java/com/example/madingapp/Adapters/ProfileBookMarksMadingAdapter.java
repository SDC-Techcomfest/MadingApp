package com.example.madingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madingapp.Models.Response.BookMarkResponse;
import com.example.madingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileBookMarksMadingAdapter extends RecyclerView.Adapter<ProfileBookMarksMadingAdapter.ViewHolder> {

    private List<BookMarkResponse.Bookmark> responses;
    private onClickAdapter onClickAdapter;

    public ProfileBookMarksMadingAdapter(List<BookMarkResponse.Bookmark> responses, onClickAdapter onClickAdapter) {
        this.responses = responses;
        this.onClickAdapter = onClickAdapter;
    }

    @NonNull
    @Override
    public ProfileBookMarksMadingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileBookMarksMadingAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_profile_book_marks_mading, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileBookMarksMadingAdapter.ViewHolder holder, int position) {
        BookMarkResponse.Bookmark response = responses.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdapter.onClick(response);
            }
        });

        Picasso.get()
                .load(response.getMading().getMadingImage())
                .into(holder.imgProfileContentMading);
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfileContentMading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfileContentMading = itemView.findViewById(R.id.imgBookmarks_Profile);
        }
    }

    public void setData(List<BookMarkResponse.Bookmark> data) {
        responses.clear();
        responses.addAll(data);
        notifyDataSetChanged();
    }

    public interface onClickAdapter {
        void onClick(BookMarkResponse.Bookmark data);
    }
}
