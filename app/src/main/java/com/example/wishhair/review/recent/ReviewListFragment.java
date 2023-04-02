package com.example.wishhair.review.recent;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    RecyclerViewAdapterRecent listAdapter;

    //    img

    //    sort
    private static String sort_selected = null;
    private static final String[] sortItems = {"최신 순", "오래된 순", "좋아요 순"};

//    request
    String accessToken;

    @Override
    public void onResume() {
        super.onResume();

        //       request List data
        ReviewListRequest(accessToken);

        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recentRecyclerView.setAdapter(listAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.review_fragment_list, container, false);

        CustomTokenHandler customTokenHandler = new CustomTokenHandler(requireActivity());
        accessToken = customTokenHandler.getAccessToken();

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

        recentRecyclerView = v.findViewById(R.id.review_recent_recyclerView);
        recentReviewItems = new ArrayList<>();
        listAdapter = new RecyclerViewAdapterRecent(recentReviewItems, getContext());

        //        swipeRefreshLayout
//        TODO 새로고침 제대로 구현 >>>> 지금 새로고침할때마다 똑같은거 다시 들어감
        SwipeRefreshLayout swipeRefreshLayout = v.findViewById(R.id.review_recent_swipeRefLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            ReviewListRequest(accessToken);
            listAdapter.notifyDataSetChanged();
            final Handler handler = new Handler();
            handler.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 500);
        });

        // TODO: 2023-03-12  나중에 아이템 클릭시 해당 게시글 이동 리스너로 활용
        listAdapter.setOnItemClickListener(new RecyclerViewAdapterRecent.OnItemClickListener() {
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

    @SuppressLint("NotifyDataSetChanged")
    private void ReviewListRequest(String accessToken) {
        final String URL_REVIEWLIST = UrlConst.URL + "/api/review";
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
                        String userNickName = resultObject.getString("userNickName");
                        String score = resultObject.getString("score");
                        int likes = resultObject.getInt("likes");
                        String content = resultObject.getString("contents");
                        String createDate = resultObject.getString("createdDate");

                        receivedData.setUserNickName(userNickName);
                        receivedData.setScore(score);
                        receivedData.setLikes(likes);
                        receivedData.setContents(content);
                        receivedData.setCreatedDate(createDate);

                        JSONArray photosArray = resultObject.getJSONArray("photos");
                        List<Bitmap> receivedBitmaps = new ArrayList<>();
                        for (int j = 0; j < photosArray.length(); j++) {
                            JSONObject photoObject = photosArray.getJSONObject(j);
                            String encodedImage = photoObject.getString("encodedImage");

                            Bitmap imageBitmap = decodeImage(encodedImage);
                            receivedBitmaps.add(imageBitmap);

                        }
                        receivedData.setBitmapImages(receivedBitmaps);

                        String date = receivedData.getCreatedDate();
                        if (receivedBitmaps.size() > 0) {
                            ReviewItem itemB = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
                                    receivedBitmaps, receivedData.getContents(),
                                    receivedData.getScore(), true, receivedData.getLikes(), date);
                            recentReviewItems.add(itemB);
                        }
//                       set review data
//                        setReceivedData(receivedData);

                        /*JSONArray hasTagsArray = resultObject.getJSONArray("hasTags");
                        for (int k = 0; k < hasTagsArray.length(); k++) {
                            JSONObject hasTagObject = hasTagsArray.getJSONObject(k);
                            String tag = hasTagObject.getString("tag");
                            System.out.println("Tag " + (k + 1) + ": " + tag);
                        }*/
                    }
                    JSONObject pagingObject = jsonObject.getJSONObject("paging");
                    String contentSize = pagingObject.getString("contentSize");
                    String page = pagingObject.getString("page");
                    String hasNext = pagingObject.getString("hasNext");
                    Log.d("paging", contentSize + " " + page + " " + hasNext);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

        recentRecyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    public static Bitmap decodeImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Log.d("decoded string", Arrays.toString(decodedString));

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    private void setReceivedData(ReviewItem receivedData) {
        //                       TODO remove sampleImage
        List<String> photos = receivedData.getPhotos();

        String imageSample = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";

        @SuppressLint("SimpleDateFormat")
        String date = receivedData.getCreatedDate();

        if (photos.size() == 1) {
            Log.i("received photo url", photos.toString());
            ReviewItem item = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
                    photos.get(0), receivedData.getContents(),
                    receivedData.getScore(), true, receivedData.getLikes(), date);
            recentReviewItems.add(item);
        } else if (photos.size() > 1) {
            ReviewItem item = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
                    photos.get(0), photos.get(1), receivedData.getContents(),
                    receivedData.getScore(), true, receivedData.getLikes(), date);
            recentReviewItems.add(item);
        }

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
