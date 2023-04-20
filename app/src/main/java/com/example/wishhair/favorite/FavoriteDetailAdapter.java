package com.example.wishhair.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wishhair.R;

public class FavoriteDetailAdapter extends RecyclerView.Adapter<FavoriteDetailAdapter.ViewHolder> {
    private Context context;
    private String[] sliderImage;

    public FavoriteDetailAdapter(Context context, String[] sliderImage) {
        this.context = context;
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public FavoriteDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favoirte_detail_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.favorite_detail_slider);
        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(mImageView);
        }
    }
}
