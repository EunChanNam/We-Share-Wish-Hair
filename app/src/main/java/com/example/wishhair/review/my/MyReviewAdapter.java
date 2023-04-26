package com.example.wishhair.review.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.R;
import com.example.wishhair.review.ReviewItem;

import java.util.ArrayList;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {
    private final ArrayList<ReviewItem> reviewItems;

    public MyReviewAdapter(ArrayList<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
    }

// 목록 클릭 시 내용으로 이동
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

        View view = inflater.inflate(R.layout.review_recycler_item_my, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = reviewItems.get(position);

        holder.hairStyle.setText(item.getHairStyleName());
        StringBuilder tags = new StringBuilder();
        for (int i = 0; i < item.getTags().size(); i++) {
            tags.append(item.getTags().get(i));
        }
        holder.tags.setText(tags);
        holder.grade.setText(item.getScore());
        holder.heartCount.setText(String.valueOf(item.getLikes()));
        holder.date.setText(item.getCreatedDate());
        holder.viewContent.setOnClickListener(view -> {
            int position1 = holder.getAdapterPosition();
            if (position1 != RecyclerView.NO_POSITION) {
                if (mListener != null) {
                    mListener.onItemClick(view, position1);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hairStyle, tags, grade, heartCount, date;
        Button viewContent;

        ViewHolder(View itemView) {
            super(itemView);
            this.hairStyle = itemView.findViewById(R.id.review_my_tv_hairStyleName);
            this.tags = itemView.findViewById(R.id.review_my_tv_tags);
            this.grade = itemView.findViewById(R.id.review_my_tv_grade);
            this.heartCount = itemView.findViewById(R.id.review_my_tv_heartCount);
            this.date = itemView.findViewById(R.id.review_my_tv_date);
            this.viewContent = itemView.findViewById(R.id.review_my_btn_viewContent);
        }
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
}
