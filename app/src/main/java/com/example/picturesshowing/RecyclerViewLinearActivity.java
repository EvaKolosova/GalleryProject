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
    private ArrayList<ListStructure> imagesPaths = new ArrayList<>();
    private File[] listOfFiles;
    private RecyclerViewLinearAdapter adapter;
    private RecyclerView recyclerView;

    private RecyclerViewLinearAdapter.OnItemClickListener onItemClickListener = (View view, int position, String name) -> {
        //fullImageSize
        Intent intent = new Intent(RecyclerViewLinearActivity.this, FullActivity.class);
        intent.setAction(android.content.Intent.ACTION_SEND);
        String path;
        if (name.equals("ivSecond"))
            path = imagesPaths.get(position).imageTwo;
        else
            path = imagesPaths.get(position).imageOne;
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
                d.imageOne = image1;
                if (cursor.moveToNext()) {
                    String image2 = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    d.imageTwo = image2;
                }
                imagesPaths.add(d);
            }
            cursor.close();
        }
        recyclerView = findViewById(R.id.rvLinearRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewLinearAdapter(RecyclerViewLinearActivity.this, imagesPaths, onItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    /*public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                ListStructure d = new ListStructure();
                d.imageOne = listOfFiles[i].getAbsolutePath();
                d.imageTwo = listOfFiles[++i].getAbsolutePath();
                f.add(d);
            }
        }
    }*/
}
