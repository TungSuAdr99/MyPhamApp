package com.example.review.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.review.R;
import com.example.review.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class SearchActivity extends AppCompatActivity {

    private EditText edtSearch;

    private DatabaseReference cosmeticRef;
    private ArrayList<Product> arrayProducts = new ArrayList<>();

    private String strSearch = "";
    private RecyclerView rvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edt_search);
        rvProduct = findViewById(R.id.rv_product);

        cosmeticRef = FirebaseDatabase.getInstance().getReference().child("MyPham");
        retrieveCosmetics();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strSearch = s.toString();
                onStart();
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();

        Log.e("KMFL", "====");

        FirebaseRecyclerOptions<Product> options;

        if(strSearch.equals("")){
            options = new FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(cosmeticRef, Product.class)
                    .build();
        }else {
            options = new FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(cosmeticRef.orderByChild("name")
                            .startAt(strSearch)
                            .endAt(strSearch + "\uf8ff"), Product.class)
                    .build();
        }

        FirebaseRecyclerAdapter<Product, FindCosmeticsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Product, FindCosmeticsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FindCosmeticsViewHolder holder, final int position, @NonNull final Product model) {
                holder.txtNameProduct.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.img_product);
                Log.e("KMFFG", position + "");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("image", model.getImage());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("ingredient", model.getIngredient());
                        intent.putExtra("detail", model.getDetail());
                        intent.putExtra("promotional", model.getPromotional());
                        intent.putExtra("spinner", model.getSpinner());
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FindCosmeticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
                FindCosmeticsViewHolder viewHolder = new FindCosmeticsViewHolder(view);
                return viewHolder;
            }
        };

        rvProduct.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void startIntent(int position){
        Intent intent = new Intent(SearchActivity.this, ProductDetailsActivity.class);
        intent.putExtra("name", arrayProducts.get(position).getName());
        intent.putExtra("image", arrayProducts.get(position).getImage());
        intent.putExtra("price", arrayProducts.get(position).getPrice());
        intent.putExtra("ingredient", arrayProducts.get(position).getIngredient());
        intent.putExtra("detail", arrayProducts.get(position).getDetail());
        //intent.putExtra("spinner", positionSpinner);
        intent.putExtra("position", position);

        /*//related product
        Random rand = new Random();
        int positionRelatedOne = rand.nextInt(products.size());
        int positionRelatedTwo = rand.nextInt(products.size());
        int positionRelatedThree = rand.nextInt(products.size());

        while (positionRelatedOne == positionRelatedTwo){
            positionRelatedOne = rand.nextInt(products.size());
        }

        while (positionRelatedOne == positionRelatedThree){
            positionRelatedOne = rand.nextInt(products.size());
        }

        while (positionRelatedTwo == positionRelatedThree){
            positionRelatedTwo = rand.nextInt(products.size());
        }

        intent.putExtra("imageRelatedOne", products.get(positionRelatedOne).getImage());
        intent.putExtra("nameRelatedOne", products.get(positionRelatedOne).getName());

        intent.putExtra("imageRelatedTwo", products.get(positionRelatedTwo).getImage());
        intent.putExtra("nameRelatedTwo", products.get(positionRelatedTwo).getName());

        intent.putExtra("imageRelatedThree", products.get(positionRelatedThree).getImage());
        intent.putExtra("nameRelatedThree", products.get(positionRelatedThree).getName());

        intent.putExtra("positionRelatedOne", positionRelatedOne);
        intent.putExtra("positionRelatedTwo", positionRelatedTwo);
        intent.putExtra("positionRelatedThree", positionRelatedThree);*/

        startActivity(intent);
    }

    private class FindCosmeticsViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_product;
        private TextView txtNameProduct;

        public FindCosmeticsViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txtNameProduct = itemView.findViewById(R.id.txt_name_product);

        }
    }
}
