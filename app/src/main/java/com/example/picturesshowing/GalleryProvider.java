package com.example.picturesshowing;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class GalleryProvider extends ContentProvider {
    final String LOG_TAG = "myProvidersLogs";
    final private int SELECT_PICTURE = 123;
    Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        projection = new String[]{
            MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(images, projection, null, null, null);
        int count = 0;
        Log.i(LOG_TAG, " query count=" + cursor.getCount());

        if (cursor.moveToFirst()) {
            String bucket;
            String date;
            int bucketColumn = cursor.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cursor.getColumnIndex(
                    MediaStore.Images.Media.DATE_TAKEN);

            do {
                // Get the field values
                bucket = cursor.getString(bucketColumn);
                date = cursor.getString(dateColumn);

                // Do something with the values.
                Log.i(LOG_TAG, " bucket=" + bucket
                        + "  date_taken=" + date);
            } while (cursor.moveToNext());
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}