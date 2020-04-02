package com.example.review.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.review.R;
import com.example.review.activity.CommentActivity;
import com.example.review.adapter.NotificationAdapter;
import com.example.review.model.Comment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements NotificationAdapter.OnClick{

    private RecyclerView rvNotification;
    private NotificationAdapter adapter;

    private DatabaseReference commentRef;
    private ArrayList<Comment> arrComments = new ArrayList<>();
    private ArrayList<String> arrKeyProduct = new ArrayList<>();
    ArrayList<Comment> arrNotifications = new ArrayList<>();

    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvNotification = view.findViewById(R.id.rv_notification);

        commentRef = FirebaseDatabase.getInstance().getReference().child("UserComments");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onStart() {
        super.onStart();

        retrieveUserComments();
    }

    private void retrieveUserComments(){
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrKeyProduct.clear();
                arrComments.clear();
                arrNotifications.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);

                    if(comment.getUidUser().equals(currentUserId)){
                        arrKeyProduct.add(comment.getKeyProduct());
                    }

                    arrComments.add(comment);
                }

                for(int i=arrComments.size()-1; i>=0; i--){

                    if(!arrComments.get(i).getUidUser().equals(currentUserId)){

                        for(int j=arrKeyProduct.size()-1; j>=0; j--){

                            if(arrComments.get(i).getKeyProduct().equals(arrKeyProduct.get(j))) {
                                arrNotifications.add(arrComments.get(i));

                                break;
                            }
                        }
                    }
                }

                adapter = new NotificationAdapter(arrNotifications, getContext());
                adapter.setClick(NotificationFragment.this);
                rvNotification.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void click(int position) {
        Intent intent = new Intent(getContext(), CommentActivity.class);
        intent.putExtra("spinner", arrNotifications.get(position).getSpinner());
        intent.putExtra("keyProduct", arrNotifications.get(position).getKeyProduct());
        startActivity(intent);
    }
}
