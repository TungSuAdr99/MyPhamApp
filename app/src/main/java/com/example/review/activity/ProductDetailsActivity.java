package com.example.review.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.review.ProductDetailsActivity.ListView_Product_LienQuan.Contact_Product_LienQuan;
import com.example.review.ProductDetailsActivity.ListView_Product_LienQuan.CustomAdapter_Product_LienQuan;

import com.example.review.ProductDetailsActivity.TreeView.MyHolder;
import com.example.review.ProductDetailsActivity.TreeView.model.TreeNode;
import com.example.review.ProductDetailsActivity.TreeView.view.AndroidTreeView;
import com.example.review.R;
import com.example.review.adapter.CommentAdapter;
import com.example.review.model.Comment;
import com.example.review.model.Like;
import com.example.review.model.Review;
import com.example.review.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView imgLove;
    private LinearLayout linearLayoutLike;
    boolean isLove = true;
    private LinearLayout linearLayoutComment;
    private TextView txtSizeLike;
    private LinearLayout llShare;
    private int position;
    private int spinner;
    private DatabaseReference userCommentRef;
    private RatingBar ratingBar;
    private TextView txtReview;
    private DatabaseReference userReviewsRef;

    private CircleImageView imgUserComment;
    private TextView txtUserComment;
    private TextView txtUserName;

    ArrayList<Contact_Product_LienQuan> arrayList;
    CustomAdapter_Product_LienQuan customAdapter;
    private ListView mhsp_lv_sanphamlienquan;
    private TextView txtSeeComment;

    //getIntent
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtIngredient;
    private ImageView imageView;
    private String name;
    private String price;
    private String image;
    private String ingredient;
    private String detail;

    private int sizeLike = 0;

    private FirebaseUser user;
    private DatabaseReference userLikeRef;

    private ArrayList<Like> likes = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initView();

        userLikeRef = FirebaseDatabase.getInstance().getReference().child("userLikes");
        userCommentRef = FirebaseDatabase.getInstance().getReference().child("UserComments");
        userReviewsRef = FirebaseDatabase.getInstance().getReference().child("UserReviews");

        retrieveUserLikes();
        retrieveComment();
        retrieveReviews();

        takeIntent();
        TreeView();
        LVSpLienquan();

        XLTextViewThich();
        switchToAtvtCmt();
        user = FirebaseAuth.getInstance().getCurrentUser();

        txtName.setText("Tên: " + name);
        txtPrice.setText("Gía: " + price);
        txtIngredient.setText("Thành phần: " + ingredient);
        Glide.with(this).load(image).into(imageView);

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, image);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        txtReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = new Review(user.getUid(), ratingBar.getRating()+"", spinner+"", position+"");

                userReviewsRef.push().setValue(review);
            }
        });
    }

    private void initView() {
        txtName = findViewById(R.id.txt_name);
        txtPrice = findViewById(R.id.txt_price);
        txtIngredient = findViewById(R.id.txt_ingredient);
        imageView = findViewById(R.id.imageView);
        txtSizeLike = findViewById(R.id.txt_size_like);
        llShare = findViewById(R.id.ll_share);
        imgUserComment = findViewById(R.id.img_user_comment);
        txtUserName = findViewById(R.id.txt_user_name);
        txtUserComment = findViewById(R.id.txt_user_comment);
        ratingBar = findViewById(R.id.rating_bar);
        txtReview = findViewById(R.id.txt_review);
    }

    private void switchToAtvtCmt()
    {
        linearLayoutComment = findViewById(R.id.ll_comment);
        txtSeeComment = findViewById(R.id.txt_see_comment);

        linearLayoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    Intent intent = new Intent(ProductDetailsActivity.this, CommentActivity.class);
                    intent.putExtra("spinner", spinner);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }else {
                    Toast.makeText(ProductDetailsActivity.this,
                            "Bạn cần đăng nhập để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSeeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    Intent intent = new Intent(ProductDetailsActivity.this, CommentActivity.class);
                    intent.putExtra("spinner", spinner);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }else {
                    Toast.makeText(ProductDetailsActivity.this,
                            "Bạn cần đăng nhập để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void takeIntent(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        image = intent.getStringExtra("image");
        ingredient = intent.getStringExtra("ingredient");
        detail = intent.getStringExtra("detail");
        spinner = intent.getIntExtra("spinner", 0);
        position = intent.getIntExtra("position", 0);
    }

    public void LVSpLienquan()
    {
        mhsp_lv_sanphamlienquan = findViewById(R.id.mhsp_lv_sanphamlienquan);
        arrayList = new ArrayList<>();//tạo 1 mảng

        arrayList.add(new Contact_Product_LienQuan(R.drawable.anhsp,"Kem chống nắng","9.999 người thích"));
        arrayList.add(new Contact_Product_LienQuan(R.drawable.anhsp,"Kem dưỡng da","8.888 người thích"));
        arrayList.add(new Contact_Product_LienQuan(R.drawable.anhsp,"Kem chống nắng","9.999 người thích"));

        customAdapter = new CustomAdapter_Product_LienQuan(this, R.layout.row, arrayList);
        mhsp_lv_sanphamlienquan.setAdapter(customAdapter);
    }

    public void XLTextViewThich()
    {
        linearLayoutLike = findViewById(R.id.ll_like);
        imgLove = findViewById(R.id.img_love);

        linearLayoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    if(isLove){
                        isLove = false;
                        imgLove.setImageResource(R.drawable.tym2);
                        Like like = new Like(spinner+"", position+"", user.getUid());
                        userLikeRef.push().setValue(like);
                    }else {

                        isLove = true;

                        for(int i=0; i<likes.size(); i++){

                            int s = Integer.parseInt(likes.get(i).getSpinner());
                            int p = Integer.parseInt(likes.get(i).getPosition());

                            if(s == spinner && p == position && likes.get(i).getUidUser().equals(user.getUid())){
                                imgLove.setImageResource(R.drawable.tym1);
                                userLikeRef.child(likes.get(i).getKey()).removeValue();
                            }
                        }
                    }
                }else {
                    Toast.makeText(ProductDetailsActivity.this,
                            "Bạn cần đăng nhập để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void TreeView()
    {
        //Root
        TreeNode root = TreeNode.root();

        //Parent2
        MyHolder.IconTreeItem CSD = new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Cách sử dụng");
        TreeNode parentCSD = new TreeNode(CSD).setViewHolder(new MyHolder(getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));

        //Child
        MyHolder.IconTreeItem childItemCSD = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Chi tiết: " + detail);
        TreeNode childCSD = new TreeNode(childItemCSD).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.activity_proddetails_child_girdview, 25));

        //Add child.
        parentCSD.addChildren(childCSD);

        //Add parent
        root.addChild(parentCSD);

        //Add AndroidTreeView into view.
        AndroidTreeView tView = new AndroidTreeView(getApplicationContext(), root);
        ((LinearLayout) findViewById(R.id.ll_parent)).addView(tView.getView());
    }

    private void retrieveUserLikes(){
        userLikeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.clear();
                Log.e("KMFT", "55=");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Like like = snapshot.getValue(Like.class);
                    like.setKey(snapshot.getKey());

                    likes.add(like);
                }

                for(int i=0; i<likes.size(); i++){

                    int s = Integer.parseInt(likes.get(i).getSpinner());
                    int p = Integer.parseInt(likes.get(i).getPosition());

                    if(s == spinner && p == position){
                        sizeLike++;
                        Log.e("KMFT", sizeLike + "=");
                    }

                    if(s == spinner && p == position && likes.get(i).getUidUser().equals(user.getUid())){
                        imgLove.setImageResource(R.drawable.tym2);
                        isLove = false;

                        break;
                    }else {
                        isLove = true;
                        imgLove.setImageResource(R.drawable.tym1);
                    }
                }

                sizeLike = 0;
                for(int i=0; i<likes.size(); i++){

                    int s = Integer.parseInt(likes.get(i).getSpinner());
                    int p = Integer.parseInt(likes.get(i).getPosition());

                    if(s == spinner && p == position){
                        sizeLike++;
                        Log.e("KMFT", sizeLike + "=");
                    }
                }

                txtSizeLike.setText(sizeLike + " lượt thích");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveComment(){
        final ArrayList<Comment> arrComments = new ArrayList<>();
        userCommentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //get all comments
                comments.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);
                    comment.setKey(snapshot.getKey());
                    comments.add(comment);
                }

                //on take comments by spinner and position
                arrComments.clear();
                for(int i=0; i<comments.size(); i++){

                    int s = Integer.parseInt(comments.get(i).getSpinner());
                    int p = Integer.parseInt(comments.get(i).getPosition());

                    if(s == spinner && p == position){
                        arrComments.add(comments.get(i));
                    }
                }

                if(arrComments.size() != 0){
                    imgUserComment.setVisibility(View.VISIBLE);
                    txtUserName.setVisibility(View.VISIBLE);
                    txtUserComment.setVisibility(View.VISIBLE);
                    //get Random
                    Random rand = new Random();
                    int positionRandom = rand.nextInt(arrComments.size());

                    //set user comment
                    if(!arrComments.get(positionRandom).getImageUser().equals("")){
                        Glide.with(ProductDetailsActivity.this)
                                .load(arrComments.get(positionRandom).getImageUser())
                                .into(imgUserComment);
                    }else {
                        imgUserComment.setImageResource(R.drawable.ic_profile);
                    }

                    txtUserName.setText(arrComments.get(positionRandom).getNameUser());

                    if(!arrComments.get(positionRandom).getComment().equals(""))
                        txtUserComment.setText(arrComments.get(positionRandom).getComment());
                    else
                        txtUserComment.setText("Bình luận");

                    txtSeeComment.setText("Xem tất cả bình luận");
                }else {
                    imgUserComment.setVisibility(View.GONE);
                    txtUserName.setVisibility(View.GONE);
                    txtUserComment.setVisibility(View.GONE);
                    txtSeeComment.setText("Chưa có bình luận nào");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveReviews(){
        userReviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reviews.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Review review = snapshot.getValue(Review.class);
                    reviews.add(review);
                }

                for(int i=0; i<reviews.size(); i++){
                    int s = Integer.parseInt(reviews.get(i).getSpinner());
                    int p = Integer.parseInt(reviews.get(i).getPosition());

                    if(s == spinner && p == position && reviews.get(i).getUidUser().equals(user.getUid())){
                        ratingBar.setRating(Float.parseFloat(reviews.get(i).getReviewUser()));
                        return;
                    }
                }

                ratingBar.setRating(0);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
