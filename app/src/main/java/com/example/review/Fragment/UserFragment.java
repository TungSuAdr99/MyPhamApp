package com.example.review.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.review.MainActivity;
import com.example.review.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class UserFragment extends Fragment {

    private List<AuthUI.IdpConfig> providers;
    private static final int AUTH_REQUEST_CODE = 1000;

    private Button btnDangXuat;
    private TextView txtNameUser;
    private FirebaseUser user;

    private boolean check;

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUTH_REQUEST_CODE){
            if(user.getEmail() != null)
                txtNameUser.setText(user.getDisplayName());

            if(user.getPhoneNumber() != null){
                txtNameUser.setText(user.getPhoneNumber());
            }

            if(user.getEmail() == "TungSuAdr99@gmail.com"){

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
}
