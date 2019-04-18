package com.example.picturesshowing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecyclerViewGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //RecyclerViewGridActivity.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecyclerViewGridActivity.this, MainActivity.class));
    }

}
