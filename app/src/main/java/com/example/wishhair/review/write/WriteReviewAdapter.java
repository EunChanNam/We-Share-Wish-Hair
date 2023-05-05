package com.example.wishhair.review.write;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wishhair.R;

import java.util.ArrayList;

public class WriteReviewAdapter extends RecyclerView.Adapter<WriteReviewAdapter.WriteViewHolder> {

    private ArrayList<Uri> items;
    private final Context context;

    public WriteReviewAdapter(ArrayList<Uri> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public WriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_write_picture, parent, false);
        return new WriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteViewHolder viewHolder, int position) {
        Uri imageURI = items.get(position);

        Glide.with(context).load(imageURI).into(viewHolder.imageView);

        viewHolder.btn_del.setOnClickListener(view -> {
            int position1 = viewHolder.getAdapterPosition();
            deleteItems(position1);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class WriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton btn_del;
        WriteViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.review_item_write_imageview);
            btn_del = itemView.findViewById(R.id.review_item_write_btn_delete);
        }
    }

    public void deleteItems(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }
}
