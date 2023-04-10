package com.example.wishhair.review.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wishhair.R;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {

    private final Context context;
//    private final String[] sliderImage;
    private final List<Uri> uriImages;

    /*public ImageSliderAdapter(Context context, String[] sliderImage) {
        this.context = context;
        this.sliderImage = sliderImage;
    }*/

    public ImageSliderAdapter(Context context, List<Uri> uriImages) {
        this.context = context;
        this.uriImages = uriImages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_slider_item_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.bindSliderImage(sliderImage[position]);
//        holder.bindBitmapImage(uriImages.get(position));
        holder.bindUriImage(uriImages.get(position));
    }

    @Override
    public int getItemCount() {
//        return sliderImage.length;
        return uriImages.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageSlide_review_detail);
        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context).load(imageURL).into(mImageView);
        }

        public void bindBitmapImage(Bitmap image) {
            Glide.with(context).load(image).into(mImageView);
        }

        public void bindUriImage(Uri imageUri) {
            Glide.with(context).load(imageUri).into(mImageView);
        }
    }
}
