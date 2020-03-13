package com.example.review;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Manhinhtimkiem extends AppCompatActivity {
    private EditText edt_timkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhtimkiem);

        edt_timkiem = findViewById(R.id.edt_timkiem);
    }
}
