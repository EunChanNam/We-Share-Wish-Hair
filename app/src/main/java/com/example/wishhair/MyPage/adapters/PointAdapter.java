package com.example.wishhair.MyPage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.MyPage.items.PointItem;
import com.example.wishhair.R;

import java.util.ArrayList;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {
    private ArrayList<PointItem> pointItems = new ArrayList<PointItem>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView PointlistTitle, PointlistNum, PointlistDate;

        ViewHolder(View view) {
            super(view);

            PointlistTitle = view.findViewById(R.id.point_title);
            PointlistNum = view.findViewById(R.id.point_num);
            PointlistDate = view.findViewById(R.id.point_date);
        }
    }

    @NonNull
    @Override
    public PointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.point_item, parent, false);
        return new PointAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PointItem item = pointItems.get(position);
    }

    @Override
    public int getItemCount() {
        return pointItems.size();
    }

    public void addItem(PointItem e) {
        pointItems.add(e);

    }



}
