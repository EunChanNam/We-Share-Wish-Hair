package com.example.wishhair.review.my;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.R;
import com.example.wishhair.review.recent.RecentAdapter;
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

        for (int i = 0; i < 4; i++) {
            ReviewItem newItem = new ReviewItem(R.drawable.user_sample, "무슨무슨" + "펌", new ArrayList<>(),
                    "is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.",
                    "3.4", 500, "22.03.12", false);
            myReviewItems.add(newItem);
        }

        RecyclerViewAdapterMy recyclerViewAdapterMy = new RecyclerViewAdapterMy(myReviewItems);
        myRecyclerView.setAdapter(recyclerViewAdapterMy);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // TODO: 2023-03-13  나중에 아이템 클릭시 해당 게시글 이동 리스너로 활용
        recyclerViewAdapterMy.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });

        return v;
    }
}
