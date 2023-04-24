package com.example.wishhair.review.recent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.CustomTokenHandler;
import com.example.wishhair.R;
import com.example.wishhair.review.ReviewItem;
import com.example.wishhair.review.detail.ReviewDetailActivity;
import com.example.wishhair.review.write.WriteReviewActivity;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewListFragment extends Fragment {
// recyclerView 내용 업데이트 및 갱신
// https://kadosholy.tistory.com/55
// https://velog.io/@yamamamo/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%A0%84%ED%99%94%EB%B2%88%ED%98%B8%EB%B6%80%EC%95%B12-%EB%A6%AC%EC%82%AC%EC%9D%B4%ED%81%B4%EB%9F%AC%EB%B7%B0-%EC%95%84%EC%9D%B4%ED%85%9C-%ED%81%B4%EB%A6%AD-%EC%88%98%EC%A0%95-%EC%82%AD%EC%A0%9C

    public ReviewListFragment() {
        // Required empty public constructor
        }

    private ArrayList<ReviewItem> recentReviewItems;
    private RadioGroup filter;
    private RadioButton filter_whole, filter_man, filter_woman;
    private Button btn_temp_write;
    private RecyclerView recentRecyclerView;
    RecentAdapter recentAdapter;

    //    sort
    private static String sort_selected = null;
    private static final String[] sortItems = {"최신 순", "오래된 순", "좋아요 순"};

//    request
    String accessToken;

    @Override
    public void onResume() {
        super.onResume();

        //       request List data
        reviewListRequest(accessToken);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.review_fragment_recent, container, false);

        CustomTokenHandler customTokenHandler = new CustomTokenHandler(requireActivity());
        accessToken = customTokenHandler.getAccessToken();

//        temp write button
//        TODO 임시 글쓰기 버튼, 나중에 삭제해야댐
        btn_temp_write = v.findViewById(R.id.review_fragment_btn_write);
        btn_temp_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WriteReviewActivity.class);
                startActivity(intent);
            }
        });

//        review list
        recentReviewItems = new ArrayList<>();
        recentAdapter = new RecentAdapter(recentReviewItems, getContext());
        recentRecyclerView = v.findViewById(R.id.review_recent_recyclerView);
        recentRecyclerView.setAdapter(recentAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        //        swipeRefreshLayout
//        TODO 새로고침 제대로 구현 >>>> 지금 새로고침할때마다 똑같은거 다시 들어감
        SwipeRefreshLayout swipeRefreshLayout = v.findViewById(R.id.review_recent_swipeRefLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            reviewListRequest(accessToken);
            final Handler handler = new Handler();
            handler.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 500);
        });

        // TODO: 2023-03-12  해당 게시글 이동 리스너
        recentAdapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(v.getContext(), ReviewDetailActivity.class);
                ReviewItem selectedItem = recentReviewItems.get(position);
                intent.putExtra("userNickname", selectedItem.getUserNickName());
                intent.putExtra("hairStyleName", selectedItem.getHairStyleName());
                intent.putStringArrayListExtra("tags", selectedItem.getTags());
                intent.putExtra("score", selectedItem.getScore());
                intent.putExtra("likes", selectedItem.getLikes());
                intent.putExtra("date", selectedItem.getCreatedDate());
                intent.putExtra("content", selectedItem.getContent());
                intent.putStringArrayListExtra("imageUrls", selectedItem.getImageUrls());
                startActivity(intent);
            }

        });

//        sort
        filter = v.findViewById(R.id.review_fragment_filter_radioGroup);
        filter_whole = v.findViewById(R.id.review_fragment_filter_whole);
        filter_man = v.findViewById(R.id.review_fragment_filter_man);
        filter_woman = v.findViewById(R.id.review_fragment_filter_woman);

        Spinner spinner_sort = v.findViewById(R.id.review_fragment_spinner_sort);
        sort_select(spinner_sort);
//        TODO 정렬 기준으로 받아오기

        return v;
    }


    @SuppressLint("NotifyDataSetChanged")
    private void reviewListRequest(String accessToken) {
        final String URL_REVIEWLIST = UrlConst.URL + "/api/review";
        List<ReviewItem> requestItems = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_REVIEWLIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("reviewListRequest", response.toString());
//                parse received data
                String stringResponse = String.valueOf(response);
                try {
                    JSONObject jsonObject = new JSONObject(stringResponse);
                    JSONArray resultArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.length(); i++) {
                        ReviewItem receivedData = new ReviewItem();
                        JSONObject resultObject = resultArray.getJSONObject(i);
//                        Log.d("resultObject", resultObject.toString());
                        String userNickName = resultObject.getString("userNickname");
                        String score = resultObject.getString("score");
                        int likes = resultObject.getInt("likes");
                        String content = resultObject.getString("contents");
                        String createDate = resultObject.getString("createdDate");
                        String hairStyleName = resultObject.getString("hairStyleName");

                        JSONArray hashTagsArray = resultObject.getJSONArray("hashTags");
                        ArrayList<String> tags = new ArrayList<>();
                        for (int j = 0; j < hashTagsArray.length(); j++) {
                            JSONObject hasTagObject = hashTagsArray.getJSONObject(j);
                            tags.add(hasTagObject.getString("tag"));
                        }
                        receivedData.setTags(tags);

                        receivedData.setUserNickName(userNickName);
                        receivedData.setScore(score);
                        receivedData.setLikes(likes);
                        receivedData.setCreatedDate(createDate);
                        receivedData.setHairStyleName(hairStyleName);
                        receivedData.setContent(content);

                        JSONArray photosArray = resultObject.getJSONArray("photos");
                        ArrayList<String> receivedUrls = new ArrayList<>();
                        for (int j = 0; j < photosArray.length(); j++) {
                            JSONObject photoObject = photosArray.getJSONObject(j);
                            String imageUrl = photoObject.getString("storeUrl");

                            receivedUrls.add(imageUrl);
                        }
                        receivedData.setImageUrls(receivedUrls);

                        requestItems.add(receivedData);
                    }

                    JSONObject pagingObject = jsonObject.getJSONObject("paging");
                    String contentSize = pagingObject.getString("contentSize");
                    String page = pagingObject.getString("page");
                    String hasNext = pagingObject.getString("hasNext");
                    Log.d("paging", contentSize + " " + page + " " + hasNext);

                    recentReviewItems.clear();
                    recentReviewItems.addAll(requestItems);
                    recentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> Log.e("reviewList error", error.toString())) { @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void sort_select(Spinner spinner_sort) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sortItems);
        spinner_sort.setAdapter(spinnerAdapter);

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sort_selected = sortItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}
