package com.example.wishhair.review.recent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
    private static final String IMG_PATH = "C:\\Users\\hath8\\IdeaProjects\\backend\\We-Share-Wish-Hair\\src\\main\\resources\\static\\images\\";
    private Bitmap bitmap;

    //    sort
    private static String sort_selected = null;
    private static final String[] sortItems = {"최신 순", "오래된 순", "좋아요 순"};

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
        /*//===============================dummy data===============================
        String imageSample = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";
        ReviewItem newItem = new ReviewItem(R.drawable.user_sample, "현정" + " 님", "3" + " 개", "3.03",
                imageSample, imageSample,
                " is a root vegetable, typically orange in color, though purple, black, red, white, and yellow cultivars exist,[2][3][4] all of which are domesticated forms of the wild carrot, Daucus carota, native to Europe and Southwestern Asia. The plant probably originated in Persia and was originally cultivated for its leaves and seeds. The most commonly eaten part of the plant is the taproot, although the stems and leaves are also eaten. The domestic carrot has been selectively bred for its enlarged, more palatable, less woody-textured taproot.",
                "3.8", false, 314, "22.05.13");
        recentReviewItems.add(newItem);*/

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
//                        RecentReceivedData receivedData = getDate(resultArray);
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
                        List<String> fileNames = new ArrayList<>();
                        for (int j = 0; j < photosArray.length(); j++) {
                            JSONObject photoObject = photosArray.getJSONObject(j);
                            String storeFilename = photoObject.getString("storeFilename");

//                            String bitmapUrl = setImage(storeFilename);
//                            Log.d("bitmap URL", bitmapUrl);
                            fileNames.add(storeFilename);
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

        recentRecyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }


    private void setReceivedData(ReviewItem receivedData) {
        //                       TODO remove sampleImage
        List<String> photos = receivedData.getPhotos();
        //어쨋든 이미지를 서버로 보낼 때 Uri로 보내서 서버에 잘 저장되고
        //받아올 때 URL + fileName 으로 받아와서  그럼 그 URL을 비트맵 형식으로 바꿔야하나? 아닌데 그냥 URL 받으면 된다했었는데
//      https://stickode.tistory.com/382 >> url to bitmap, imageView에 적용
//      사진은 list<string>으로 받아왔으니까
//      사진 ReviewItems 랑 RecentReviewAdapter에서 set할때 잘 건드려바
//      지금 어쨋든 adapter에서 content1, 2를 모두 bind 하기때문에
//      bind할때 photos.get(1)같이 접근하면 2번째 사진이 없을 떄 널포인터가 터지겠지?
//        그러면 이제 bind 할 때 사진 갯수를 파악해서 처리해야하나
//        아니면 아래에 새로 만들 때 저렇게 처리해도 되나
//        >> 이건 나중에 테스트 하보고
//        >>>> 그냥 log에 warring으로만 뜨고 종료되진 않는구나
//      지금 bind할때 log찍게 해놨으니까 받아지는 url 어떤식으로 받아지나 보고
//      이거 서버 안되는것도 잘 모르겠네
//      집노트북에서는 review 데이터가 안받아와

//        그리고 reviewItem에서
//        setContentImage1_1 / 2_1 둘다 만들어놨는데
//        어쨋든 지금 서버에서 받아온 사진들은 list<String>에 넣어지니까
//        저건 필요없고 bind할때 알아서 잘 받아오면 되나


        String imageSample = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String date = receivedData.getCreatedDate();

        if (photos.size() == 0) {
//              테스트 할때 이미지 없는거 거슬리니까 일단 넣지 마!
//            ReviewItem item = new ReviewItem(R.drawable.user_sample, receivedData.getUserNickName(), "5", "3.3",
//                    imageSample, receivedData.getContents(),
//                    receivedData.getScore(), true, receivedData.getLikes(), date);
//            recentReviewItems.add(item);
        }
        else if (photos.size() == 1) {
            Log.i("received photo url", photos.toString());
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
