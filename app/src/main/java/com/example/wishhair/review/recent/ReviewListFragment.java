package com.example.wishhair.review.recent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

//    img
    private static final String IMG_PATH = "C:\\Users\\hath8\\IdeaProjects\\backend\\We-Share-Wish-Hair\\src\\main\\resources\\static\\images\\";
    private Bitmap bitmap;

    //    sort
    private static String sort_selected = null;
    private static final String[] sortItems = {"최신 순", "오래된 순", "좋아요 순"};

    @SuppressLint("NotifyDataSetChanged")
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
        String imageSample = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";
        ReviewItem newItem = new ReviewItem(R.drawable.user_sample, "현정" + " 님", "3" + " 개", "3.03",
                imageSample, imageSample,
                " is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.",
                "3.8", false, 314, "22.05.13");
        recentReviewItems.add(newItem);


        RecyclerViewAdapterRecent recentRecyclerViewAdapter = new RecyclerViewAdapterRecent(recentReviewItems, getContext());
        recentRecyclerView.setAdapter(recentRecyclerViewAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        //        swipeRefreshLayout
//        TODO 새로고침 제대로 구현
        SwipeRefreshLayout swipeRefreshLayout = v.findViewById(R.id.review_recent_swipeRefLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            ReviewListRequest(accessToken);
            recentRecyclerViewAdapter.notifyDataSetChanged();
            final Handler handler = new Handler();
            handler.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 500);
        });

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
//                parse received data
                String stringResponse = String.valueOf(response);
                try {
                    JSONObject jsonObject = new JSONObject(stringResponse);
                    JSONArray resultArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.length(); i++) {
//                        RecentReceivedData receivedData = getDate(resultArray);
                        RecentReceivedData receivedData = new RecentReceivedData();
                        JSONObject resultObject = resultArray.getJSONObject(i);
                        Log.d("resultObject", resultObject.toString());
                        String userNickName = resultObject.getString("userNickName");
                        String score = resultObject.getString("score");
                        int likes = resultObject.getInt("likes");
                        String content = resultObject.getString("contents");
                        String createDate = resultObject.getString("createdDate");

                        receivedData.setUserNickName(userNickName);
                        receivedData.setScore(score);
                        receivedData.setLikes(likes);
                        receivedData.setContents(content);
                        receivedData.setCreateDate(createDate);

                        JSONArray photosArray = resultObject.getJSONArray("photos");
                        List<String> fileNames = new ArrayList<>();
                        for (int j = 0; j < photosArray.length(); j++) {
                            JSONObject photoObject = photosArray.getJSONObject(j);
                            String storeFilename = photoObject.getString("storeFilename");
                            fileNames.add(IMG_PATH + storeFilename);
                        }
                        receivedData.setPhotos(fileNames);
//                       set review data
                        setReceivedData(receivedData);

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
    }

//    private RecentReceivedData getDate(JSONArray resultArray, int i) throws JSONException {
//        RecentReceivedData receivedData = new RecentReceivedData();
//
//        JSONObject resultObject = resultArray.getJSONObject(i);
//        Log.d("resultObject", resultObject.toString());
//        String userNickName = resultObject.getString("userNickName");
//        String score = resultObject.getString("score");
//        int likes = resultObject.getInt("likes");
//        receivedData.setUserNickName(userNickName);
//        receivedData.setScore(score);
//        receivedData.setLikes(likes);
//
//        JSONArray photosArray = resultObject.getJSONArray("photos");
//        List<String> fileNames = new ArrayList<>();
//        for (int j = 0; j < photosArray.length(); j++) {
//            JSONObject photoObject = photosArray.getJSONObject(j);
//            String storeFilename = photoObject.getString("storeFilename");
//            fileNames.add(IMG_PATH + storeFilename);
//        }
//        receivedData.setPhotos(fileNames);
//
//        return receivedData;
//    }

    private void setReceivedData(RecentReceivedData receivedData) {
        //                       TODO remove sampleImage
        List<String> photos = receivedData.getPhotos();
        Log.d("received photos", photos.toString() + " size: " + photos.size());
        String imageSample = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String date = receivedData.getCreateDate();
        Log.d("Date", date);

        if (photos.size() == 0) {
            ReviewItem item = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
                    imageSample, receivedData.getContents(),
                    receivedData.getScore(), true, receivedData.getLikes(), date);
            recentReviewItems.add(item);
        }
        else if (photos.size() == 1) {
            ReviewItem item = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
                    photos.get(0), receivedData.getContents(),
                    receivedData.getScore(), true, receivedData.getLikes(), date);
            recentReviewItems.add(item);
        } else {
            ReviewItem item = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
                    photos.get(0), photos.get(1), receivedData.getContents(),
                    receivedData.getScore(), true, receivedData.getLikes(), date);
            recentReviewItems.add(item);
        }

    }

//    private void setImage(URL url) {
//        Thread uThread = new Thread() {
//            @Override
//            public void run(){
//                try{
//                    // 이미지 URL 경로
//                    // web에서 이미지를 가져와 ImageView에 저장할 Bitmap을 만든다.
//                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                    conn.setDoInput(true); // 서버로부터 응답 수신
//                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
//
//                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기
//                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
//
//                }catch (MalformedURLException e){
//                    e.printStackTrace();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        uThread.start(); // 작업 Thread 실행
//
//        try{
//            //메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야 한다.
//            //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리도록 한다.
//            //join() 메서드는 InterruptedException을 발생시킨다.
//            uThread.join();
//
//            //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
//            //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정
//            imageView.setImageBitmap(bitmap);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }

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