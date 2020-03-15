package com.example.review.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.review.MainActivity;
import com.example.review.Manhinhtimkiem;
import com.example.review.R;
import com.example.review.adapter.CustomAdapter;
import com.example.review.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Button bt_sanpham;
    private GridView gv_ds;
    ArrayList<Contact> arrayList;
    CustomAdapter customAdapter;
    private ViewFlipper view_flipper;
    private TextView tv_chuyentimkiem;
    ImageView img1, img2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // dùng như main ở activivy
        super.onViewCreated(view, savedInstanceState);

        listSP(view);
        bt_sp(view);
        chuyenQC(view);
//        chuyenTimkiem(view);
    }

//    public void chuyenTimkiem(View view)
//    {
//        tv_chuyentimkiem = view.findViewById(R.id.tv_chuyentimkiem);
//
//        tv_chuyentimkiem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Manhinhtimkiem.class);
//                startActivity(intent);
//            }
//        });
//    }

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

    public void bt_sp(View view)
    {
        bt_sanpham = view.findViewById(R.id.bt_sanpham);
        bt_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đã nhấn!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void listSP(View view)
    {
        gv_ds = view.findViewById(R.id.gv_ds);
        arrayList = new ArrayList<>();

        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));
        arrayList.add(new Contact(R.drawable.son));

        customAdapter = new CustomAdapter(getContext(), R.layout.row, arrayList);
        gv_ds.setAdapter(customAdapter);
    }
}
