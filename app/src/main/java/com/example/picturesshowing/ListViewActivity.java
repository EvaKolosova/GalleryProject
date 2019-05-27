package com.example.picturesshowing;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private ArrayList<ListStructure> imagesPaths = new ArrayList<>();
    private File[] listOfFiles;
    private ListViewActivity.ImageAdapter imageAdapter;
    private ListView listOfImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

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

        listOfImages = findViewById(R.id.listViewImages);
        imageAdapter = new ListViewActivity.ImageAdapter();
        listOfImages.setAdapter(imageAdapter);
    }

    /*public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory()) {
            listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                ListStructure listStructure = new ListStructure();
                listStructure.imageOne = listOfFiles[i].getAbsolutePath();
                listStructure.imageTwo = listOfFiles[++i].getAbsolutePath();
                imagesPaths.add(listStructure);
            }
        }
    }*/

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return imagesPaths.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ListViewActivity.ViewHolder holder1;

            if (convertView == null) {
                holder1 = new ListViewActivity.ViewHolder();
                convertView = mInflater.inflate(R.layout.linear_row_view, null);
                holder1.ivFirst = convertView.findViewById(R.id.imageFirst);
                holder1.ivSecond = convertView.findViewById(R.id.imageSecond);
                convertView.setTag(holder1);
            } else {
                holder1 = (ListViewActivity.ViewHolder) convertView.getTag();
            }

            holder1.ivFirst.setOnClickListener((View v) -> {
                holder1.onItemClick(v, position, "ivFirst");
                Log.d("imageOne position", "position is: " + position);
            });

            holder1.ivSecond.setOnClickListener((View v) -> {
                holder1.onItemClick(v, position, "ivSecond");
                Log.d("imageTwo position", "position is: " + position);
            });

            ListStructure itemData = imagesPaths.get(position);
            Log.d("images", "pathOne " + itemData.imageOne + ", pathTwo " + itemData.imageTwo);
            Glide
                    .with(ListViewActivity.this)
                    .load(itemData.imageOne)
                    .into(holder1.ivFirst);
            Glide
                    .with(ListViewActivity.this)
                    .load(itemData.imageTwo)
                    .into(holder1.ivSecond);

            return convertView;
        }
    }

    class ViewHolder {
        ImageView ivFirst;
        ImageView ivSecond;

        void onItemClick(View view, int position, String name) {
            Intent intent = new Intent(ListViewActivity.this, FullActivity.class);
            intent.setAction(android.content.Intent.ACTION_SEND);
            String path;
            if (name.equals("ivSecond"))
                path = imagesPaths.get(position).imageTwo;
            else
                path = imagesPaths.get(position).imageOne;
            intent.putExtra("imageUri", path);
            startActivity(intent);
        }
    }
}
