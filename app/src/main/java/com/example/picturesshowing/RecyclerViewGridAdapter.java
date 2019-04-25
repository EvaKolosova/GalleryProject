package com.example.picturesshowing;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewGridAdapter extends RecyclerView.Adapter<RecyclerViewGridAdapter.ViewHolder> {

    protected Context mContext;
    private ArrayList<String> f;
    private Uri[] imageUri;
    private LayoutInflater mInflater;
    //final RecyclerViewGridActivity.ViewHolder holder = new RecyclerViewGridActivity().ViewHolder();

    // data is passed into the constructor
    RecyclerViewGridAdapter(Context context, Uri[] image, ArrayList<String> f) {
        this.mInflater = LayoutInflater.from(context);
        this.imageUri = image;
        mContext = context;
        this.f = f;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_item_view, parent, false);
        return new ViewHolder(view);
    }

    // parent activity will implement this method to respond to click events
    public interface OnItemClickListener {
        void onItemClick(View view, int position, String name);
    }

    // binds the data to the ImageView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.myImageView.setImageURI(imageUri[position]);
//        holder.myImageView2.setImageURI(imageUri[position]);

        String itemData = f.get(position);
        Glide
                .with(mContext)
                .load(itemData)
                .into(holder.imageview);

//        holder.imageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // OnItemClickListener.super.onItemClick(v, position, "myImageView");
//                Log.d("position of image1 is ", "position " + position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return f.size();
    }

    public long getItemId(int position) { return position;}

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{ //implements ItemClickListener {
        ImageView imageview;

        ViewHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageOne);
        }
    }
}
