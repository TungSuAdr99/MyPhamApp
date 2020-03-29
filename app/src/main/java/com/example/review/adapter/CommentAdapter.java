package com.example.review.adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.review.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<Comment> comments;
    private Context mContext;

    public CommentAdapter(Context mContext, ArrayList<Comment> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //dùng ảnh tải từ database
        if(!comments.get(position).getImageUser().equals("")){
            Glide.with(mContext)
                    .load(comments.get(position).getImageUser())
                    .into(holder.imgUser);
        }else{
            holder.imgUser.setImageResource(R.drawable.ic_profile);
        }

        if(!comments.get(position).getImageComment().equals("")){
            Glide.with(mContext)
                    .load(comments.get(position).getImageComment())
                    .into(holder.imgComment);
        }

        holder.txtNameUser.setText(comments.get(position).getNameUser());
        holder.txtComment.setText(comments.get(position).getComment());
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, contacts.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgUser;
        TextView txtNameUser;
        TextView txtComment;
        ImageView imgComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            txtNameUser = itemView.findViewById(R.id.txt_name_user);
            txtComment =  itemView.findViewById(R.id.txt_comment_user);
            imgComment = itemView.findViewById(R.id.img_comment_user);
        }
    }

}
