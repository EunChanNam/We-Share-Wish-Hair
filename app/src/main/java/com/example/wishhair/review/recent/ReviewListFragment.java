package com.example.wishhair.review.recent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.CustomTokenHandler;
import com.example.wishhair.R;
import com.example.wishhair.review.ReviewItem;
import com.example.wishhair.review.detail.ReviewDetailActivity;
import com.example.wishhair.review.write.WriteReviewActivity;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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

//    sort
    private static String sort_selected = null;
    private static final String[] sortItems = {"최신 순", "오래된 순", "좋아요 순"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.review_fragment_list, container, false);

        CustomTokenHandler customTokenHandler = new CustomTokenHandler(requireActivity());
        String accessToken = customTokenHandler.getAccessToken();
//       request List data
        ReviewListRequest(accessToken);
//        temp write button
//        TODO 임시 글쓰기 버튼, 나중에 삭제해야댐
        btn_temp_write = v.findViewById(R.id.temp_write_btn);
        btn_temp_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WriteReviewActivity.class);
                startActivity(intent);
            }
        });


        filter = v.findViewById(R.id.review_fragment_filter_radioGroup);
        filter_whole = v.findViewById(R.id.review_fragment_filter_whole);
        filter_man = v.findViewById(R.id.review_fragment_filter_man);
        filter_woman = v.findViewById(R.id.review_fragment_filter_woman);

        RecyclerView recentRecyclerView = v.findViewById(R.id.review_recent_recyclerView);
        recentReviewItems = new ArrayList<>();

        //===============================dummy data===============================
        for (int i=0;i<5;i++){
            ReviewItem newItem = new ReviewItem(R.drawable.user_sample, "현정" + " 님", "3" + " 개", "3.03", R.drawable.star_fill, R.drawable.heart_fill, " is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.", "3.8", false, 314+i, "22.05.13");
            recentReviewItems.add(newItem);
        }

        RecyclerViewAdapterRecent recentRecyclerViewAdapter = new RecyclerViewAdapterRecent(recentReviewItems);
        recentRecyclerView.setAdapter(recentRecyclerViewAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // TODO: 2023-03-12  나중에 아이템 클릭시 해당 게시글 이동 리스너로 활용
        recentRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapterRecent.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(v.getContext(), ReviewDetailActivity.class);
                startActivity(intent);
            }
        });

//        sort
        Spinner spinner_sort = v.findViewById(R.id.review_fragment_spinner_sort);
        sort_select(spinner_sort);
//        TODO 정렬 기준으로 받아오기


        return v;
    }

    private void ReviewListRequest(String accessToken) {
        final String URL_REVIEWLIST = UrlConst.URL + "/api/review";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_REVIEWLIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("reviewListRequest", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("reviewList error", error.toString());
            }
        }) { @Override
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
                Log.d("sort_selected", sort_selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

}