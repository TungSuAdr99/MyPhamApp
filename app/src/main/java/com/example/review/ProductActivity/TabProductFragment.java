package com.example.review.ProductActivity;

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

import com.example.review.ProductActivity.GirdView_ProductActivity.ProductContact;
import com.example.review.ProductActivity.GirdView_ProductActivity.ProductCustomAdapter;
import com.example.review.ProductDetailsActivity.ProductDetailsActivity;
import com.example.review.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabProductFragment extends Fragment {
    private GridView Product_gv_DSSanphamchon;
    private ArrayList<ProductContact> arrayList;
    private ProductCustomAdapter customAdapter;
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
        ChuyenManHinhSPchitiet(view);
    }

    private  void ChuyenManHinhSPchitiet(View view)
    {

//        for (int i=0; i<Product_gv_DSSanphamchon.getChildCount(); i++)
//        {
//            CardView cardView = (CardView) Product_gv_DSSanphamchon.getChildAt(i);
//            final int finalI = i;
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
////                    startActivity(intent);
//                    Toast.makeText(getContext(), "aa", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

        Product_gv_DSSanphamchon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "aa"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SpinnerSP(View view)
    {
        Product_spn_DSSanpham = view.findViewById(R.id.Product_spn_DSSanpham);
        List<String> list = new ArrayList<>();//Tạo mảng
        list.add("Son");
        list.add("Kem chống nắng");
        list.add("Kem dưỡng da");
        list.add("Mặt nạ");

        ArrayAdapter<String> adapte = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list);//Trung gian để đưa data lên view
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);//tạo ra nút để tích
        Product_spn_DSSanpham.setAdapter(adapte);//lấy data lên cho sp1
    }

    private void GirdViewSP(View view)
    {
        Product_gv_DSSanphamchon = view.findViewById(R.id.Product_gv_DSSanphamchon);
        arrayList = new ArrayList<>();//tạo 1 mảng
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son A"));
        arrayList.add(new ProductContact(R.drawable.anhqc, "Son B"));
        arrayList.add(new ProductContact(R.drawable.son, "Son C"));
        arrayList.add(new ProductContact(R.drawable.son, "Son D"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son E"));
        arrayList.add(new ProductContact(R.drawable.anhqc, "Son F"));
        arrayList.add(new ProductContact(R.drawable.anhqc, "Son G"));
        arrayList.add(new ProductContact(R.drawable.son, "Son H"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son K"));
        arrayList.add(new ProductContact(R.drawable.anhsp, "Son L"));
        arrayList.add(new ProductContact(R.drawable.son, "Son M"));
        arrayList.add(new ProductContact(R.drawable.anhqc, "Son N"));

        customAdapter = new ProductCustomAdapter(getContext(), R.layout.activity_product_girdview, arrayList);
        Product_gv_DSSanphamchon.setAdapter(customAdapter);
    }
}
