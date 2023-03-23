package com.example.wishhair;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wishhair.MyPage.InformationModifyFragment;
import com.example.wishhair.MyPage.MyCouponFragment;
import com.example.wishhair.MyPage.MyInformationFragment;
import com.example.wishhair.MyPage.MyPageFragment;
import com.example.wishhair.MyPage.MyPointList;
import com.example.wishhair.MyPage.MyHeartlistFragment;
import com.example.wishhair.MyPage.MyStyleFragment;

import com.example.wishhair.review.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final HomeFragment homeFragment = new HomeFragment();
    private final RecommendFragment recommendFragment = new RecommendFragment();
    private final ReviewFragment reviewFragment = new ReviewFragment();
    private final MyPageFragment myPageFragment = new MyPageFragment();
    private final MyStyleFragment myStyleFragment = new MyStyleFragment();
    private final MyHeartlistFragment myHeartlistFragment = new MyHeartlistFragment();
    private final InformationModifyFragment informationModifyFragment = new InformationModifyFragment();
    private final MyCouponFragment myCouponFragment = new MyCouponFragment();
    private final MyPointList myPointList = new MyPointList();

    private final MyInformationFragment myInformationFragment = new MyInformationFragment();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainLayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView BottomNavigation = findViewById(R.id.BottomNavigation);
        BottomNavigation.setOnItemSelectedListener(new BottomNavigationItemSelectedListener());
        

    }

    class BottomNavigationItemSelectedListener implements NavigationBarView.OnItemSelectedListener{

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(item.getItemId())
            {
                case R.id.bot_nav_home:
                    transaction.replace(R.id.MainLayout, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_recommend:
                    transaction.replace(R.id.MainLayout, recommendFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_review:
                    transaction.replace(R.id.MainLayout, reviewFragment).commitAllowingStateLoss();
                    break;
                case R.id.bot_nav_myPage:
                    transaction.replace(R.id.MainLayout, myPageFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    public void ChangeFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch(index)
        {
            case 1:
                transaction.replace(R.id.MainLayout, myInformationFragment).commitAllowingStateLoss();
                break;
            case 2:
                transaction.replace(R.id.MainLayout, myPageFragment).commitAllowingStateLoss();
                break;
            case 3:
                transaction.replace(R.id.MainLayout, myStyleFragment).commitAllowingStateLoss();
                break;
            case 4:
                transaction.replace(R.id.MainLayout, myHeartlistFragment).commitAllowingStateLoss();
                break;
            case 5:
                transaction.replace(R.id.MainLayout, informationModifyFragment).commitAllowingStateLoss();
                break;
            case 6:
                transaction.replace(R.id.MainLayout, myCouponFragment).commitAllowingStateLoss();
                break;
            case 7:
                transaction.replace(R.id.MainLayout, myPointList).commitAllowingStateLoss();
                break;
        }

    }

    public void setOnClickShowAlert(ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickShowAlert();
            }
        });
    }

    public void OnClickShowAlert() {
        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(this);
        // alert의 title과 Messege 세팅
//        myAlertBuilder.setTitle("Alert");
        myAlertBuilder.setMessage("test"+"P를 차감하여\n쿠폰을 구입하시겠습니까?");
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
}