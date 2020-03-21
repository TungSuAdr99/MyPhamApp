package com.example.review.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.review.Tab.TabDc;
import com.example.review.Tab.TabHome;
import com.example.review.Tab.TabSp;
import com.example.review.Tab.TabTkn;
import com.example.review.Tab.TabUd;
import com.example.review.activity.Manhinhtimkiem;
import com.example.review.R;
import com.example.review.adapter.SanPhamAdapter;
import com.example.review.model.SanPham;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private GridView gv_ds;
    ArrayList<SanPham> arrayList;
    SanPhamAdapter customAdapter;
    private ViewFlipper view_flipper;
//    private TextView tv_chuyentimkiem;
    ImageView img1, img2;

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // dùng như main ở activivy
        super.onViewCreated(view, savedInstanceState);

        listSP(view);
        chuyenQC(view);
        Tablayout(view);
    }

    public  void Tablayout(View view)
    {
        tabLayout = view.findViewById(R.id.tablayout);
        frameLayout = view.findViewById(R.id.frame_tab);
        linearLayout = view.findViewById(R.id.linear_layout);

        frameLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

        ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);//ẩn cái đầu tiên

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0)
                {
                    Fragment homeFrament = new TabHome();
                    getFragmentManager().beginTransaction().replace(R.id.frame_tab, homeFrament).commit();

                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 1)
                {
                    Log.e("KMF","kkk");
                    Fragment homeFrament = new TabSp();
                    getFragmentManager().beginTransaction().replace(R.id.frament, homeFrament).commit();

                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 2)
                {
                    Log.e("KMF","kkkaaaa");
                    Fragment homeFrament = new TabDc();
                    getFragmentManager().beginTransaction().replace(R.id.frament, homeFrament).commit();

                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 3)
                {
                    Log.e("KMF","kkkaaaa");
                    Fragment homeFrament = new TabUd();
                    getFragmentManager().beginTransaction().replace(R.id.frament, homeFrament).commit();

                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 4)
                {
                    Log.e("KMF","kkkaaaa");
                    Fragment homeFrament = new TabTkn();
                    getFragmentManager().beginTransaction().replace(R.id.frament, homeFrament).commit();

                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void chuyenQC(View view)
    {
        view_flipper = view.findViewById(R.id.view_flipper);
        img1 = view.findViewById(R.id.img_anhQC1);
        img2 = view.findViewById(R.id.img_anhQC2);

        if(img1.getParent() != null) {
            ((ViewGroup)img1.getParent()).removeView(img1);
        }

        if(img2.getParent() != null) {
            ((ViewGroup)img2.getParent()).removeView(img2);
        }

        view_flipper.addView(img1);
        view_flipper.addView(img2);

        view_flipper.setFlipInterval(2000);
        view_flipper.startFlipping();
    }

    public  void listSP(View view)
    {
        gv_ds = view.findViewById(R.id.gv_ds);
        arrayList = new ArrayList<>();

        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));
        arrayList.add(new SanPham(R.drawable.son));


        customAdapter = new SanPhamAdapter(getContext(), R.layout.row, arrayList);
        gv_ds.setAdapter(customAdapter);
    }
}
