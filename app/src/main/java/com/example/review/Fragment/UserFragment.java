package com.example.review.Fragment;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.review.MainActivity;
import com.example.review.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {

    private List<AuthUI.IdpConfig> providers;
    private static final int AUTH_REQUEST_CODE = 1000;

    private String storagePermission[];
    private static final int IMAGE_PICK_GALLERY_CODE = 1001;
    private static final int STORAGE_REQUEST_CODE = 1002;

    private Button btnDangXuat;
    private TextView txtNameUser;
    private FirebaseUser user;

    private boolean check;

    private CircleImageView imgProfile;
    private Uri imageUri;

    private DatabaseReference userRef;
    private StorageReference userProfileImgRef;
    private String downloadUrl = "";
    private String nameUser = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDangXuat = view.findViewById(R.id.btn_dang_xuat);
        txtNameUser = view.findViewById(R.id.txt_user);
        imgProfile = view.findViewById(R.id.img_profile);

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        userProfileImgRef = FirebaseStorage.getInstance().getReference().child("ProfileImages");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        eventsClick();
    }

    private void eventsClick() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showSignInOptions();
                            }
                        });
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkStoragePermission()){
                    requestStoragePermission();
                }else {
                    pickGallery();
                }
            }
        });
    }

    private void pickGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null && !check){
            check = true;
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        }
        if(user == null){
            showSignInOptions();
            Log.e("KMF","kkk");
            check = false;
        }

        if(user != null){

            retrieveUser();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUTH_REQUEST_CODE){
            if(user.getEmail() != null)
                nameUser = user.getDisplayName();

            if(user.getPhoneNumber() != null){
                nameUser = user.getPhoneNumber();
                Log.e("KMFQ", nameUser + "");
            }
        }

        if(requestCode == IMAGE_PICK_GALLERY_CODE){
            CropImage.activity(data.getData())
                    .setMinCropResultSize(2300, 2300)
                    .setMaxCropResultSize(2300,2300)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(getContext(), this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            imgProfile.setImageURI(imageUri);
        }

        saveUserData();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_REQUEST_CODE){
            boolean writeStorageAccepted = grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED;

            if(writeStorageAccepted){
                pickGallery();
            }
        }
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyThem)
                        .setAlwaysShowSignInMethodScreen(true)
                        .build(), AUTH_REQUEST_CODE
        );
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), storagePermission, STORAGE_REQUEST_CODE);
    }

    private void saveUserData(){

        if(imageUri == null) {
            final HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            profileMap.put("name", nameUser);

            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                String imageDb = dataSnapshot.child("image").getValue().toString();
                                if(imageDb == null)
                                    profileMap.put("image", downloadUrl);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(profileMap);
        }else {
            final StorageReference filePath = userProfileImgRef
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            filePath.putFile(imageUri)
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

                                HashMap<String, Object> profileMap = new HashMap<>();
                                profileMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                profileMap.put("name", nameUser);
                                profileMap.put("image", downloadUrl);

                                userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(profileMap);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("KMFW", ":((((( " + e.getMessage());
                }
            });
        }
    }

    private void retrieveUser(){
        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            String imageDb = dataSnapshot.child("image").getValue().toString();
                            String nameDb = dataSnapshot.child("name").getValue().toString();

                            nameUser = nameDb;
                            txtNameUser.setText(nameDb);
                            if(!imageDb.equals("")){
                                Picasso.get().load(imageDb).placeholder(R.drawable.ic_profile).into(imgProfile);
                            }else {
                                imgProfile.setImageResource(R.drawable.ic_profile);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
