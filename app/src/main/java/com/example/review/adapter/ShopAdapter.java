package com.example.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.review.R;
import com.example.review.model.Product;
import com.example.review.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends ArrayAdapter<Shop> {
    private Context context;
    private int resource;
    private ArrayList<Shop> arrContact;

    public ShopAdapter(@NonNull Context context, int resource, @NonNull List<Shop> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = (ArrayList<Shop>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//làm theo

        ViewHoldel viewHoldel;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);

            viewHoldel = new ViewHoldel();

            viewHoldel.imgAnh = convertView.findViewById(R.id.img_anhSP);
            convertView.setTag(viewHoldel);//lưu lại những thằng đã khởi tạo trên vào viewHoldel
        }
        else
        {
            viewHoldel= (ViewHoldel) convertView.getTag();//lấy cái đã khởi tạo ra dùng
        }
        Shop contact = arrContact.get(position);//MainActivity run: arrayList(VD: DS có 5 số đt) -> CustomAdapter -> class CustomAdapter -> objects -> arrContact "lấy số đt thứ..."

        Glide.with(context).load(contact.getImage()).into(viewHoldel.imgAnh);

        return convertView;
    }

    public class ViewHoldel//tạo đầu tiên
    {
        ImageView imgAnh;
    }
}
