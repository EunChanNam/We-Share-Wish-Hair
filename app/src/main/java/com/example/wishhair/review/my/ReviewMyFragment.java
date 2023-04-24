package com.example.wishhair.review.my;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.R;
import com.example.wishhair.review.detail.MyReviewDetailActivity;
import com.example.wishhair.review.detail.RecentReviewDetailActivity;
import com.example.wishhair.review.ReviewItem;

import java.util.ArrayList;

public class ReviewMyFragment extends Fragment {

    public ReviewMyFragment() {}

    ArrayList<ReviewItem> myReviewItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.review_fragment_my, container, false);

        RecyclerView myRecyclerView = v.findViewById(R.id.review_my_recyclerView);
        myReviewItems = new ArrayList<>();

        //===============================dummy data===============================
        ArrayList<String> images = new ArrayList<>();
        images.add("https://wswh-storage.kr.object.ncloudstorage.com/d3f61f7f-f283-4867-a98d-a4e20ed3ea46.jpg");
        images.add("https://wswh-storage.kr.object.ncloudstorage.com/d3f61f7f-f283-4867-a98d-a4e20ed3ea46.jpg");
        for (int i = 0; i < 4; i++) {
            ArrayList<String> tempTags = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                tempTags.add("#tag ");
            }
            tempTags.add("tags");
            ReviewItem newItem = new ReviewItem(images, "무슨무슨" + "펌", tempTags,
                    "is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.",
                    "3.4", 500, "22.03.12");
            myReviewItems.add(newItem);
        }

        MyReviewAdapter myReviewAdapter = new MyReviewAdapter(myReviewItems);
        myRecyclerView.setAdapter(myReviewAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // TODO: 2023-03-13  나중에 아이템 클릭시 해당 게시글 이동 리스너로 활용
        myReviewAdapter.setOnItemClickListener((v1, position) -> {
            Intent intent = new Intent(v1.getContext(), MyReviewDetailActivity.class);
            ReviewItem selectedItem = myReviewItems.get(position);
            intent.putExtra("hairStyleName", selectedItem.getHairStyleName());
            intent.putStringArrayListExtra("tags", selectedItem.getTags());
            intent.putExtra("score", selectedItem.getScore());
            intent.putExtra("likes", selectedItem.getLikes());
            intent.putExtra("date", selectedItem.getCreatedDate());
            intent.putExtra("content", selectedItem.getContent());
            intent.putStringArrayListExtra("imageUrls", selectedItem.getImageUrls());
            startActivity(intent);
        });

        return v;
    }
}
