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
    private RecyclerViewGridAdapter adapter;
    private RecyclerView imagegrid;
    private ArrayList<String> f = new ArrayList<>();// list of files paths
    private RecyclerViewGridAdapter.OnItemClickListener onItemClickListener = (View view, int position, String name) -> {
        //fullImageSize
        Intent intent = new Intent(RecyclerViewGridActivity.this, FullActivity.class);
        intent.setAction(android.content.Intent.ACTION_SEND);
        String path;
        path = f.get(position);
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
                f.add(image);
            }
            cursor.close();
        }

        imagegrid = findViewById(R.id.rvGridRecycler);
        imagegrid.setLayoutManager(new GridLayoutManager(RecyclerViewGridActivity.this, 2));
        adapter = new RecyclerViewGridAdapter(RecyclerViewGridActivity.this, f, onItemClickListener);
        imagegrid.setAdapter(adapter);
    }

    /*public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }*/
}
