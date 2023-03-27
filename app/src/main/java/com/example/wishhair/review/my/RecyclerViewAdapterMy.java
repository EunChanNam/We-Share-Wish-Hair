package com.example.wishhair.review.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.R;
import com.example.wishhair.review.recent.RecyclerViewAdapterRecent;
import com.example.wishhair.review.ReviewItem;

import java.util.ArrayList;

public class RecyclerViewAdapterMy extends RecyclerView.Adapter<RecyclerViewAdapterMy.ViewHolder> {
    private ArrayList<ReviewItem> reviewItems;

    public RecyclerViewAdapterMy(ArrayList<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
    }

// 목록 클릭 시 내용으로 이동
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private RecyclerViewAdapterRecent.OnItemClickListener mListener = null;
    public void setOnItemClickListener(RecyclerViewAdapterRecent.OnItemClickListener listener) {
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

        holder.hairImage.setImageResource(item.getHairImage());
        holder.hairStyle.setText(item.getHairStyle());
        holder.tags.setText(item.getTags());
        holder.grade.setText(item.getGrade());
        holder.heartCount.setText(String.valueOf(item.getHeartCount()));
        holder.content.setText(item.getContent());
        holder.date.setText(item.getDate());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hairImage;
        TextView hairStyle, tags, grade, heartCount, content, date, isPoint;

        ViewHolder(View itemView) {
            super(itemView);
            this.hairImage = itemView.findViewById(R.id.hairStyleImage);
            this.hairStyle = itemView.findViewById(R.id.review_my_tv_hairStyle);
            this.tags = itemView.findViewById(R.id.review_my_tags);
            this.grade = itemView.findViewById(R.id.review_my_tv_grade);
            this.heartCount = itemView.findViewById(R.id.review_my_tv_heartCount);
            this.content = itemView.findViewById(R.id.review_my_tv_content);
            this.date = itemView.findViewById(R.id.review_my_tv_date);
            this.isPoint = itemView.findViewById(R.id.review_my_tv_isPoint);
        }
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
}

