package com.example.review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.review.Fragment.HomeFragment;
import com.example.review.Fragment.ThongbaoFragment;
import com.example.review.Fragment.ToiFragment;
import com.example.review.Fragment.YeuthihFragment;
import com.example.review.adapter.CustomAdapter;
import com.example.review.model.Contact;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

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
                case R.id.nav_yeuthich:
                    selectedFrament = new YeuthihFragment();
                            break;
                case R.id.nav_luutru:
                    selectedFrament = new ThongbaoFragment();
                    break;
                case R.id.nav_toi:
                    selectedFrament = new ToiFragment();
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
