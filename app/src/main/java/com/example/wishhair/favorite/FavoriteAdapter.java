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

    public interface OnItemClickListener {
        void onItemClicked(int position, String data);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView FavoriteStyleName, FavoriteHeartCount;
        public ImageView FavoriteStyleImage;

        public TextView getFavoriteStyleName() {
            return FavoriteStyleName;
        }

        ViewHolder(View view) {
            super(view);

            FavoriteStyleImage = view.findViewById(R.id.favorite_style_image);
            FavoriteStyleName = view.findViewById(R.id.favorite_style_name);
            FavoriteHeartCount = view.findViewById(R.id.favorite_heart_count);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String data = "";
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        data = getFavoriteStyleName().getText().toString();
                    }
                    itemClickListener.onItemClicked(pos, data);
                }
            });
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
