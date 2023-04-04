package com.example.wishhair.MyPage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishhair.MyPage.PointHistory;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import java.util.ArrayList;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {
    private ArrayList<PointHistory> pointItems = new ArrayList<PointHistory>();
    final static private String point_url = UrlConst.URL + "/api/my_page";
    private Context context;

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
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PointHistory item = pointItems.get(position);
    }

    @Override
    public int getItemCount() {
        return pointItems.size();
    }

    public void addItem(PointHistory e) {
        pointItems.add(e);
    }
    public void setItems(ArrayList<PointHistory> items) {
        this.pointItems = items;
    }

}
