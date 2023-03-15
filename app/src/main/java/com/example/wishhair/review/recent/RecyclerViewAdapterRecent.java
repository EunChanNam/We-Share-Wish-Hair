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

import com.example.wishhair.R;
import com.example.wishhair.review.ReviewItem;

import java.util.ArrayList;

public class RecyclerViewAdapterRecent extends RecyclerView.Adapter<RecyclerViewAdapterRecent.ViewHolder> {

    private final ArrayList<ReviewItem> reviewItems;

    public RecyclerViewAdapterRecent(ArrayList<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
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

        View view = inflater.inflate(R.layout.recycler_item_review_recent, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = reviewItems.get(position);

        holder.profileImage.setImageResource(item.getProfileImage());
        holder.contentImage1.setImageResource(item.getContentImage1());
        holder.contentImage2.setImageResource(item.getContentImage2());
        holder.nickname.setText(item.getNickname());
        holder.authorAvgGrade.setText(item.getAuthorAvgGrade());
        holder.authorReviewCount.setText(item.getAuthorReviewCount());
        holder.content.setText(item.getContent());
        holder.grade.setText(item.getGrade());
        holder.date.setText(item.getDate());
        if (item.getIsHeart()){
            holder.isHeart.setImageResource(R.drawable.heart_fill);
        } else {
            holder.isHeart.setImageResource(R.drawable.heart_empty);
        }
        holder.heartCount.setText(String.valueOf(item.getHeartCount()));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage, contentImage1, contentImage2, isHeart;
        TextView nickname, authorReviewCount, authorAvgGrade, content, grade, date, heartCount;
        Button viewContent;

        ViewHolder(View itemView) {
            super(itemView);
            this.profileImage = itemView.findViewById(R.id.review_recent_profile_image);
            this.contentImage1 = itemView.findViewById(R.id.review_recent_contentImage1);
            this.contentImage2 = itemView.findViewById(R.id.review_recent_contentImage2);
            this.nickname = itemView.findViewById(R.id.review_recent_tv_name);
            this.authorAvgGrade = itemView.findViewById(R.id.review_recent_tv_authorAvgGrade);
            this.authorReviewCount = itemView.findViewById(R.id.review_recent_tv_reviewCount);
            this.content = itemView.findViewById(R.id.review_recent_tv_content);
            this.grade = itemView.findViewById(R.id.review_recent_tv_grade);
            this.isHeart = itemView.findViewById(R.id.review_recent_imageView_isHeart);
            this.heartCount = itemView.findViewById(R.id.review_recent_tv_heartCount);
            this.date = itemView.findViewById(R.id.review_recent_tv_date);
            this.viewContent = itemView.findViewById(R.id.review_recent_Button_viewContent);
        }
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
}
