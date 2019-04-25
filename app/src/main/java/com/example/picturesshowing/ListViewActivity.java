package com.example.picturesshowing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import com.bumptech.glide.Glide;

public class ListViewActivity extends AppCompatActivity {
    private ListViewActivity.ImageAdapter imageAdapter;
    ListView imagelist;
    ArrayList<ListStructure> f = new ArrayList<>();// list of file paths
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //---*---*---*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getFromSdcard();
        imagelist = findViewById(R.id.listViewImages);
        imageAdapter = new ListViewActivity.ImageAdapter();
        imagelist.setAdapter(imageAdapter);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListViewActivity.this, MainActivity.class));
    }

    public void getFromSdcard()
    {
        File file= new File(android.os.Environment.getExternalStorageDirectory(), "Download");
        if (file.isDirectory())
        {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++)
            {
                ListStructure listStructure = new ListStructure();
                listStructure.image1 = listFile[i].getAbsolutePath();
                listStructure.image2 = listFile[++i].getAbsolutePath();
                f.add(listStructure);
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
            final ListViewActivity.ViewHolder holder1;

            if (convertView == null) {
                holder1 = new ListViewActivity.ViewHolder();
                convertView = mInflater.inflate(R.layout.linear_row_view, null);
                holder1.imageview = convertView.findViewById(R.id.imageFirst);
                holder1.imageview2 = convertView.findViewById(R.id.imageSecond);
                convertView.setTag(holder1);
            }
            else {
                holder1 = (ListViewActivity.ViewHolder) convertView.getTag();
            }

            holder1.imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder1.onItemClick(v, position, "imageview");
                    Log.d("position of image1 is ", "position " + position);
                }
            });

            holder1.imageview2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder1.onItemClick(v, position, "imageview2");
                    Log.d("position of image2 is ", "position " + position);
                }
            });

            ListStructure itemData = f.get(position);
            Log.d("images", "str1 " + itemData.image1 + " str2 " + itemData.image2);
            Glide
                    .with(ListViewActivity.this)
                    .load(itemData.image1)
                    .into(holder1.imageview);
            Glide
                    .with(ListViewActivity.this)
                    .load(itemData.image2)
                    .into(holder1.imageview2);

            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
        ImageView imageview2;

        void onItemClick(View view, int position, String name) {
            Intent intent = new Intent(ListViewActivity.this, FullActivity.class);
            intent.setAction(android.content.Intent.ACTION_SEND);
            String path;
            if (name.equals("imageview2"))
                path = f.get(position).image2;
            else
                path = f.get(position).image1;
            intent.putExtra("imageUri", path);
            startActivity(intent);
        }
    }
}
