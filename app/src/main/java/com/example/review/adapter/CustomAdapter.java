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

import com.example.review.R;
import com.example.review.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private ArrayList<Contact> arrContact;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = (ArrayList<Contact>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//làm theo

        ViewHoldel viewHoldel;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

            viewHoldel = new ViewHoldel();

            viewHoldel.imgAnh = convertView.findViewById(R.id.img_anhSP);
            convertView.setTag(viewHoldel);//lưu lại những thằng đã khởi tạo trên vào viewHoldel
        }
        else
        {
            viewHoldel= (ViewHoldel) convertView.getTag();//lấy cái đã khởi tạo ra dùng
        }



        Contact contact = arrContact.get(position);//MainActivity run: arrayList(VD: DS có 5 số đt) -> CustomAdapter -> class CustomAdapter -> objects -> arrContact "lấy số đt thứ..."

        viewHoldel.imgAnh.setBackgroundResource(contact.getImage() );//lấy ảnh qua kiểu int
        return convertView;
    }

    public class ViewHoldel//tạo đầu tiên
    {
        ImageView imgAnh;
    }
}
