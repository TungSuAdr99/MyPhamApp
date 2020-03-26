package com.example.review.ProductActivity.GirdView_ProductActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.review.R;

import java.util.ArrayList;


public class ProductCustomAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<ProductContact> arrContact;

    public ProductCustomAdapter(Context context, int resource, ArrayList<ProductContact> arrContact) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
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

        ProductContact productContact = arrContact.get(position);
        holdel.imgAnh.setBackgroundResource(productContact.getPrd_gv_img());//lấy ảnh qua kiểu int
        holdel.tvName.setText(productContact.getPrd_gv_name());

        return convertView;
    }

    private class ViewHoldel//tạo đầu tiên
    {
        ImageView imgAnh;
        TextView tvName;
    }
}
