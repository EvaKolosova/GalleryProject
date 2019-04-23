package com.example.picturesshowing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FullActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        imageView = findViewById(R.id.imageView);

//        if(getIntent().hasExtra("byteArrayGrid")) {
//            Bitmap b = BitmapFactory.decodeByteArray(
//                    getIntent().getByteArrayExtra("byteArrayGrid"),0,getIntent().getByteArrayExtra("byteArrayGrid").length);
//            imageView.setImageBitmap(b);
//        }

        if(getIntent().hasExtra("byteArray")) {
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imageView.setImageBitmap(b);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
