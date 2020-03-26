package com.example.review.ProductDetailsActivity.ListView_Product_LienQuan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.review.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter_Product_LienQuan extends ArrayAdapter<Contact_Product_LienQuan> {
    private Context context;
    private int resource;
    private ArrayList<Contact_Product_LienQuan> arrContact;

    public CustomAdapter_Product_LienQuan(@NonNull Context context, int resource, @NonNull List<Contact_Product_LienQuan> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = (ArrayList<Contact_Product_LienQuan>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//làm theo

        ViewHoldel viewHoldel;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.forn_listview_atvt_product_lienquan, parent, false);

            viewHoldel = new ViewHoldel();

            viewHoldel.imgAnh = convertView.findViewById(R.id.imgAnh);
            viewHoldel.tvName = convertView.findViewById(R.id.tvName);
            viewHoldel.tvNumber = convertView.findViewById(R.id.tvNumber);

            convertView.setTag(viewHoldel);//lưu lại những thằng đã khởi tạo trên vào viewHoldel
        }
        else
        {
            viewHoldel= (ViewHoldel) convertView.getTag();//lấy cái đã khởi tạo ra dùng
        }



        Contact_Product_LienQuan contact = arrContact.get(position);//MainActivity run: arrayList(VD: DS có 5 số đt) -> CustomAdapter -> class CustomAdapter -> objects -> arrContact "lấy số đt thứ..."

        viewHoldel.imgAnh.setBackgroundResource(contact.getmImg() );//lấy ảnh qua kiểu int

        /*viewHoldel.imgAnh.setBackgroundResource(R.drawable.su);*/
        viewHoldel.tvName.setText(contact.getmName());
        viewHoldel.tvNumber.setText(contact.getmNumber());

        return convertView;
    }

    public class ViewHoldel//tạo đầu tiên
    {
        ImageView imgAnh;
        TextView tvName;
        TextView tvNumber;
    }
}
