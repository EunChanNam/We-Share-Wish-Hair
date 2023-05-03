package com.example.wishhair.MyPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RefundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RefundFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    final static private String url = UrlConst.URL + "/api/point/use";
    private SharedPreferences loginSP;
    Button refundApply;
    EditText inputBank;
    EditText inputAccount;
    EditText inputPoint;

    public RefundFragment() {
        // Required empty public constructor
    }


    public static RefundFragment newInstance(String param1, String param2) {
        RefundFragment fragment = new RefundFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_point_refund_fragment, container, false);
        inputBank = view.findViewById(R.id.refund_input_bank);
        inputAccount = view.findViewById(R.id.refund_input_account);
        inputPoint = view.findViewById(R.id.refund_input_point);
        refundApply = view.findViewById(R.id.refund_apply);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginSP = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String accessToken = loginSP.getString("accessToken", "fail acc");

        refundApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(view.getContext(), R.style.RefundAlertDialogTheme);
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.mypage_refund_dialog, view.findViewById(R.id.dialog_refund_layout));
                    // alert의 title과 Messege 세팅

                    myAlertBuilder.setView(v);
                    AlertDialog alertDialog = myAlertBuilder.create();

                    // 버튼 리스너 설정
                    v.findViewById(R.id.dialog_refund_OKbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RefundRequest(accessToken);
                            Toast.makeText(view.getContext().getApplicationContext(),"환급 완료 !",Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    });
                    v.findViewById(R.id.dialog_refund_Canclebtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    // 다이얼로그 형태 지우기
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
                    alertDialog.show();
                }
            });
    }

    // 환급 Request
    public void RefundRequest(String accessToken) {
        JSONObject refundObj = new JSONObject();
        try {
            refundObj.put("bankName",inputBank.toString());
            refundObj.put("accountNumber", inputAccount.toString());
            refundObj.put("dealAmount", Integer.parseInt(inputPoint.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url , refundObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
//                Log.i("Request", "success");
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

}