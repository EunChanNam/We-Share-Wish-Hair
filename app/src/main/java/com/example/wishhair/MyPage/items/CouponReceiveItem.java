package com.example.wishhair.MyPage.items;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wishhair.R;

public class CouponReceiveItem extends AppCompatActivity {
    ImageButton CouponReceiveButton;
    TextView CouponReceiveTitle, CouponReceiveExplanation, CouponReceiveConstraint;;

    public CouponReceiveItem() {
    };


    public void ButtonListenerSet(int i) {
        if(CouponReceiveButton != null) {
            CouponReceiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickShowAlert(i);
                }
            });
        }
    }
    public void OnClickShowAlert(int i) {
        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(this);
        // alert의 title과 Messege 세팅
//        myAlertBuilder.setTitle("Alert");
        myAlertBuilder.setMessage(i+"P를 차감하여\n쿠폰을 구입하시겠습니까?");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                // OK 버튼을 눌렸을 경우
                Toast.makeText(getApplicationContext(),"구입 완료",
                        Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancle 버튼을 눌렸을 경우
                Toast.makeText(getApplicationContext(),"",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
        myAlertBuilder.show();
    }

    public ImageButton getCouponReceiveButton() {
        return CouponReceiveButton;
    }

    public void setCouponReceiveButton(ImageButton couponReceiveButton) {
        CouponReceiveButton = couponReceiveButton;
    }

    public TextView getCouponReceiveTitle() {
        return CouponReceiveTitle;
    }

    public void setCouponReceiveTitle(TextView couponReceiveTitle) {
        CouponReceiveTitle = couponReceiveTitle;
    }

    public TextView getCouponReceiveExplanation() {
        return CouponReceiveExplanation;
    }

    public void setCouponReceiveExplanation(TextView couponReceiveExplanation) {
        CouponReceiveExplanation = couponReceiveExplanation;
    }

    public TextView getCouponReceiveConstraint() {
        return CouponReceiveConstraint;
    }

    public void setCouponReceiveConstraint(TextView couponReceiveConstraint) {
        CouponReceiveConstraint = couponReceiveConstraint;
    }
}
