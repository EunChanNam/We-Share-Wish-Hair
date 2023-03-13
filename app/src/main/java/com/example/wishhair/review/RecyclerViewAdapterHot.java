package com.example.wishhair.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.R;

import java.util.ArrayList;

public class RecyclerViewAdapterHot extends RecyclerView.Adapter<RecyclerViewAdapterHot.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView title, description;

        ViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = itemView.findViewById(R.id.item_review_hot_imageView_Thumbnail);
            title = itemView.findViewById(R.id.item_review_hot_textView_title);
            description = itemView.findViewById(R.id.item_review_hot_textView_description);
        }
    }

    private ArrayList<ReviewItem> reviewItems = null;

    public RecyclerViewAdapterHot(ArrayList<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_item_review_hot, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = reviewItems.get(position);

        holder.thumbnailImageView.setImageResource(R.drawable.home);
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

}
