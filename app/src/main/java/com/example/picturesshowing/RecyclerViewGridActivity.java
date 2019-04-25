package com.example.picturesshowing;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewGridActivity extends AppCompatActivity {
    RecyclerViewGridAdapter adapter;
    RecyclerView imagegrid;
    //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
    ArrayList<String> f = new ArrayList<>();// list of file paths
    Uri[] uri;
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getFromSdcard();
        imagegrid = findViewById(R.id.rvGridRecycler);
        imagegrid.setLayoutManager(new GridLayoutManager(RecyclerViewGridActivity.this, 2));
        adapter = new RecyclerViewGridAdapter(RecyclerViewGridActivity.this, uri, f);
        imagegrid.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecyclerViewGridActivity.this, MainActivity.class));
    }
    public void getFromSdcard() {
        File file= new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }

    class ViewHolder {
        ImageView imageview;
    }
}
