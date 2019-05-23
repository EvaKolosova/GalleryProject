package com.example.picturesshowing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

public class RecyclerViewStaggeredGridActivity extends AppCompatActivity {
    private RecyclerViewStaggeredGridAdapter staggeredAdapter;
    private RecyclerView favPlaces;
    private ArrayList<String> images;// list of files paths
    private RecyclerViewStaggeredGridAdapter.OnItemClickListener onItemClickListener = (View view, int position, String name) -> {
        //fullImageSize
        Intent intent = new Intent(RecyclerViewStaggeredGridActivity.this, FullActivity.class);
        intent.setAction(android.content.Intent.ACTION_SEND);
        String path;
        path = images.get(position);
        intent.putExtra("imageUri", path);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_staggered_grid_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        images = getImages();

        favPlaces = findViewById(R.id.rvStaggeredGridRecycler);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        favPlaces.setLayoutManager(layoutManager);
        favPlaces.setHasFixedSize(true);

        staggeredAdapter = new RecyclerViewStaggeredGridAdapter(this, images, onItemClickListener);
        favPlaces.setAdapter(staggeredAdapter);
    }

    private ArrayList<String> getImages(){
        ArrayList<String> imagesPath = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String image1 = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                imagesPath.add(image1);
            }
            cursor.close();
        }
        return imagesPath;
    }
}
