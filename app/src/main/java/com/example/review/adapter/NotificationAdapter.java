package com.example.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.review.R;
import com.example.review.model.Comment;
import com.example.review.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{

    private ArrayList<Comment> arrNotification;
    private ArrayList<Product> arrProducts = new ArrayList<>();
    private ArrayList<String> arrNameProducts = new ArrayList<>();
    private Context context;
    private OnClick onClick;

    public NotificationAdapter(ArrayList<Comment> arrNotification, Context context) {
        this.arrNotification = arrNotification;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(view, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(arrNotification.get(position));
        holder.retrieveCosmetics(position);
    }

    @Override
    public int getItemCount() {
        return arrNotification.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txtNameUserNotification, txtTextNotification, txtNameProductNotification, txtText1Notification;
        private ImageView imgUserCommentNotification;
        private OnClick onClick;

        public MyViewHolder(@NonNull View itemView, OnClick onClick) {
            super(itemView);

            txtNameUserNotification = itemView.findViewById(R.id.txt_name_user_notification);
            txtTextNotification = itemView.findViewById(R.id.txt_text_notification);
            imgUserCommentNotification = itemView.findViewById(R.id.img_user_notification);
            txtNameProductNotification = itemView.findViewById(R.id.txt_name_product_notification);
            txtText1Notification = itemView.findViewById(R.id.txt_text1_notification);

            this.onClick = onClick;
            itemView.setOnClickListener(this);
        }

        void setData(Comment comment){
            txtNameUserNotification.setText(comment.getNameUser());

            if(!comment.getImageUser().equals("")){
                Glide.with(context)
                        .load(comment.getImageUser())
                        .into(imgUserCommentNotification);
            }else {
                imgUserCommentNotification.setImageResource(R.drawable.ic_profile);
            }
        }

        private void retrieveCosmetics(final int position){
            DatabaseReference cosmeticRef = FirebaseDatabase.getInstance().getReference().child("MyPham");
            cosmeticRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    arrProducts.clear();

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Product product = snapshot.getValue(Product.class);
                        product.setKey(snapshot.getKey());

                        arrProducts.add(product);
                    }

                    for (int i=0; i<arrNotification.size(); i++){
                        for(int j=0; j<arrProducts.size(); j++){
                            if(arrNotification.get(i).getKeyProduct().equals(arrProducts.get(j).getKey())){
                                arrNameProducts.add(arrProducts.get(j).getName());
                            }
                        }
                    }

                    txtTextNotification.setText("cũng đã bình luận về sản phẩm");
                    txtNameProductNotification.setText(arrNameProducts.get(position));
                    txtText1Notification.setText(" mà bạn bình luận");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            onClick.click(getAdapterPosition());
        }
    }

    public void setClick(OnClick onClick){
        this.onClick = onClick;
    }

    public interface OnClick{
        void click(int position);
    }
}
