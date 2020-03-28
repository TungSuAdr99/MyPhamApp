package com.example.review.CmtActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.review.CmtActivity.RecyclerView_Atvt_Cmt.ContactRecycler;
import com.example.review.CmtActivity.RecyclerView_Atvt_Cmt.RecyclerViewAdapter;
import com.example.review.R;

import java.util.ArrayList;

public class CmtActivity extends AppCompatActivity {
    boolean Binhluan = true;
    private RecyclerView mhsp_lo_trongbinhluan;
    private ArrayList<ContactRecycler> contacts = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmt);
        XLRecycler();
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
        RecyclerView recyclerView =findViewById(R.id.mhcmt_lo_trongbinhluan);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames,contacts);
        recyclerView.setAdapter(adapter);
    }
}
