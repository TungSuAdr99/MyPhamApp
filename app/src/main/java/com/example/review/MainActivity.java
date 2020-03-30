package com.example.review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.review.Fragment.HomeFragment;
import com.example.review.Fragment.NotificationFragment;
import com.example.review.Fragment.UserFragment;
import com.example.review.Fragment.AddressFragment;
import com.example.review.Fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNav;

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
            Fragment selectedFragment = null;

            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();// màn hình của home đk gán cho 1 màn hình trung gian
                    break;
                case R.id.nav_address:
                    selectedFragment = new AddressFragment();
                    break;

                case R.id.nav_product:
                    selectedFragment = new ProductFragment();
                    break;

                case R.id.nav_thongbao:
                    selectedFragment = new NotificationFragment();
                    break;
                case R.id.nav_toi:
                    selectedFragment = new UserFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frament, selectedFragment).commit();// màn hình frament đk thay thế bằng màn hình tring gian
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
