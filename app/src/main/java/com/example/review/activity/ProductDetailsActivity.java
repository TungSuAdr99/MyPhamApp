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
import com.example.review.MainActivity;
import com.example.review.model.Map;
import com.example.review.model.Product;
import com.example.review.model.RelatedProduct;
import com.example.review.adapter.RelatedProductAdapter;

import com.example.review.ProductDetailsActivity.TreeView.MyHolder;
import com.example.review.ProductDetailsActivity.TreeView.model.TreeNode;
import com.example.review.ProductDetailsActivity.TreeView.view.AndroidTreeView;
import com.example.review.R;
import com.example.review.model.Comment;
import com.example.review.model.Like;
import com.example.review.model.Review;
import com.example.review.model.Shop;
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
    private int spinner;
    private DatabaseReference userCommentRef;
    private RatingBar ratingBar;
    private TextView txtReview;
    private DatabaseReference userReviewsRef;
    private TextView txtInformationShop;

    private CircleImageView imgUserComment;
    private TextView txtUserComment;
    private TextView txtUserName;
    private TextView tv_location;

    ArrayList<RelatedProduct> arrayList;
    RelatedProductAdapter customAdapter;
    private ListView lvRelatedProduct;
    private TextView txtSeeComment;
    private TextView txtPromotional;

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
    private int positionStar = -1;
    private String imageRelatedOne;
    private String nameRelatedOne;
    private String imageRelatedTwo;
    private String nameRelatedTwo;
    private String imageRelatedThree;
    private String nameRelatedThree;
    private String keyShop;
    private String informationShop;

    //key product
    private String keyRelatedOne;
    private String keyRelatedTwo;
    private String keyRelatedThree;

    private String promotional; //khuyến mãi
    private String keyProduct = "";

    private int sizeLike = 0;
    private int sizeLikeRelatedOne = 0;
    private int sizeLikeRelatedTwo = 0;
    private int sizeLikeRelatedThree = 0;

    private FirebaseUser user;
    private DatabaseReference userLikeRef;
    private DatabaseReference cosmeticRef;
    private DatabaseReference mapsRef;

    private ArrayList<Like> likes = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();
    private ArrayList<Product> arrayProducts = new ArrayList<>();

    private Map map;
    private double coordinateX;
    private double coordinateY;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        setDatabaseReference();

        initView();
        takeIntent();

        TreeView();

        XLTextViewThich();
        switchToAtvtCmt();
        user = FirebaseAuth.getInstance().getCurrentUser();

        txtName.setText("Tên: " + name);
        txtPrice.setText("Giá: " + price);
        txtIngredient.setText("Thành phần: " + ingredient);
        if(!image.equals(""))
            Glide.with(this).load(image).into(imageView);
        txtPromotional.setText("Khuyến mãi: " + promotional);


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
                if(user == null){
                    Toast.makeText(ProductDetailsActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }else {
                    Review review = new Review(user.getUid(), ratingBar.getRating()+"",
                            spinner+"", keyProduct);

                    if(positionStar >= 0)
                        userReviewsRef.child(reviews.get(positionStar).getKey()).removeValue();

                    userReviewsRef.push().setValue(review);

                    Toast.makeText(ProductDetailsActivity.this, "cảm ơn b đã đánh giá sản phẩm này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchScreenLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();

        retrieveShops();
        retrieveCosmetics();
        retrieveUserLikes();
        retrieveComment();
        retrieveReviews();
        retrieveMaps();
    }

    private void setDatabaseReference(){
        userLikeRef = FirebaseDatabase.getInstance().getReference().child("userLikes");
        userCommentRef = FirebaseDatabase.getInstance().getReference().child("UserComments");
        userReviewsRef = FirebaseDatabase.getInstance().getReference().child("UserReviews");
        cosmeticRef = FirebaseDatabase.getInstance().getReference().child("MyPham");
        mapsRef = FirebaseDatabase.getInstance().getReference().child("Maps");
    }

    private void switchScreenLocation()
    {
        tv_location = findViewById(R.id.tv_location);
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, MapsActivity.class);
                intent.putExtra("coordinateX", coordinateX);
                intent.putExtra("coordinateY", coordinateY);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        lvRelatedProduct = findViewById(R.id.lv_related_product);
        arrayList = new ArrayList<>();
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
        txtPromotional = findViewById(R.id.txt_promotional);
        txtInformationShop = findViewById(R.id.txt_infomation_shop);
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
                    intent.putExtra("keyProduct", keyProduct);
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
                    intent.putExtra("keyProduct", keyProduct);
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
        promotional = intent.getStringExtra("promotional");
        keyProduct = intent.getStringExtra("keyProduct");
        keyShop = intent.getStringExtra("keyShop");

        imageRelatedOne = intent.getStringExtra("imageRelatedOne");
        imageRelatedTwo = intent.getStringExtra("imageRelatedTwo");
        imageRelatedThree = intent.getStringExtra("imageRelatedThree");

        nameRelatedOne = intent.getStringExtra("nameRelatedOne");
        nameRelatedTwo = intent.getStringExtra("nameRelatedTwo");
        nameRelatedThree = intent.getStringExtra("nameRelatedThree");

        keyRelatedOne = intent.getStringExtra("keyRelatedOne");
        keyRelatedTwo = intent.getStringExtra("keyRelatedTwo");
        keyRelatedThree = intent.getStringExtra("keyRelatedThree");
    }

    public void XLTextViewThich() {
        linearLayoutLike = findViewById(R.id.ll_like);
        imgLove = findViewById(R.id.img_love);

        linearLayoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    if(isLove){
                        isLove = false;
                        imgLove.setImageResource(R.drawable.tym2);
                        Like like = new Like(spinner+"", keyProduct, user.getUid());
                        userLikeRef.push().setValue(like);
                    }else {

                        isLove = true;

                        for(int i=0; i<likes.size(); i++){

                            int s = Integer.parseInt(likes.get(i).getSpinner());
                            String likeProductKey = likes.get(i).getKeyProduct();

                            if(s == spinner && likeProductKey.equals(keyProduct) && likes.get(i).getUidUser().equals(user.getUid())){
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

    public void TreeView() {
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
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Like like = snapshot.getValue(Like.class);
                    like.setKey(snapshot.getKey());

                    likes.add(like);
                }

                for(int i=0; i<likes.size(); i++){

                    int s = Integer.parseInt(likes.get(i).getSpinner());
                    String likeProductKey = likes.get(i).getKeyProduct();

                    if(s == spinner && likeProductKey.equals(keyProduct) && likes.get(i).getUidUser().equals(user.getUid())){
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
                    String likeProductKey = likes.get(i).getKeyProduct();

                    if(s == spinner && likeProductKey.equals(keyProduct)){
                        sizeLike++;
                    }
                }

                txtSizeLike.setText(sizeLike + " lượt thích");

                //relate product
                arrayList.clear();
                sizeLikeRelatedOne = 0;
                sizeLikeRelatedTwo = 0;
                sizeLikeRelatedThree = 0;
                for(int i=0; i<likes.size(); i++){

                    int s = Integer.parseInt(likes.get(i).getSpinner());
                    String likeProductKey = likes.get(i).getKeyProduct();

                    if(s == spinner && likeProductKey.equals(keyRelatedOne)){
                        sizeLikeRelatedOne++;
                    }else if(s == spinner && likeProductKey.equals(keyRelatedTwo)){
                        sizeLikeRelatedTwo++;
                    }else if(s == spinner && likeProductKey.equals(keyRelatedThree)){
                        sizeLikeRelatedThree++;
                    }
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter(){
        arrayList.add(new RelatedProduct(imageRelatedOne,nameRelatedOne,sizeLikeRelatedOne+" người thích"));
        arrayList.add(new RelatedProduct(imageRelatedTwo,nameRelatedTwo,sizeLikeRelatedTwo+" người thích"));
        arrayList.add(new RelatedProduct(imageRelatedThree,nameRelatedThree,sizeLikeRelatedThree+" người thích"));

        customAdapter = new RelatedProductAdapter(ProductDetailsActivity.this, R.layout.item_shop, arrayList);
        lvRelatedProduct.setAdapter(customAdapter);
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

                //on take comments by spinner and keyProduct
                arrComments.clear();
                for(int i=0; i<comments.size(); i++){

                    int s = Integer.parseInt(comments.get(i).getSpinner());
                    String commentProductKey = comments.get(i).getKeyProduct();

                    if(s == spinner && commentProductKey.equals(keyProduct)){
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
                    if(!arrComments.get(positionRandom).getImageUser().equals("") &&
                            arrComments.get(positionRandom).getImageUser() != null){
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
                    review.setKey(snapshot.getKey());
                    reviews.add(review);
                }

                for(int i=0; i<reviews.size(); i++){
                    int s = Integer.parseInt(reviews.get(i).getSpinner());
                    String reviewProductKey = reviews.get(i).getKeyProduct();

                    if(s == spinner && reviewProductKey.equals(keyProduct) && reviews.get(i).getUidUser().equals(user.getUid())){
                        ratingBar.setRating(Float.parseFloat(reviews.get(i).getReviewUser()));
                        positionStar = i;
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

    private void retrieveCosmetics(){
        cosmeticRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayProducts.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    product.setKey(snapshot.getKey());
                    arrayProducts.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveMaps(){
        mapsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    map = snapshot.getValue(Map.class);
                    map.setKey(snapshot.getKey());

                    if(map.getKeyShop().equals(keyShop)){
                        coordinateX = Double.parseDouble(map.getCoordinateX());
                        coordinateY = Double.parseDouble(map.getCoordinateY());
                        location = map.getLocation();

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveShops(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference().child("Shop");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Shop shop = snapshot.getValue(Shop.class);
                    shop.setKey(snapshot.getKey());

                    if(shop.getKey().equals(keyShop)){
                        informationShop = shop.getInformation();
                        txtInformationShop.setText("Shop: " + informationShop);
                        return;
                    }
                }

                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
