package com.example.wishhair.MyPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.MainActivity;
import com.example.wishhair.MyPage.adapters.MyPageRecyclerViewAdapter;
import com.example.wishhair.MyPage.items.HeartlistItem;
import com.example.wishhair.R;
import com.example.wishhair.sign.LoginActivity;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MyPageFragment extends Fragment {

    MainActivity mainActivity;
    RecyclerView HeartlistRecyclerView;
    MyPageRecyclerViewAdapter adapter;
    RecyclerDecoration recyclerDecoration;

    private SharedPreferences loginSP;
    final static private String url = UrlConst.URL + "/api/logout";
    final static private String url2 = UrlConst.URL + "/api/user/my_page";
    final static private String url_wishlist = UrlConst.URL + "/api/wish_list/wish_list";

    static String testName = null;
    TextView tv;
    TextView point_preview;
    ImageView userpicture;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.modify_toolbar);

        ImageButton toMyInformationButton = view.findViewById(R.id.mypage_to_config);
        ImageButton toMyPoint = view.findViewById(R.id.mypage_to_point);
//        Button toMyCoupon = view.findViewById(R.id.toMyCoupon);

        HeartlistRecyclerView = view.findViewById(R.id.HeartlistRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        HeartlistRecyclerView.setLayoutManager(layoutManager);
//        recyclerDecoration = new RecyclerDecoration(-200);
//        HeartlistRecyclerView.addItemDecoration(recyclerDecoration);

        adapter = new MyPageRecyclerViewAdapter();

        HeartlistRecyclerView.setAdapter(adapter);

        toMyInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(8);
            }
        });
        toMyPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(7);
            }
        });
//        toMyCoupon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mainActivity.ChangeFragment(6);
//            }
//        });

//        userpicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                activityResultLauncher.launch(intent);
//            }
//        });

/*      HomeFragment로 이동하는 버튼 <불필요 시 삭제>
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.MainLayout, homeFragment);
                fragmentTransaction.commit();
            }
        });
        toolbar.inflateMenu(R.menu.메뉴.xml);   버튼 추가 시 사용할 것 */

//        LOGOUT
        ImageButton btn_logout  = view.findViewById(R.id.mypage_button_logout);
        loginSP = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String accessToken = loginSP.getString("accessToken", "fail acc");

        Log.d("acc", loginSP.getString("accessToken", "fail acc"));
        Log.d("ref", loginSP.getString("refreshToken", "fail ref"));

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(accessToken);
            }
        });
        myPageRequest(accessToken);
        myPageRecyclerviewRequest(accessToken);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage_fragment, container, false);
        tv = view.findViewById(R.id.mypage_nickname);
        point_preview = view.findViewById(R.id.mypage_point_preview);
        userpicture = view.findViewById(R.id.mypage_user_picture);
        return view;
    }

    private void logout(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                logout_delete_token(loginSP);
                Intent intent = new Intent(mainActivity, LoginActivity.class);
                startActivity(intent);
                mainActivity.finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                logout_delete_token(loginSP);
                Intent intent = new Intent(mainActivity, LoginActivity.class);
                startActivity(intent);
                mainActivity.finish();
            }

        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(jsonObjectRequest);
    }

    private void logout_delete_token (SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.apply();
    }

    public void myPageRequest(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2 , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    testName = response.getString("nickname");
                    String mypoint = Integer.toString(response.optInt("point"));
                    Log.i("받아온 거", testName);
                    tv.setText(testName+" 님");
                    point_preview.setText(mypoint+ "P");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }

    //wishlist recyclerview request
    public void myPageRecyclerviewRequest(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_wishlist , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONArray jsonArray = obj.getJSONArray("result");
                    for (int i=0;i<3;i++) {
                        HeartlistItem item = new HeartlistItem();
                        JSONObject object = jsonArray.getJSONObject(i);
                        item.setHeartlistStyleName(object.getString("hairStyleName"));
                        Log.i("photo response test", object.getString("hairStyleName"));
                        adapter.addItem(item);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }



    public ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        userpicture.setImageURI(uri);
                    }
                }
            }
    );

}
