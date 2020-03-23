package com.example.review.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.review.Tab.TabAddressFragment;
import com.example.review.Tab.TabProductFragment;
import com.example.review.Tab.TabSeachsFragment;
import com.example.review.Tab.TabEndowFragment;
import com.example.review.R;
import com.example.review.activity.SearchActivity;
import com.example.review.model.adapter.ProductAdapter;
import com.example.review.model.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private GridView gvCosmetics;
    ArrayList<Product> arrayList;
    ProductAdapter customAdapter;
    private ViewFlipper viewFlipper;
    private ImageView imgSearch;
    ImageView imgAdvertise1, imgAdvertise2;

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
        chuyenTk(view);
    }

    public void chuyenTk(View view)
    {
        imgSearch = view.findViewById(R.id.img_Timkiem);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
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
                Fragment fragment = null;
               if(tab.getPosition() == 1)
                {
                    fragment = new TabProductFragment();
                }
                else if(tab.getPosition() == 2)
                {
                    fragment = new TabAddressFragment();
                }
                else if(tab.getPosition() == 3)
                {
                    fragment = new TabEndowFragment();
                }
                else if(tab.getPosition() == 4)
                {
                    fragment = new TabSeachsFragment();
                }

                frameLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.frament, fragment).commit();
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
        viewFlipper = view.findViewById(R.id.view_flipper);
        imgAdvertise1 = view.findViewById(R.id.img_anhQC1);
        imgAdvertise2 = view.findViewById(R.id.img_anhQC2);

        setAdvertise();

        if(imgAdvertise1.getParent() != null) {
            ((ViewGroup)imgAdvertise1.getParent()).removeView(imgAdvertise1);
        }

        if(imgAdvertise2.getParent() != null) {
            ((ViewGroup)imgAdvertise2.getParent()).removeView(imgAdvertise2);
        }

        viewFlipper.addView(imgAdvertise1);
        viewFlipper.addView(imgAdvertise2);


        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    //set QC
    private void setAdvertise(){
        final ArrayList<String> arrayAdvertises = new ArrayList<>();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference().child("advertise");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    arrayAdvertises.add(snapshot.getValue().toString());
                }

                Glide.with(getContext()).load(arrayAdvertises.get(0)).into(imgAdvertise1);
                Glide.with(getContext()).load(arrayAdvertises.get(1)).into(imgAdvertise2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void listSP(View view)
    {
        gvCosmetics = view.findViewById(R.id.gv_ds);
        arrayList = new ArrayList<>();

        setCosmetics();

    }

    //set MyPham
    private void setCosmetics(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference().child("MyPham");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    product.setKey(snapshot.getKey());
                    arrayList.add(product);
                }

                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter() {
        customAdapter = new ProductAdapter(getContext(), R.layout.row, arrayList);
        gvCosmetics.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }
}
