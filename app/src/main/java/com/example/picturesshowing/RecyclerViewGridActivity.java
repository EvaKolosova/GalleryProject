package com.example.picturesshowing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class RecyclerViewGridActivity extends AppCompatActivity {
    private ArrayList<String> imagesPaths = new ArrayList<>();
    private RecyclerViewGridAdapter adapter;
    private RecyclerView imageGrid;

    private RecyclerViewGridAdapter.OnItemClickListener onItemClickListener = (View view, int position, String name) -> {
        //fullImageSize
        Intent intent = new Intent(RecyclerViewGridActivity.this, FullActivity.class);
        intent.setAction(android.content.Intent.ACTION_SEND);
        String path;
        path = imagesPaths.get(position);
        intent.putExtra("imageUri", path);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getFromSdcard();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String image = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imagesPaths.add(image);
            }
            cursor.close();
        }

        imageGrid = findViewById(R.id.rvGridRecycler);
        imageGrid.setLayoutManager(new GridLayoutManager(RecyclerViewGridActivity.this, 2));
        adapter = new RecyclerViewGridAdapter(RecyclerViewGridActivity.this, imagesPaths, onItemClickListener);
        imageGrid.setAdapter(adapter);
    }

    /*public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                imagesPaths.add(listOfFiles[i].getAbsolutePath());
            }
        }
    }*/
}
