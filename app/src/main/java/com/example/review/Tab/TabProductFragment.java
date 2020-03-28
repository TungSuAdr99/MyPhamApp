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

import com.example.review.adapter.ProductFullAdapter;
import com.example.review.activity.ProductDetailsActivity;
import com.example.review.R;
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
    }

    private void SpinnerSP(View view)
    {
        Product_spn_DSSanpham = view.findViewById(R.id.Product_spn_DSSanpham);
        List<String> list = new ArrayList<>();//Tạo mảng
        list.add("Son");
        list.add("Kem chống nắng");
        list.add("Kem dưỡng da");
        list.add("Mặt nạ");

        ArrayAdapter<String> adapte = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, list);//Trung gian để đưa data lên view
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);//tạo ra nút để tích
        Product_spn_DSSanpham.setAdapter(adapte);//lấy data lên cho sp1

        Product_spn_DSSanpham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), Product_spn_DSSanpham.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
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

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter(){
        customAdapter = new ProductFullAdapter(getContext(), R.layout.activity_product_girdview, arrayList);
        Product_gv_DSSanphamchon.setAdapter(customAdapter);
        customAdapter.setClick(this);
    }

    @Override
    public void click(int position) {
        Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
        intent.putExtra("name", arrayList.get(position).getName());
        intent.putExtra("image", arrayList.get(position).getImage());
        intent.putExtra("price", arrayList.get(position).getPrice());
        intent.putExtra("ingredient1", arrayList.get(position).getIngredient1());
        intent.putExtra("ingredient2", arrayList.get(position).getIngredient2());
        intent.putExtra("detail", arrayList.get(position).getDetail());

        Log.e("KMF", arrayList.get(position).getDetail());
        startActivity(intent);
    }
}
