package com.example.wishhair.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.MyPage.items.PointItem;
import com.example.wishhair.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<FavoriteItem> FavoriteItems = new ArrayList<FavoriteItem>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView FavoriteStyleName, FavoriteHeartCount;
        public ImageView FavoriteStyleImage;

        ViewHolder(View view) {
            super(view);

            FavoriteStyleImage = view.findViewById(R.id.favorite_style_image);
            FavoriteStyleName = view.findViewById(R.id.favorite_style_name);
            FavoriteHeartCount = view.findViewById(R.id.favorite_heart_count);
        }
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteItem item = FavoriteItems.get(position);
        holder.FavoriteStyleName.setText(item.getFavoriteStyleName());
    }

    @Override
    public int getItemCount() {
        return FavoriteItems.size();
    }

    public void addItem(FavoriteItem e) {
        FavoriteItems.add(e);

    }



}
