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
    //private Bitmap thumbnails;
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
                //full size image on another activity
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                Intent intent = new Intent(GridViewActivity.this, FullActivity.class);
                intent.putExtra("byteArray", bs.toByteArray());
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

//            holder.imageview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //zoomImage(imagegrid, holder.imageview.getImageAlpha());
//                    //holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                }
//            });

            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
            holder.imageview.setImageBitmap(myBitmap);
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
    }

    private void zoomImage(final View view, int image) {
        // Загружаем изображение с большим разрешением в ImageView
        view.setAlpha(image);

        // Рассчитываем начальные и конечные границы изображения.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // Начальные границы берем от прямоугольника компонента миниатюры,
        // а конечные от прямоугольника компонента полноразмерной картинки.
        // Также установим смещение границ для полноразмерной картинки в точку начала анимации.
        view.getGlobalVisibleRect(startBounds);
        findViewById(R.id.imageOne)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Настроим начальные границы, чтобы у них было такое же соотношение как у конечных.
        // Это предотвратит нежелательное растягивание во время анимации.
        // также рассчитаем начальный коэффициент масштабирования (конечный коэффициент всегда равен 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Расширяем границы старта горизонтально
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Расширяем границы старта вертикально
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Прячем миниатюры и показываем полную картинку.

        view.setVisibility(View.VISIBLE);

        // Устанавливаем опорные точки для SCALE_X и SCALE_Y
        // в левый верхний угол большой картинки (по умолчанию они в центре).
        view.setScaleX(0f);
        view.setScaleY(0f);
    }
}
