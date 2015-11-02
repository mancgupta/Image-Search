package com.example.magupta.imagesearch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.magupta.imagesearch.R;
import com.example.magupta.imagesearch.models.ImageItem;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        // PUll out url
        ImageItem imageItem = (ImageItem)getIntent().getSerializableExtra("item");
        // Find Image View
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        // Load image url using picaso
        Picasso.with(this).load(imageItem.getUrl()).into(ivImageResult);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
