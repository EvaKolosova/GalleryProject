package com.example.picturesshowing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewGridActivity extends AppCompatActivity {
    RecyclerViewGridAdapter adapter;
    RecyclerView imagegrid;
    ArrayList<String> f = new ArrayList<>();// list of files paths
    RecyclerViewGridAdapter.OnItemClickListener onItemClickListener = new RecyclerViewGridAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position, String name) {
            //fullImageSize
            Intent intent = new Intent(RecyclerViewGridActivity.this, FullActivity.class);
            intent.setAction(android.content.Intent.ACTION_SEND);
            String path;
            path = f.get(position);
            intent.putExtra("imageUri", path);
            startActivity(intent);
        }
    };
    Uri[] uri;
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getFromSdcard();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                String image = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                f.add(image);
            }
            cursor.close();
        }

        imagegrid = findViewById(R.id.rvGridRecycler);
        imagegrid.setLayoutManager(new GridLayoutManager(RecyclerViewGridActivity.this, 2));
        adapter = new RecyclerViewGridAdapter(RecyclerViewGridActivity.this, uri, f, onItemClickListener);
        imagegrid.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecyclerViewGridActivity.this, MainActivity.class));
    }

    public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }
}
