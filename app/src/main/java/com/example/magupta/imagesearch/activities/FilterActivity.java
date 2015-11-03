package com.example.magupta.imagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.magupta.imagesearch.R;

public class FilterActivity extends AppCompatActivity {
    private Spinner imageSpinner;
    private Spinner colorSpinner;
    private Spinner typeSpinner;
    private EditText siteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setupViews();
        String imageSize = getIntent().getStringExtra("image_size");
        String color = getIntent().getStringExtra("color");
        String type = getIntent().getStringExtra("type");
        String site = getIntent().getStringExtra("site");
//        Spinner color = (Spinner) view.findViewById(R.id.etColor);
//        color.setSelection(FilterParams.getIndex(
//                args.getString("color"),
//                getResources().getStringArray(R.array.filter_colors)
//        ));
//
        if (imageSize != null){
            imageSpinner.setSelection(getIndex(imageSize,getResources().getStringArray(R.array.filter_sizes)));
        }
        if (color != null){
            colorSpinner.setSelection(getIndex(color,getResources().getStringArray(R.array.filter_colors)));
        }
        if (type != null){
            typeSpinner.setSelection(getIndex(type, getResources().getStringArray(R.array.filter_types)));
        }
        if (site != null){
            siteText.setText(site);
        }
    }

    public static int getIndex(String value, String[] array) {
        int index = 0;

        if (value.equals("any"))
            return index;

        for ( int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void setupViews() {
        imageSpinner = (Spinner)findViewById(R.id.sImageSize);
        colorSpinner = (Spinner)findViewById(R.id.sColorFilter);
        typeSpinner = (Spinner)findViewById(R.id.sImageType);
        siteText = (EditText)findViewById(R.id.etSiteFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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

    public void saveSearchFilters(View view) {
        String imageSize = imageSpinner.getSelectedItem().toString();
        String color = colorSpinner.getSelectedItem().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String site = siteText.getText().toString();

        Intent i = new Intent();
        i.putExtra("image_size", imageSize);
        i.putExtra("color", color);
        i.putExtra("type", type);
        i.putExtra("site", site);
        setResult(RESULT_OK, i);
        finish();
    }
}
