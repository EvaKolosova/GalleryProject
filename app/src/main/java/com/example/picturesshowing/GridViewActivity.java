package com.example.picturesshowing;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    private ArrayList<String> imagesPaths = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private File[] listOfFiles;
    private GridView imagegrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getFromSdcard();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String image = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Log.d("kolosova_checkInfo", "imageInfo: " + image);
                imagesPaths.add(image);
            }
            cursor.close();
        }

        imagegrid = findViewById(R.id.gridViewImages);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);
        imagegrid.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(GridViewActivity.this, FullActivity.class);
            intent.setAction(android.content.Intent.ACTION_SEND);
            String path;
            path = imagesPaths.get(position);
            intent.putExtra("imageUri", path);
            startActivity(intent);
        });
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
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.grid_item_view, null);
                holder.imageView = convertView.findViewById(R.id.imageOne);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Bitmap myBitmap = BitmapFactory.decodeFile(imagesPaths.get(position));
            holder.imageView.setImageBitmap(myBitmap);
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageView;
    }
}