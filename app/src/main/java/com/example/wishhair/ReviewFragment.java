package com.example.wishhair;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wishhair.recyclerView.RecyclerViewAdapterRecent;
import com.example.wishhair.recyclerView.RecyclerViewItem;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {
// recyclerView 내용 업데이트 및 갱신
// https://kadosholy.tistory.com/55
// https://velog.io/@yamamamo/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%A0%84%ED%99%94%EB%B2%88%ED%98%B8%EB%B6%80%EC%95%B12-%EB%A6%AC%EC%82%AC%EC%9D%B4%ED%81%B4%EB%9F%AC%EB%B7%B0-%EC%95%84%EC%9D%B4%ED%85%9C-%ED%81%B4%EB%A6%AD-%EC%88%98%EC%A0%95-%EC%82%AD%EC%A0%9C

    public ReviewFragment() {
        // Required empty public constructor
        }

    ArrayList<RecyclerViewItem> recentRecyclerViewItems;
    RadioGroup filter;
    RadioButton filter_whole, filter_man, filter_woman;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        filter = v.findViewById(R.id.review_fragment_filter_radioGroup);
        filter_whole = v.findViewById(R.id.review_fragment_filter_whole);
        filter_man = v.findViewById(R.id.review_fragment_filter_man);
        filter_man = v.findViewById(R.id.review_fragment_filter_woman);

        RecyclerView recentRecyclerView = v.findViewById(R.id.review_recyclerView_recent);
        recentRecyclerViewItems = new ArrayList<>();

        //===============================dummy data===============================
        for (int i=0;i<5;i++){
            RecyclerViewItem newItem = new RecyclerViewItem(R.drawable.user_sample, "현정" + " 님", "3" + " 개", "3.03", R.drawable.star_fill, R.drawable.heart_fill, " is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.", "3.8", false, 314+i, "22.05.13");
            recentRecyclerViewItems.add(newItem);
        }

        RecyclerViewAdapterRecent recentRecyclerViewAdapter = new RecyclerViewAdapterRecent(recentRecyclerViewItems);
        recentRecyclerView.setAdapter(recentRecyclerViewAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // TODO: 2023-03-12  나중에 아이템 클릭시 해당 게시글 이동 리스너로 활용
        recentRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapterRecent.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
        return v;
    }
   /* public void addItemRecent(String profileImage, String nickname, String authorReviewCount, String authorAvgGrade, String contentImage1, String contentImage2, String content, String grade,boolean isHeart, int heartCount, String date) {
        RecyclerViewItem item = new RecyclerViewItem();

        item.setProfileImage(profileImage);
        item.setNickname(nickname);
        item.setAuthorReviewCount(authorReviewCount);
        item.setAuthorAvgGrade(authorAvgGrade);
        item.setContentImage1(contentImage1);
        item.setContentImage2(contentImage2);
        item.setContent(content);
        item.setGrade(grade);
        item.setHeartCount(heartCount);
        item.setIsHeart(false);
        item.setDate(date);

        recentRecyclerViewItems.add(item);
    }*/
}