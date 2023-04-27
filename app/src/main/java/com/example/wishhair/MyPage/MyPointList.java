package com.example.wishhair.MyPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.MainActivity;
import com.example.wishhair.MyPage.adapters.PointAdapter;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPointList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPointList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MainActivity mainActivity;
    private OnBackPressedCallback callback;

    PointAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    private SharedPreferences loginSP;
    final static private String url = UrlConst.URL + "/api/user/my_page";
    final static private String point_url = UrlConst.URL + "/api/point";

    static private String accessToken;
    TextView mypointview;
    Button toPointRefund;
    public MyPointList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPointList.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPointList newInstance(String param1, String param2) {
        MyPointList fragment = new MyPointList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mainActivity.ChangeFragment(2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getView().findViewById(R.id.modify_toolbar);
        loginSP = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String accessToken = loginSP.getString("accessToken", "fail acc");


        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(2);
            }
        });

        toPointRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(9);
            }
        });

        recyclerView = view.findViewById(R.id.pointlist_recyclerview);
        adapter = new PointAdapter();
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext(), 1);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.point_recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        PointListRequest(accessToken);
        PointHistoryRequest(accessToken);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_point_list_fragment, container, false);
        mypointview = view.findViewById(R.id.point_pointview);
        toPointRefund = view.findViewById(R.id.point_refund);
        return view;
    }

    public void PointListRequest(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                String mypoint = Integer.toString(response.optInt("point"));
                Log.i("pointlist response ", mypoint);
                mypointview.setText("현재 포인트\n"+mypoint+"P");

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
    public void PointHistoryRequest(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, point_url , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Gson", "request");
                processResponse(response);
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

    public void processResponse(JSONObject response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<PointHistory>>(){}.getType();
        List<PointHistory> phList = new ArrayList<PointHistory>();
        PointHistory test1 = new PointHistory();
        phList.add(test1);
        if (phList != null && !phList.isEmpty()) {
            adapter.setItems((ArrayList<PointHistory>) phList);
            adapter.notifyDataSetChanged();
            Log.d("Gson","adapter passed");
        }
        else
            Log.d("Gson","else passed");
    }
}