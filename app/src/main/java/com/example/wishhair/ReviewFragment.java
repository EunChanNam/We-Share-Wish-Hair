package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.recyclerView.RecyclerViewAdapterHot;
import com.example.wishhair.recyclerView.RecyclerViewAdapterRecent;
import com.example.wishhair.recyclerView.RecyclerViewItem;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {
        // Required empty public constructor
    }

    private RecyclerView hotRecyclerView, recentRecyclerView;
    private ArrayList<RecyclerViewItem> hotRecyclerViewItems, recentRecyclerViewItems;
    private RecyclerViewAdapterHot hotRecyclerViewAdapter;
    private RecyclerViewAdapterRecent recentRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        hotRecyclerView = v.findViewById(R.id.review_recyclerView_hot);
        hotRecyclerViewItems = new ArrayList<>();
        recentRecyclerView = v.findViewById(R.id.review_recyclerView_recent);
        recentRecyclerViewItems = new ArrayList<>();

        //===============================dummy data===============================
        for (int i=0;i<5;i++){
            addItemHot("thumbnail", "title" + i, "description");
        }

        for (int i=0;i<5;i++){
            addItemRecent("thumbnail", "title" + i, "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription");
        }

        hotRecyclerViewAdapter = new RecyclerViewAdapterHot(hotRecyclerViewItems);
        hotRecyclerView.setAdapter(hotRecyclerViewAdapter);
        hotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        recentRecyclerViewAdapter = new RecyclerViewAdapterRecent(recentRecyclerViewItems);
        recentRecyclerView.setAdapter(recentRecyclerViewAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return v;
    }
    public void addItemHot(String thumbnail, String title, String description) {
        RecyclerViewItem item = new RecyclerViewItem();

        item.setThumbnail(thumbnail);
        item.setTitle(title);
        item.setDescription(description);

        hotRecyclerViewItems.add(item);
    }
    public void addItemRecent(String thumbnail, String title, String description) {
        RecyclerViewItem item = new RecyclerViewItem();

        item.setThumbnail(thumbnail);
        item.setTitle(title);
        item.setDescription(description);

        recentRecyclerViewItems.add(item);
    }
}