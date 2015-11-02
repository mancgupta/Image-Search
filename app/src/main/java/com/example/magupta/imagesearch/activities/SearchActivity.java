package com.example.magupta.imagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.magupta.imagesearch.R;
import com.example.magupta.imagesearch.models.ImageItem;
import com.example.magupta.imagesearch.views.adapters.ImageItemAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageItem> imageItems;
    private final String IMAGE_API_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    private int resultCount = 8;
    private ImageItemAdapter imageItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageItems = new ArrayList<>();

        // Create Adapter
        imageItemAdapter = new ImageItemAdapter(SearchActivity.this, imageItems);

        // Get List View
        gvResults = (GridView) findViewById(R.id.gvResults);

        // Set Adapter
        gvResults.setAdapter(imageItemAdapter);

    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // Get image result into the intent
                ImageItem imageItem = imageItems.get(position);
                // Pass image result into  the intent
                i.putExtra("url", imageItem.getUrl());
                // Launch Image display activity
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    // Fired whenever button is press ( android:onClick)
    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();

        fetchImages(query);
    }

    public void fetchImages(String query){
        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = IMAGE_API_URL + "&q=" + query + "&rsz=" + Integer.toString(resultCount);
        Log.i("DEBUG", searchUrl);
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageItems.clear();
                    imageItems.addAll(ImageItem.fromJSONArray(imageResultsJson));
                    Log.i("DEBUG", imageItems.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("DEBUG", response.toString());
                imageItemAdapter.notifyDataSetChanged();
            }
        });
    }

}
