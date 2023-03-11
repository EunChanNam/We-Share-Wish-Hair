package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.recyclerView.RecyclerViewAdapterRecent;
import com.example.wishhair.recyclerView.RecyclerViewItem;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {
        // Required empty public constructor
    }

    private ArrayList<RecyclerViewItem> recentRecyclerViewItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        RecyclerView recentRecyclerView = v.findViewById(R.id.review_recyclerView_recent);
        recentRecyclerViewItems = new ArrayList<>();

        //===============================dummy data===============================


        for (int i=0;i<5;i++){
            addItemRecent(" ", "현정" + " 님", "3" + " 개", "3.03", " ", " ", " is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.", "3.8", "314", "22.05.13");
        }

        RecyclerViewAdapterRecent recentRecyclerViewAdapter = new RecyclerViewAdapterRecent(recentRecyclerViewItems);
        recentRecyclerView.setAdapter(recentRecyclerViewAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return v;
    }

    public void addItemRecent(String profileImage, String nickname, String ARC, String AAG, String CI1, String CI2, String content, String grade, String heart, String date) {
        RecyclerViewItem item = new RecyclerViewItem();

        item.setProfileImage(profileImage);
        item.setNickname(nickname);
        item.setAuthorReviewCount(ARC);
        item.setAuthorAvgGrade(AAG);
        item.setContentImage1(CI1);
        item.setContentImage2(CI2);
        item.setContent(content);
        item.setGrade(grade);
        item.setHeart(heart);
        item.setDate(date);

        recentRecyclerViewItems.add(item);
    }
}