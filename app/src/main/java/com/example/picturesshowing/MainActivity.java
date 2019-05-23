package com.example.picturesshowing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test1.SuperToggleRect;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ACCESS = 110;
    private Button recyclerViewButton;
    private Button staggeredGridViewButton;
    private Button gridViewButton;
    private Button listViewButton;
    private SuperToggleRect toggleButton;
    private TextView APKtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        staggeredGridViewButton = findViewById(R.id.staggeredViewButton);
        recyclerViewButton = findViewById(R.id.recyclerViewButton);
        gridViewButton = findViewById(R.id.gridViewButton);
        listViewButton = findViewById(R.id.listViewButton);
        toggleButton = findViewById(R.id.toggleButton);
        APKtext = findViewById(R.id.APKversion);

        Integer colorNumOne = Color.parseColor("#364EEC");
        Integer colorNumTwo = Color.parseColor("#54E3FF");
        toggleButton.setColorOn(colorNumOne);
        toggleButton.setColorOff(colorNumTwo);
//        toggleButton.getColorOff();
//        toggleButton.setEnabled(false);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission has already been granted
        } else {
            requestPermission();
        }
    }

    public void recyclerViewClick(View v) {
        if (toggleButton.isOn() == false) // grid or linear recyclerView
            startActivity(new Intent(MainActivity.this, RecyclerViewGridActivity.class));
        else
            startActivity(new Intent(MainActivity.this, RecyclerViewLinearActivity.class));
    }

    public void gridViewClick(View v) {
        startActivity(new Intent(MainActivity.this, GridViewActivity.class));
    }

    public void staggeredGridViewClick(View v) {
        startActivity(new Intent(MainActivity.this, RecyclerViewStaggeredGridActivity.class));
    }

    public void listViewClick(View v) {
        startActivity(new Intent(MainActivity.this, ListViewActivity.class));
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ACCESS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            requestPermission();
            Log.i("kolosova_permission", "Permission Denied, You cannot see photos from Gallery.");
        }
    }
}