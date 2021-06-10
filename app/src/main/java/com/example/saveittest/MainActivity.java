package com.example.saveittest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new homeFragment()).commit();
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;

                switch (item.getItemId()){
                    case R.id.menu_home : temp = new homeFragment();
                    break;
                    case R.id.menu_youtube: temp = new youtubeFragment();
                    break;
                    case  R.id.menu_facebook: temp = new facebookFragment();
                    break;
                    case R.id.menu_instagram: temp = new  instagramFragment();
                    break;
                    case  R.id.menu_download: temp = new downloadFragment();
                    break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,temp).commit();
                return false;
            }
        });
    }
}