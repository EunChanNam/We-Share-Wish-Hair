package com.example.wishhair.review.recent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wishhair.R;
import com.example.wishhair.review.ReviewItem;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private final ArrayList<ReviewItem> reviewItems;
    private final Context context;

    public RecentAdapter(ArrayList<ReviewItem> reviewItems, Context context) {
        this.reviewItems = reviewItems;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.review_recycler_item_recent, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = reviewItems.get(position);

        if (item.getImageUrls().size() > 0) {
            holder.bindContentImage(item.getImageUrls().get(0));
        }

        holder.hairStyleName.setText(item.getHairStyleName());
        holder.tags.setText(item.getTags());
        holder.nickname.setText(item.getUserNickName());
        holder.grade.setText(item.getScore());
        holder.date.setText(item.getCreatedDate());
        if (item.getIsHeart()){
            holder.isHeart.setImageResource(R.drawable.heart_fill);
        } else {
            holder.isHeart.setImageResource(R.drawable.heart_empty);
        }
        holder.heartCount.setText(String.valueOf(item.getLikes()));
        holder.viewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(view, position);
                    }
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImage, isHeart;
        TextView nickname, hairStyleName, tags,grade, date, heartCount;
        Button viewContent;

        ViewHolder(View itemView) {
            super(itemView);
            this.contentImage = itemView.findViewById(R.id.review_recent_contentImage);
            this.nickname = itemView.findViewById(R.id.review_recent_tv_nickname);
            this.hairStyleName = itemView.findViewById(R.id.review_recent_tv_hairStyleName);
            this.tags = itemView.findViewById(R.id.review_recent_tv_tags);
            this.grade = itemView.findViewById(R.id.review_recent_tv_grade);
            this.isHeart = itemView.findViewById(R.id.review_recent_imageView_isHeart);
            this.heartCount = itemView.findViewById(R.id.review_recent_tv_heartCount);
            this.date = itemView.findViewById(R.id.review_recent_tv_date);
            this.viewContent = itemView.findViewById(R.id.review_recent_btn_viewContent);
        }

        public void bindContentImage(String imageUrl) {
            Glide.with(context).load(imageUrl).into(contentImage);
        }

    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
}
