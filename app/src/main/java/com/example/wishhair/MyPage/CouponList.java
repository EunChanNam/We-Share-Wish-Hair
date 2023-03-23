package com.example.wishhair.MyPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.MyPage.adapters.CouponAdapter;
import com.example.wishhair.MyPage.items.CouponItem;
import com.example.wishhair.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView couponRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CouponAdapter couponAdapter;

    public CouponList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CouponList.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponList newInstance(String param1, String param2) {
        CouponList fragment = new CouponList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        couponRecyclerView = view.findViewById(R.id.couponlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        couponRecyclerView.setLayoutManager(layoutManager);
        couponAdapter = new CouponAdapter();

        couponAdapter.addItem(new CouponItem());
        couponAdapter.addItem(new CouponItem());
        couponAdapter.addItem(new CouponItem());
        couponAdapter.addItem(new CouponItem());
        couponAdapter.addItem(new CouponItem());
        couponAdapter.addItem(new CouponItem());
        couponRecyclerView.setAdapter(couponAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.coupon_list_fragment, container, false);
    }
}