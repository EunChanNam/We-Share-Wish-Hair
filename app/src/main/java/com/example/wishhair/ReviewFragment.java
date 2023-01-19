package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.recyclerView.RecyclerViewAdapter;
import com.example.wishhair.recyclerView.RecyclerViewItem;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView_hot;
    private ArrayList<RecyclerViewItem> recyclerViewItems;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView_hot = v.findViewById(R.id.review_recyclerView_hot);
        recyclerViewItems = new ArrayList<>();

        for (int i=0;i<5;i++){
            addItem("thumbnail", "title" + i, "description");
        }
        recyclerViewAdapter = new RecyclerViewAdapter(recyclerViewItems);
        recyclerView_hot.setAdapter(recyclerViewAdapter);
        recyclerView_hot.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        return v;
    }
    public void addItem(String thumbnail, String title, String description) {
        RecyclerViewItem item = new RecyclerViewItem();

        item.setThumbnail(thumbnail);
        item.setTitle(title);
        item.setDescription(description);

        recyclerViewItems.add(item);
    }
}