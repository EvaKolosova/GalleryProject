package com.example.picturesshowing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class RecyclerViewLinearActivity extends AppCompatActivity implements RecyclerViewLinearAdapter.ItemClickListener  {

    RecyclerViewLinearAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_linear_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // data for showing pictures to user
        //<String> data = new ArrayList<>();

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvPictures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RecyclerViewLinearAdapter(this, data);
        //adapter.setClickListener(this);
        //recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecyclerViewLinearActivity.this, MainActivity.class));
    }

    @Override
    public void onItemClick(View view, int position) {
       // fullImageSize
    }


}
