package com.example.review.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.review.ProductDetailsActivity.ListView_Product_LienQuan.Contact_Product_LienQuan;
import com.example.review.ProductDetailsActivity.ListView_Product_LienQuan.CustomAdapter_Product_LienQuan;
import com.example.review.ProductDetailsActivity.RecyclerView_Cmt.ContactRecycler;
import com.example.review.ProductDetailsActivity.RecyclerView_Cmt.RecyclerViewAdapter;
import com.example.review.ProductDetailsActivity.TreeView.MyHolder;
import com.example.review.ProductDetailsActivity.TreeView.model.TreeNode;
import com.example.review.ProductDetailsActivity.TreeView.view.AndroidTreeView;
import com.example.review.R;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView mhsp_img_thich;
    private LinearLayout mhsp_lo_thich;
    private LinearLayout mhsp_lo_binhluan;
    private RecyclerView mhsp_lo_trongbinhluan;
    boolean Yeuthih = true;
    boolean Binhluan = true;
    ArrayList<Contact_Product_LienQuan> arrayList;
    CustomAdapter_Product_LienQuan customAdapter;
    ListView mhsp_lv_sanphamlienquan;
    private ArrayList<ContactRecycler> contacts = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();

    //getIntent
    private TextView txtName;
    private ImageView imageView;
    private String name;
    private String price;
    private String image;
    private String ingredient1;
    private String ingredient2;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        txtName = findViewById(R.id.txt_name);
        imageView = findViewById(R.id.imageView);

        takeIntent();
        TreeView();
        XLTextViewThich();
        XLLayoutBinhLuan();
        XLRecycler();
        LVSpLienquan();

        txtName.setText(name);
        Glide.with(this).load(image).into(imageView);
    }

    private void takeIntent(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        image = intent.getStringExtra("image");
        ingredient1 = intent.getStringExtra("ingredient1");
        ingredient2 = intent.getStringExtra("ingredient1");
        detail = intent.getStringExtra("detail");
    }

    public  void  XLRecycler()
    {
        contacts.add(new ContactRecycler(R.drawable.anhsp, "Tùng", "Dùng tốt"));
        contacts.add(new ContactRecycler(R.drawable.anhsp, "Su", "Chất lượng"));
        initRecyclerView();
    }

    private void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView =findViewById(R.id.mhsp_lo_trongbinhluan);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames,contacts);
        recyclerView.setAdapter(adapter);
    }

    public void LVSpLienquan()
    {
        mhsp_lv_sanphamlienquan = findViewById(R.id.mhsp_lv_sanphamlienquan);
        arrayList = new ArrayList<>();//tạo 1 mảng

        arrayList.add(new Contact_Product_LienQuan(R.drawable.anhsp,"Kem chống nắng","9.999 người thích"));
        arrayList.add(new Contact_Product_LienQuan(R.drawable.anhsp,"Kem dưỡng da","8.888 người thích"));
        arrayList.add(new Contact_Product_LienQuan(R.drawable.anhsp,"Kem chống nắng","9.999 người thích"));

        customAdapter = new CustomAdapter_Product_LienQuan(this, R.layout.row, arrayList);
        mhsp_lv_sanphamlienquan.setAdapter(customAdapter);
    }

    public void XLLayoutBinhLuan()
    {
        mhsp_lo_binhluan = findViewById(R.id.mhsp_lo_binhluan);
        mhsp_lo_trongbinhluan = findViewById(R.id.mhsp_lo_trongbinhluan);

        mhsp_lo_trongbinhluan.setVisibility(View.GONE);

        mhsp_lo_binhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Binhluan)
                {
                    mhsp_lo_trongbinhluan.setVisibility(View.VISIBLE);
                    Binhluan = false;
                }
                else
                {
                    mhsp_lo_trongbinhluan.setVisibility(View.GONE);
                    Binhluan = true;
                }
            }
        });
    }

    public void XLTextViewThich()
    {
        mhsp_lo_thich = findViewById(R.id.mhsp_lo_thich);
        mhsp_img_thich = findViewById(R.id.mhsp_img_thich);

        mhsp_lo_thich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Yeuthih){
                    mhsp_img_thich.setImageResource(R.drawable.tym2);
                    Yeuthih = false;
                }else {
                    mhsp_img_thich.setImageResource(R.drawable.tym1);
                    Yeuthih = true;
                }
                Toast.makeText(ProductDetailsActivity.this, "aa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TreeView()
    {
        //Root
        TreeNode root = TreeNode.root();

        //Parent
        MyHolder.IconTreeItem TTSP = new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Thông tin sản phẩm");
        TreeNode parentTTSP = new TreeNode(TTSP).setViewHolder(new MyHolder(getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));

        //Child
        MyHolder.IconTreeItem childItemTTSP = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Tên:" + name);
        TreeNode childTTSP = new TreeNode(childItemTTSP).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.activity_proddetails_child_girdview, 25));

        //Child
        MyHolder.IconTreeItem childItem1TTSP = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Giá:" + price);
        TreeNode child1TTSP = new TreeNode(childItem1TTSP).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.activity_proddetails_child_girdview, 25));

        //Add child.
        parentTTSP.addChildren(childTTSP);
        parentTTSP.addChildren(child1TTSP);

        //Parent1
        MyHolder.IconTreeItem BTP = new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Bảng thành phần");
        TreeNode parentBTP = new TreeNode(BTP).setViewHolder(new MyHolder(getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));

        //Child
        MyHolder.IconTreeItem childItemBTP = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Thành phần 1:" + ingredient1);
        TreeNode childBTP = new TreeNode(childItemBTP).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.activity_proddetails_child_girdview, 25));

        //Child
        MyHolder.IconTreeItem childItem1BTP = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Thành phần 2:" + ingredient2);
        TreeNode child1BTP = new TreeNode(childItem1BTP).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.activity_proddetails_child_girdview, 25));

        //Add child.
        parentBTP.addChildren(childBTP);
        parentBTP.addChildren(child1BTP);

        //Parent2
        MyHolder.IconTreeItem CSD = new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Cách sử dụng");
        TreeNode parentCSD = new TreeNode(CSD).setViewHolder(new MyHolder(getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));

        //Child
        MyHolder.IconTreeItem childItemCSD = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Chi tiết" + detail);
        TreeNode childCSD = new TreeNode(childItemCSD).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.activity_proddetails_child_girdview, 25));

        //Add child.
        parentCSD.addChildren(childCSD);

        //Add parent
        root.addChild(parentTTSP);
        root.addChild(parentBTP);
        root.addChild(parentCSD);

        //Add AndroidTreeView into view.
        AndroidTreeView tView = new AndroidTreeView(getApplicationContext(), root);
        ((LinearLayout) findViewById(R.id.ll_parent)).addView(tView.getView());
    }
}
