package com.example.wishhair;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentHome fragmentHome = new FragmentHome();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment3 fragment3 = new Fragment3();
    private Fragment4 fragment4 = new Fragment4();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainLayout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView BottomNavigation = findViewById(R.id.BottomNevigation);
        BottomNavigation.setOnItemSelectedListener(new BottomNavigationItemSelectedListener());

    }

    class BottomNavigationItemSelectedListener implements NavigationBarView.OnItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(item.getItemId())
            {
                case R.id.home:
                    transaction.replace(R.id.MainLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.menu2:
                    transaction.replace(R.id.MainLayout, fragment2).commitAllowingStateLoss();
                    break;
                case R.id.menu3:
                    transaction.replace(R.id.MainLayout, fragment3).commitAllowingStateLoss();
                    break;
                case R.id.menu4:
                    transaction.replace(R.id.MainLayout,fragment4).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}