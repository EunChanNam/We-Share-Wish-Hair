package com.example.wishhair.recyclerView;

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

public class RecyclerViewAdapterRecent extends RecyclerView.Adapter<RecyclerViewAdapterRecent.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage, contentImage1, contentImage2;
        TextView nickname, authorReviewCount, authorAvgGrade, content, grade, heart;
        ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.review_recent_profile_image);
            contentImage1 = itemView.findViewById(R.id.review_recent_contentImage1);
            contentImage2 = itemView.findViewById(R.id.review_recent_contentImage2);
            nickname = itemView.findViewById(R.id.review_recent_tv_name);
            authorAvgGrade = itemView.findViewById(R.id.review_recent_tv_authorAvgGrade);
            authorReviewCount = itemView.findViewById(R.id.review_recent_tv_reviewCount);
            content = itemView.findViewById(R.id.review_recent_tv_content);
            grade = itemView.findViewById(R.id.review_recent_tv_grade);
            heart = itemView.findViewById(R.id.review_recent_imageBtn_heart);
        }
    }

    private ArrayList<RecyclerViewItem> recyclerViewItems = null;

    public RecyclerViewAdapterRecent(ArrayList<RecyclerViewItem> recyclerViewItems) {
        this.recyclerViewItems = recyclerViewItems;
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
        RecyclerViewItem item = recyclerViewItems.get(position);

        holder.profileImage.setImageResource(R.drawable.user_sample);

    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

}
