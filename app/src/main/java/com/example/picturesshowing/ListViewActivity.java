package com.example.picturesshowing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private ListViewActivity.ImageAdapter imageAdapter;
    ListView imagelist;
    ArrayList<D> f = new ArrayList<>();// list of file paths
    File[] listFile;

    class D {
        String str1;
        String str2;
    }

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
        imagelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                Intent intent = new Intent(ListViewActivity.this, FullActivity.class);
                intent.putExtra("byteArray", bs.toByteArray());
                startActivity(intent);
            }
        });
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
                D d = new D();
                d.str1 = listFile[i].getAbsolutePath();
                d.str2 = listFile[++i].getAbsolutePath();
                f.add(d);
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
            final ListViewActivity.ViewHolder holder2;

            if (convertView == null) {
                holder1 = new ListViewActivity.ViewHolder();
                convertView = mInflater.inflate(R.layout.linear_row_view, null);
                holder1.imageview = convertView.findViewById(R.id.imageFirst);
                convertView.setTag(holder1);

                holder2 = new ListViewActivity.ViewHolder();
                holder2.imageview = convertView.findViewById(R.id.imageSecond);
                convertView.setTag(holder2);
            }
            else {
                holder1 = (ListViewActivity.ViewHolder) convertView.getTag();
                holder2 = (ListViewActivity.ViewHolder) convertView.getTag();
            }

//            holder.imageview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //zoomImage(imagegrid, holder.imageview.getImageAlpha());
//                    //holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                }
//            });

            Bitmap myBitmap1 = BitmapFactory.decodeFile(f.get(position).str1);
            holder1.imageview.setImageBitmap(myBitmap1);
            Bitmap myBitmap2 = BitmapFactory.decodeFile(f.get(position).str2);
            holder2.imageview.setImageBitmap(myBitmap2);
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
    }
}
