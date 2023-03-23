package com.example.wishhair.MyPage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wishhair.MainActivity;
import com.example.wishhair.MyPage.adapters.CouponReceiveAdapter;
import com.example.wishhair.MyPage.items.CouponReceiveItem;
import com.example.wishhair.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponReceive#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponReceive extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    CouponReceiveAdapter adapter;
    LinearLayoutManager layoutManager;
    MainActivity mainActivity;
    Context context;
    ArrayList<CouponReceiveItem> arrayList;

    public CouponReceive() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CouponRecieve.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponReceive newInstance(String param1, String param2) {
        CouponReceive fragment = new CouponReceive();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.coupon_recieve_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.coupon_receive_recyclerview);
        adapter = new CouponReceiveAdapter();
        adapter.addItem(new CouponReceiveItem());
        adapter.addItem(new CouponReceiveItem());
        adapter.addItem(new CouponReceiveItem());
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);



    }



}