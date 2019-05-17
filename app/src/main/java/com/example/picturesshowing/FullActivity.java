package com.example.picturesshowing;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class FullActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        imageView = findViewById(R.id.imageView);
        if (getIntent().hasExtra("imageUri")) {
            String uriString = getIntent().getStringExtra("imageUri");
            Uri uri = Uri.parse(uriString);
            imageView.setImageURI(uri);
        }
    }
}
