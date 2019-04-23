package com.example.picturesshowing;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RecyclerViewLinearActivity extends AppCompatActivity implements RecyclerViewLinearAdapter.ItemClickListener  {

    RecyclerViewLinearAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    ArrayList<Uri> uri = new ArrayList<Uri>();
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_linear_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // data for showing pictures to user
        List<String> data = new ArrayList<>();

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvPictures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RecyclerViewLinearAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecyclerViewLinearActivity.this, MainActivity.class));
    }

    @Override
    public void onItemClick(View view, int position) {
       // fullImageSize
    }

    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public void getFromSdcard()
    {
        File file= new File(android.os.Environment.getExternalStorageDirectory(), "Download");

        if (file.isDirectory())
        {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++)
            {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }

    public static MediaStore.Images[] getImagesNames(Context context) {
        MediaStore.Images[] files = new MediaStore.Images[]{};
        Cursor cursor = null;
        try {
            String[] projection = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
            int count = 0;
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                do {
                    // ?!?!?!?!
                    //files[count] = cursor.get(nameIndex);
                }
                while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return files;
    }
}
