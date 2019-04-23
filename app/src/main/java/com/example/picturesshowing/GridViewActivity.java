package com.example.picturesshowing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    private ImageAdapter imageAdapter;
    GridView imagegrid;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getFromSdcard();
        imagegrid = findViewById(R.id.gridViewImages);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);
        imagegrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridViewActivity.this, FullActivity.class);
                intent.setAction(android.content.Intent.ACTION_SEND);
                String path;
                path = f.get(position);
                intent.putExtra("imageUri", path);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(GridViewActivity.this, MainActivity.class));
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

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return f.size();
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
                holder.imageview = convertView.findViewById(R.id.imageOne);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
            holder.imageview.setImageBitmap(myBitmap);
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
    }
}
