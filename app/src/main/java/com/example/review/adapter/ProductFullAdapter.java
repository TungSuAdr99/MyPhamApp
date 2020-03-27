package com.example.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.review.R;
import com.example.review.model.Product;

import java.util.ArrayList;


public class ProductFullAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<Product> arrContact;
    private onClick mClick;

    public ProductFullAdapter(Context context, int resource, ArrayList<Product> arrContact) {
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public int getCount() {
        return arrContact.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoldel holdel;
        if(convertView == null)
        {
            holdel = new ViewHoldel();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
            holdel.imgAnh = convertView.findViewById(R.id.product_gv_img_anhsp);
            holdel.tvName = convertView.findViewById(R.id.product_gv_tv_sp);
            convertView.setTag(holdel);
        }
        else {
            holdel = (ViewHoldel) convertView.getTag();
        }

        Product productContact = arrContact.get(position);
        //holdel.imgAnh.setBackgroundResource(productContact.getPrd_gv_img());//lấy ảnh qua kiểu int
        holdel.tvName.setText(productContact.getName());
        Glide.with(context).load(productContact.getImage()).into(holdel.imgAnh);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClick.click(position);
            }
        });

        return convertView;
    }

    private class ViewHoldel//tạo đầu tiên
    {
        ImageView imgAnh;
        TextView tvName;
    }

    public void setClick(onClick click){
        mClick = click;
    }

    public interface onClick{
        void click(int position);
    }
}
