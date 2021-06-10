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
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new youtubeFragment()).commit();
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.menu_youtube:
                        selectedFragment = new youtubeFragment();
                    break;
                    case  R.id.menu_facebook:
                        selectedFragment = new facebookFragment();
                    break;
                    case R.id.menu_instagram:
                        selectedFragment = new  instagramFragment();
                    break;
                    case  R.id.menu_download:
                        selectedFragment = new downloadFragment();
                    break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,selectedFragment).commit();
                return true;
            }
        });
    }
}