package com.example.picturesshowing;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class RecyclerViewLinearAdapter extends RecyclerView.Adapter<RecyclerViewLinearAdapter.ViewHolder> {

    protected Context mContext;
    private Uri[] imageID;
    private LayoutInflater mInflater;//раздуватель
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewLinearAdapter(Context context, Uri[] imageid) {
        this.mInflater = LayoutInflater.from(context);
        this.imageID = imageid;
        mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.linear_row_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the ImageView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri uri = imageID[position];
        holder.myImageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return imageID.length;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.rvPictures);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Object getItem(int id) {
        return imageID[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
