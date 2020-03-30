package com.example.review.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.review.MainActivity;

import com.example.review.R;
import com.example.review.activity.SearchActivity;
import com.example.review.adapter.ShopAdapter;
import com.example.review.model.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private GridView gvCosmetics;
    ArrayList<Shop> arrayShops;
    ShopAdapter customAdapter;
    private ViewFlipper viewFlipper;
    private ImageView imgSearch;
    ImageView imgAdvertise1, imgAdvertise2;
    private ProgressBar progressBar;
    private RelativeLayout rlVisibility;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // dùng như main ở activivy
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        rlVisibility = view.findViewById(R.id.rl_visibility);

        progressBar.setVisibility(View.VISIBLE);
        rlVisibility.setVisibility(View.GONE);
        MainActivity.bottomNav.setVisibility(View.GONE);

        listSP(view);
        chuyenQC(view);
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

                arrayAdvertises.clear();
                progressBar.setVisibility(View.GONE);
                rlVisibility.setVisibility(View.VISIBLE);

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
        arrayShops = new ArrayList<>();

        retrieveShops();

    }

    private void retrieveShops(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference().child("Shop");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayShops.clear();
                progressBar.setVisibility(View.GONE);
                rlVisibility.setVisibility(View.VISIBLE);
                MainActivity.bottomNav.setVisibility(View.VISIBLE);

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Shop shop = snapshot.getValue(Shop.class);
                    shop.setKey(snapshot.getKey());
                    arrayShops.add(shop);
                }

                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter() {
        customAdapter = new ShopAdapter(getContext(), R.layout.item_shop, arrayShops);
        customAdapter.notifyDataSetChanged();
        gvCosmetics.setAdapter(customAdapter);    }
}
