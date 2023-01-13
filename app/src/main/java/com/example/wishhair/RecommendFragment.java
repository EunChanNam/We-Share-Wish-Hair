package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecommendFragment extends Fragment {

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);

        TextView topBarTextView = (TextView) v.findViewById(R.id.topBarTextView);
        topBarTextView.setText("AI 추천받기");

        return v;
    }
}