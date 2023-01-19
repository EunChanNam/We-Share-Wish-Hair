package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RecommendFragment extends Fragment {

    Button btn_submitPicture, btn_start;

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend, container, false);

        btn_submitPicture = v.findViewById(R.id.reco_btn_submitPicture);

        btn_start = v.findViewById(R.id.reco_btn_start);

        return v;
    }
}