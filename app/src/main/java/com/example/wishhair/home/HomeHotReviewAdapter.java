package com.example.wishhair.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.R;

import java.util.ArrayList;

public class HomeHotReviewAdapter extends RecyclerView.Adapter<HomeHotReviewAdapter.HotViewHolder> {
    private final ArrayList<HomeItems> items;

    public HomeHotReviewAdapter( ArrayList<HomeItems> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_review_hot, parent, false);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotViewHolder holder, int position) {
        HomeItems item = items.get(position);

        holder.userName.setText(item.getUsername());
        holder.context_review.setText(item.getContext_review());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class HotViewHolder extends RecyclerView.ViewHolder {
        TextView userName, context_review;
        public HotViewHolder(@NonNull View itemView) {
            super(itemView);

            this.userName = itemView.findViewById(R.id.home_item_review_userName);
            this.context_review = itemView.findViewById(R.id.home_item_review_hot_content);
        }
    }
}
