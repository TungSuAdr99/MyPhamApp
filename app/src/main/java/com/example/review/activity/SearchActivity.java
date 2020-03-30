package com.example.review.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.review.R;
import com.example.review.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference cosmeticRef;
    private ArrayList<Product> arrayProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        cosmeticRef = FirebaseDatabase.getInstance().getReference().child("MyPham");
        retrieveCosmetics();
    }

    private void retrieveCosmetics(){
        cosmeticRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayProducts.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    arrayProducts.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
