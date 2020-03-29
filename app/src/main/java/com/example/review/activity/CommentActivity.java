package com.example.review.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.review.model.Comment;
import com.example.review.model.User;
import com.example.review.adapter.CommentAdapter;
import com.example.review.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private final static int IMAGE_PICK_GALLERY_CODE = 1010;

    private RecyclerView rvComment;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private Comment comment = new Comment();

    private DatabaseReference userRef;

    private DatabaseReference userCommentRef;
    private StorageReference commentImgRef;
    private String downloadUrl ="";

    private EditText edtComment;
    private Button btnSend;
    private ImageView imgGallery;
    private Uri imageCommentUri;
    private ImageView imgComment;

    private CommentAdapter adapter;

    private int currentUserPosition = -1;

    private int position;
    private int spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initView();

        takeIntent();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userCommentRef = FirebaseDatabase.getInstance().getReference().child("UserComments");
        commentImgRef = FirebaseStorage.getInstance().getReference().child("commentImages");

        retrieveUsers();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleComment();
            }
        });

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGallery();
            }
        });

        retrieveComments();
    }

    private void takeIntent(){
        Intent intent = getIntent();
        spinner = intent.getIntExtra("spinner", 0);
        position = intent.getIntExtra("position", 0);
    }

    private void initView() {
        edtComment = findViewById(R.id.edt_comment);
        btnSend = findViewById(R.id.btn_send);
        imgGallery = findViewById(R.id.img_gallery);
        imgComment = findViewById(R.id.img_comment);
        rvComment = findViewById(R.id.rv_comment);
    }

    private void pickGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_PICK_GALLERY_CODE){
            imageCommentUri = data.getData();

            if(imageCommentUri != null){
                imgComment.setImageURI(imageCommentUri);
                setVisibility(true);
                comment.setImageComment(imageCommentUri.toString());
            }
        }
    }

    private void setVisibility(boolean isCheck){
        if(isCheck){
            imgComment.setVisibility(View.VISIBLE);
        }else {
            imgComment.setVisibility(View.GONE);
        }
    }

    public  void  handleComment()
    {
        setVisibility(false);

        final String strComment = edtComment.getText().toString();
        if(!strComment.equals("") && imageCommentUri != null){

            final StorageReference filePath = commentImgRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            filePath.putFile(imageCommentUri)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            return filePath.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){

                                downloadUrl = task.getResult().toString();

                                comment.setImageComment(downloadUrl);
                                comment.setComment(strComment);
                                comment.setImageUser(users.get(currentUserPosition).getImage());
                                comment.setNameUser(users.get(currentUserPosition).getName());
                                comment.setUidUser(users.get(currentUserPosition).getUid());
                                comment.setSpinner(spinner +"");
                                comment.setPosition(position +"");

                                userCommentRef.push().setValue(comment);
                            }
                        }
                    });

            edtComment.setText("");

        }
        else  if(!strComment.equals("") && imageCommentUri == null){

            edtComment.setText("");

            comment.setImageComment(downloadUrl);
            comment.setComment(strComment);
            comment.setImageUser(users.get(currentUserPosition).getImage());
            comment.setNameUser(users.get(currentUserPosition).getName());
            comment.setUidUser(users.get(currentUserPosition).getUid());
            comment.setSpinner(spinner +"");
            comment.setPosition(position +"");

            userCommentRef.push().setValue(comment);
        }
        else if(strComment.equals("") && imageCommentUri != null){

            final StorageReference filePath = commentImgRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            filePath.putFile(imageCommentUri)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    downloadUrl = task.getResult().toString();

                    comment.setImageComment(downloadUrl);
                    comment.setComment(strComment);
                    comment.setImageUser(users.get(currentUserPosition).getImage());
                    comment.setNameUser(users.get(currentUserPosition).getName());
                    comment.setUidUser(users.get(currentUserPosition).getUid());
                    comment.setSpinner(spinner +"");
                    comment.setPosition(position +"");

                    userCommentRef.push().setValue(comment);
                }
            });
        }
        else {
            Toast.makeText(CommentActivity.this, "Viết nội dung hoặc chọn ảnh để bình luận", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveUsers(){
        userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            users.add(user);
                        }

                        for(int i=0; i<users.size(); i++){
                            if(users.get(i).getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                Log.e("KMFR", ":))))");
                                currentUserPosition = i;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void retrieveComments(){
        final ArrayList<Comment> arrComments = new ArrayList<>();
        userCommentRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        comments.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Comment comment = snapshot.getValue(Comment.class);
                            comment.setKey(snapshot.getKey());
                            comments.add(comment);
                        }

                        arrComments.clear();
                        for(int i=0; i<comments.size(); i++){

                            int s = Integer.parseInt(comments.get(i).getSpinner());
                            int p = Integer.parseInt(comments.get(i).getPosition());

                            if(s == spinner && p == position){

                                arrComments.add(comments.get(i));

                            }
                        }

                        adapter = new CommentAdapter(CommentActivity.this, arrComments);
                        rvComment.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
