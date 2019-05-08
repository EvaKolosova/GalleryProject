package com.example.picturesshowing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.test1.SuperToggle;
import com.example.test1.SuperToggleRect;

public class MainActivity extends AppCompatActivity {
    protected Button recyclerViewButton;
    protected Button gridViewButton;
    protected Button listViewButton;
    protected SuperToggleRect toggleButton;
    protected TextView APKtext;
    private static final int REQUEST_ACCESS = 110;
    final private int REQUEST_CODE_FOR_PERMISSIONS = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///*--**--**--*
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        recyclerViewButton = findViewById(R.id.recyclerViewButton);
        gridViewButton = findViewById(R.id.gridViewButton);
        listViewButton = findViewById(R.id.listViewButton);
        toggleButton = findViewById(R.id.toggleButton);
        APKtext = findViewById(R.id.APKversion);
        APKtext.setText(BuildConfig.BUILD_TYPE);

        //SuperToggleRect toggleButton = new SuperToggleRect(this, "#526AFF", "#06B5CF");

        int hasReadPicturesPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadPicturesPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_FOR_PERMISSIONS);
            return;
        }

    }

    public void recyclerViewClick(View v){
        if(toggleButton.isOn() == false) // grid or linear recyclerView
            startActivity(new Intent(MainActivity.this, RecyclerViewGridActivity.class));
        else
            startActivity(new Intent(MainActivity.this, RecyclerViewLinearActivity.class));
    }

    public void gridViewClick(View v){
        startActivity(new Intent(MainActivity.this, GridViewActivity.class));
    }

    public void listViewClick(View v){
        startActivity(new Intent(MainActivity.this, ListViewActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_FOR_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            } else {
                requestPermission(this);
                Log.i("PERMISSION", "Permission Denied, You cannot see photos from Gallery.");
            }
        }
    }

    public void requestPermission(Activity currentActivity) {
        ActivityCompat.requestPermissions(currentActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ACCESS);
    }
}