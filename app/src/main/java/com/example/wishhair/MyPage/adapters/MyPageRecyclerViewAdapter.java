package com.example.wishhair.MyPage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.MyPage.items.HeartlistItem;
import com.example.wishhair.R;

import java.util.ArrayList;

public class MyPageRecyclerViewAdapter extends RecyclerView.Adapter<MyPageRecyclerViewAdapter.ViewHolder>{
    private ArrayList<HeartlistItem> heartlistItems = new ArrayList<HeartlistItem>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView HeartlistPicture;
        public TextView HeartlistGrade, HeartlistHeartcount;

        ViewHolder(View view) {
            super(view);

            HeartlistPicture = view.findViewById(R.id.heartlist_my_imageView_picture);
            HeartlistGrade = view.findViewById(R.id.heartlist_my_tv_grade);
            HeartlistHeartcount = view.findViewById(R.id.heartlist_my_tv_heartCount);
        }
    }

    @NonNull
    @Override
    public MyPageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.heartlist_item, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeartlistItem item = heartlistItems.get(position);
        holder.HeartlistPicture.setImageResource(R.drawable.home);
    }

    @Override
    public int getItemCount() {
        return heartlistItems.size();
    }
    public void addItem(HeartlistItem e) {
        heartlistItems.add(e);
    }
}

