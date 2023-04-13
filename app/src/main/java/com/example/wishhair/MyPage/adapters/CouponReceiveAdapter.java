package com.example.wishhair.MyPage.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.MyPage.CouponReceive;
import com.example.wishhair.MyPage.items.CouponItem;
import com.example.wishhair.MyPage.items.CouponReceiveItem;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponReceiveAdapter extends RecyclerView.Adapter<CouponReceiveAdapter.ViewHolder>{
    private ArrayList<CouponReceiveItem> couponreceiveItems = new ArrayList<CouponReceiveItem>();
    private SharedPreferences loginSP;
    final static private String url = UrlConst.URL + "/api/point";
    private Context context;

    public CouponReceiveAdapter(ArrayList<CouponReceiveItem> arrayList) {
    }
    public CouponReceiveAdapter(){}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton CouponItemReceiveButton;
        public TextView CouponItemReceiveTitle, CouponItemReceiveExplanation, CouponItemReceiveConstraint;

        ViewHolder(View view) {
            super(view);


            CouponItemReceiveButton = view.findViewById(R.id.coupon_receive_button);
            CouponItemReceiveTitle = view.findViewById(R.id.coupon_receive_title);
            CouponItemReceiveExplanation = view.findViewById(R.id.coupon_receive_explanation);
            CouponItemReceiveConstraint = view.findViewById(R.id.coupon_receive_constraint);

//            CouponItemReceiveButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder myAlertBuilder =
//                            new AlertDialog.Builder(view.getContext());
//                    // alert의 title과 Messege 세팅
////        myAlertBuilder.setTitle("Alert");
//                    myAlertBuilder.setMessage("test"+"P를 차감하여\n쿠폰을 구입하시겠습니까?");
//                    // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
//                    myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
//                        public void onClick(DialogInterface dialog,int which){
//                            // OK 버튼을 눌렸을 경우
//                            Toast.makeText(view.getContext().getApplicationContext(), "구입 완료",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Cancle 버튼을 눌렸을 경우
//                            Toast.makeText(view.getContext().getApplicationContext(), "",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
//                    myAlertBuilder.show();
//                }
//            });
        }
    }

    @NonNull
    @Override
    public CouponReceiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.coupon_receive_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        return vh;
//        return new CouponReceiveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CouponReceiveItem item = couponreceiveItems.get(position);
        context = holder.itemView.getContext();
    }

    @Override
    public int getItemCount() {
        return couponreceiveItems.size();
    }

    public void addItem(CouponReceiveItem e) {
        couponreceiveItems.add(e);
    }

    public void CouponReceiveRequest(String accessToken) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("send", "send -1000p");

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("send", "failed");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);
                params.put("pointType", "USE");
                params.put("dealAmount" , "1000");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }
}
