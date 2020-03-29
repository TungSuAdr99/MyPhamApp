package com.example.review.Tab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.review.GirdViewQualityActivity_Demo.ProductContact;
import com.example.review.GirdViewQualityActivity_Demo.ProductCustomAdapter;
import com.example.review.R;
import com.example.review.activity.ProductDetailsActivity;
import com.example.review.adapter.ProductFullAdapter;
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
public class TabQualityFragment extends Fragment {
    private Spinner spn_star;
    private GridView gv_star;
    private ArrayList<ProductContact> arrayList;
    private ProductCustomAdapter customAdapter;

    public TabQualityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_quality, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerStar(view);
        GirdViewSP(view);
        ChuyenManHinhSPchitiet(view);
    }

    private void spinnerStar(View view)
    {
        spn_star = view.findViewById(R.id.spn_star);

        List<String> list = new ArrayList<>();//Tạo mảng
        list.add("1 Star");
        list.add("2 Star");
        list.add("3 Star");
        list.add("4 Star");
        list.add("5 Star");

        ArrayAdapter<String> adapte = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list);//Trung gian để đưa data lên view
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);//tạo ra nút để tích
        spn_star.setAdapter(adapte);//lấy data lên cho sp1

        spn_star.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), spn_star.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void GirdViewSP(View view)
    {
        gv_star = view.findViewById(R.id.gv_star);
        arrayList = new ArrayList<>();//tạo 1 mảng
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son A"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son B"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son C"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son D"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son E"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son F"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son G"));

        customAdapter = new ProductCustomAdapter(getContext(), R.layout.activity_product_girdview, arrayList);
        gv_star.setAdapter(customAdapter);
    }

    private  void ChuyenManHinhSPchitiet(View view)
    {
        gv_star.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
