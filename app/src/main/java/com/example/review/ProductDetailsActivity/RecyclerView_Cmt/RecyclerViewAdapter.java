package com.example.review.ProductDetailsActivity.RecyclerView_Cmt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.review.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<ContactRecycler> contactRecyclers;
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mNames, ArrayList<ContactRecycler> contactRecyclers) {
        this.contactRecyclers = contactRecyclers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_atvt_product_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //dùng ảnh tải từ database
        /*Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.img);*/

        holder.name.setText(contactRecyclers.get(position).getName());
        holder.cmt.setText(contactRecyclers.get(position).getCmt());
        holder.img.setImageResource(contactRecyclers.get(position).getImage());
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, contacts.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return contactRecyclers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name;
        TextView cmt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mhsp_img_recycle);
            name = itemView.findViewById(R.id.mhsp_name_recycle);
            cmt =  itemView.findViewById(R.id.mhsp_binhluan_recycle);

        }
    }
}
