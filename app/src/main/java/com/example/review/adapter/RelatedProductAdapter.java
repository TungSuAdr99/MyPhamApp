package com.example.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.review.R;
import com.example.review.model.RelatedProduct;

import java.util.ArrayList;
import java.util.List;

public class RelatedProductAdapter extends ArrayAdapter<RelatedProduct> {
    private Context context;
    private int resource;
    private ArrayList<RelatedProduct> arrContact;

    public RelatedProductAdapter(@NonNull Context context, int resource, @NonNull List<RelatedProduct> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = (ArrayList<RelatedProduct>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//làm theo

        ViewHoldel viewHoldel;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_related_product, parent, false);

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

        RelatedProduct contact = arrContact.get(position);//MainActivity run: arrayList(VD: DS có 5 số đt) -> CustomAdapter -> class CustomAdapter -> objects -> arrContact "lấy số đt thứ..."

        Glide.with(context).load(contact.getImage()).into(viewHoldel.imgAnh);
        viewHoldel.tvName.setText(contact.getName());
        viewHoldel.tvNumber.setText(contact.getLike());

        return convertView;
    }

    public class ViewHoldel//tạo đầu tiên
    {
        ImageView imgAnh;
        TextView tvName;
        TextView tvNumber;
    }
}
