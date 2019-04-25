package com.example.picturesshowing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

public class DataFromGallery {
    final private int SELECT_PICTURE = 123;

    //функция выгрузки фото из галереи в ImageViews
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





    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

//                super.onActivityResult(requestCode, resultCode, data);
//
//                if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
//                    Uri imageUri = data.getData();
//                    imgView.setImageURI(imageUri);
//
//
//                filePath = data.getData();
//                if (null != filePath) {
//                    try {
//
//                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
//                        img.setImageBitmap(bitmap);
//
//                        if (filePath.getScheme().equals("content")) {
//                            try (Cursor cursor = getContentResolver().query(filePath, null, null, null, null)) {
//                                if (cursor != null && cursor.moveToFirst()) {
//                                    file_name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                    Toast.makeText(this, file_name, Toast.LENGTH_SHORT).show();
//                                    img_name.setText(file_name);
//                                }
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }
    }
}
