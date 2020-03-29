package com.example.review.Tab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.review.adapter.ProductFullAdapter;
import com.example.review.activity.ProductDetailsActivity;
import com.example.review.R;
import com.example.review.model.MySpinner;
import com.example.review.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabProductFragment extends Fragment implements ProductFullAdapter.onClick{
    private GridView Product_gv_DSSanphamchon;
    private ArrayList<Product> arrayList;
    private ProductFullAdapter customAdapter;
    private Spinner Product_spn_DSSanpham;
    private List<MySpinner> arrSpinner = new ArrayList<>();//Tạo mảng
    private List<String> spinnerArr = new ArrayList<>();//Tạo mảng
    private int positionSpinner;
    private ArrayList<Product> products;



    public TabProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_s_p, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Product_gv_DSSanphamchon = view.findViewById(R.id.Product_gv_DSSanphamchon);


        GirdViewSP(view);
        SpinnerSP(view);

        setSpinner();
    }

    private void setSpinner(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference().child("Spinner");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrSpinner.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MySpinner spinner = snapshot.getValue(MySpinner.class);
                    spinner.setKey(snapshot.getKey());
                    arrSpinner.add(spinner);
                    spinnerArr.add(spinner.getName());
                }

                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, spinnerArr);//Trung gian để đưa data lên view
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);//tạo ra nút để tích
                Product_spn_DSSanpham.setAdapter(adapter);//lấy data lên cho sp1

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SpinnerSP(View view)
    {
        Product_spn_DSSanpham = view.findViewById(R.id.Product_spn_DSSanpham);

        Product_spn_DSSanpham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0; i<arrSpinner.size(); i++){
                    int p = Integer.parseInt(arrSpinner.get(i).getKey());

                    if(p == position){
                        setCosmetics();
                        positionSpinner = position;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void GirdViewSP(View view)
    {
        Product_gv_DSSanphamchon = view.findViewById(R.id.Product_gv_DSSanphamchon);
        setCosmetics();
    }

    private void setCosmetics(){
        arrayList = new ArrayList<>();
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

                products = new ArrayList<>();
                for(int i=0; i<arrayList.size(); i++){
                    int p = Integer.parseInt(arrayList.get(i).getSpinner());

                    if(p == positionSpinner){
                        products.add(arrayList.get(i));
                    }
                }

                customAdapter = new ProductFullAdapter(getContext(), R.layout.activity_product_girdview, products);
                Product_gv_DSSanphamchon.setAdapter(customAdapter);
                customAdapter.setClick(TabProductFragment.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void click(int position) {
        Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
        intent.putExtra("name", products.get(position).getName());
        intent.putExtra("image", products.get(position).getImage());
        intent.putExtra("price", products.get(position).getPrice());
        intent.putExtra("ingredient", products.get(position).getIngredient());
        intent.putExtra("detail", products.get(position).getDetail());
        intent.putExtra("spinner", positionSpinner);
        intent.putExtra("position", position);

        startActivity(intent);
    }
}
