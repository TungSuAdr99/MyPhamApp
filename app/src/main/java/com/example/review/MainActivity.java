package com.example.review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.review.Fragment.HomeFragment;
import com.example.review.Fragment.ThongbaoFragment;
import com.example.review.Fragment.UserFragment;
import com.example.review.Fragment.GhichuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home();

        navigationhome();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFrament = null;

            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectedFrament = new HomeFragment();// màn hình của home đk gán cho 1 màn hình trung gian
                            break;
                case R.id.nav_ghichu:
                    selectedFrament = new GhichuFragment();
                            break;
                case R.id.nav_thongbao:
                    selectedFrament = new ThongbaoFragment();
                    break;
                case R.id.nav_toi:
                    selectedFrament = new UserFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frament, selectedFrament).commit();// màn hình frament đk thay thế bằng màn hình tring gian
            return true;
        }
    };

    public void navigationhome()
    {
        bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void home(){
        Fragment homeFrament = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frament, homeFrament).commit();
    }
}
