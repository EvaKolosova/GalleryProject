package com.example.picturesshowing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewLinearActivity extends AppCompatActivity { //implements RecyclerViewLinearAdapter.OnItemClickListener  {
    File[] listFile;
    private RecyclerViewLinearAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<ListStructure> f = new ArrayList<>();// list of files paths
    private RecyclerViewLinearAdapter.OnItemClickListener onItemClickListener = (View view, int position, String name) -> {
        //fullImageSize
        Intent intent = new Intent(RecyclerViewLinearActivity.this, FullActivity.class);
        intent.setAction(android.content.Intent.ACTION_SEND);
        String path;
        if (name.equals("myImageView2"))
            path = f.get(position).image2;
        else
            path = f.get(position).image1;
        intent.putExtra("imageUri", path);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_linear_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getFromSdcard();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ListStructure d = new ListStructure();
                String image1 = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                d.image1 = image1;
                cursor.moveToNext();
                String image2 = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                d.image2 = image2;
                f.add(d);
            }
            cursor.close();
        }
        recyclerView = findViewById(R.id.rvLinearRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewLinearAdapter(RecyclerViewLinearActivity.this, f, onItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                ListStructure d = new ListStructure();
                d.image1 = listFile[i].getAbsolutePath();
                d.image2 = listFile[++i].getAbsolutePath();
                f.add(d);
            }
        }
    }
}
