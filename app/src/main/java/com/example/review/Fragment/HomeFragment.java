package com.example.review.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.review.activity.Manhinhtimkiem;
import com.example.review.R;
import com.example.review.adapter.SanPhamAdapter;
import com.example.review.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Button bt_sanpham;
    private Spinner spn_danhsachvung;
    private GridView gv_ds;
    ArrayList<SanPham> arrayList;
    SanPhamAdapter customAdapter;
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

        listV(view);
        listSP(view);
        bt_sp(view);
        chuyenQC(view);
        chuyenTimkiem(view);
    }

    public void chuyenTimkiem(View view)
    {
        tv_chuyentimkiem = view.findViewById(R.id.tv_chuyentimkiem);

        tv_chuyentimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Manhinhtimkiem.class);
                startActivity(intent);
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

        arrayList.add(new SanPham(R.drawable.son, "Son Em Cosmetics", "150.000đ", "50%"));
        arrayList.add(new SanPham(R.drawable.son, "Son Dưỡng", "300.000đ", "30%"));
        arrayList.add(new SanPham(R.drawable.son, "Son Emax", "100.000đ", "0%"));

        customAdapter = new SanPhamAdapter(getContext(), R.layout.row, arrayList);
        gv_ds.setAdapter(customAdapter);
    }

    public void listV(View view)
    {
        spn_danhsachvung = view.findViewById(R.id.spn_danhsachvung);
        List<String> list =new ArrayList<>();
        list.add("An Giang");
        list.add("Bà Rịa – Vũng Tàu");
        list.add("Bắc Giang");
        list.add("Bắc Kạn");
        list.add("Bạc Liêu");
        list.add("Bắc Ninh");
        list.add("Bến Tre");
        list.add("Bình Định");
        list.add("Bình Dương");
        list.add("Bình Phước");
        list.add("Bình Thuận");
        list.add("Cà Mau");
        list.add("Cần Thơ");
        list.add("Cao Bằng");
        list.add("Đà Nẵng");
        list.add("Đắk Lắk");
        list.add("Đắk Nông");
        list.add("Điện Biên");
        list.add("Đồng Nai");
        list.add("Đồng Tháp");
        list.add("Gia Lai");
        list.add("Hà Giang");
        list.add("Hà Nam");
        list.add("Hà Nội");
        list.add("Hà Tĩnh");
        list.add("Hải Dương");
        list.add("Hải Phòng");
        list.add("Hậu Giang");
        list.add("Hòa Bình");
        list.add("Hưng Yên");
        list.add("Khánh Hòa");
        list.add("Kiên Giang");
        list.add("Kon Tum");
        list.add("Lai Châu");
        list.add("Lâm Đồng");
        list.add("Lạng Sơn");
        list.add("Lào Cai");
        list.add("Long An");
        list.add("Nam Định");
        list.add("Nghệ An");
        list.add("Ninh Bình");
        list.add("Ninh Thuận");
        list.add("Phú Thọ");
        list.add("Phú Yên");
        list.add("Quảng Bình");
        list.add("Quảng Nam");
        list.add("Quảng Ngãi");
        list.add("Quảng Ninh");
        list.add("Quảng Trị");
        list.add("Sóc Trăng");
        list.add("Sơn La");
        list.add("Tây Ninh");
        list.add("Thái Bình");
        list.add("Thái Nguyên");
        list.add("Thanh Hóa");
        list.add("Thừa Thiên Huế");
        list.add("Tiền Giang");
        list.add("TP Hồ Chí Minh");
        list.add("Trà Vinh");
        list.add("Tuyên Quang");
        list.add("Vĩnh Long");
        list.add("Vĩnh Phúc");
        list.add("Yên Bái");

        ArrayAdapter<String> adapte = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list);//Trung gian để đưa data lên view
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);//tạo ra nút để tích
        spn_danhsachvung.setAdapter(adapte);//lấy data lên cho sp1
    }

}
