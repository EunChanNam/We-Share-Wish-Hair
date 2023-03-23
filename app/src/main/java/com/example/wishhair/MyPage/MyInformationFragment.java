package com.example.wishhair.MyPage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;

import com.example.wishhair.MainActivity;
import com.example.wishhair.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MainActivity mainActivity;

    public MyInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyInformationFragment newInstance(String param1, String param2) {
        MyInformationFragment fragment = new MyInformationFragment();
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

        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        LinearLayout MyPointLayout = getView().findViewById(R.id.MyPointLayout);
        MyPointLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams ButtonOn = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics()));
        LinearLayout.LayoutParams ButtonOff = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
        LinearLayout.LayoutParams ButtonSize = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(2);
            }
        });

        Button toInformationModify = view.findViewById(R.id.toInformationModify);
        toInformationModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.ChangeFragment(5);
            }
        });

        ToggleButton toMyPoint = view.findViewById(R.id.toMyPoint);
        toMyPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    MyPointLayout.setLayoutParams(ButtonOn);
                    Button toMyPointCoupon = new Button(getContext());
                    Button toMyPointList = new Button(getContext());

                    toMyPointCoupon.setText("test");
                    toMyPointCoupon.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    toMyPointCoupon.setHeight(20);
                    toMyPointList.setText("test2");
                    toMyPointList.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    toMyPointList.setHeight(20);

                    MyPointLayout.addView(toMyPointCoupon);
                    MyPointLayout.addView(toMyPointList);
                } else {
                    MyPointLayout.setLayoutParams(ButtonOff);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_information_fragment, container, false);
    }
}