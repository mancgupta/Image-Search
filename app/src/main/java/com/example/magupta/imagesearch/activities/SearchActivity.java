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
import com.example.magupta.imagesearch.utilities.EndlessScrollListener;
import com.example.magupta.imagesearch.utilities.GoogleClient;
import com.example.magupta.imagesearch.views.adapters.ImageItemAdapter;
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
    private ImageItemAdapter imageItemAdapter;
    private GoogleClient googleClient;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.icon_activity_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_search);
        googleClient = new GoogleClient();
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

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                googleClient.page = page;
                fetchImages(false);
                return true;
            }
        });

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // Get image result into the intent
                ImageItem imageItem = imageItems.get(position);
                // Pass image result into  the intent
                i.putExtra("item", imageItem);
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

        if (id == R.id.filter_result){
            Intent i = new Intent(SearchActivity.this, FilterActivity.class);
            i.putExtra("image_size", googleClient.imageSize);
            i.putExtra("color", googleClient.color);
            i.putExtra("type", googleClient.type);
            i.putExtra("site", googleClient.site);
            startActivityForResult(i, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    // Fired whenever button is press ( android:onClick)
    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();
        googleClient.query = query;
        googleClient.page = 1;
        fetchImages(true);
    }

    public void fetchImages(final boolean clearList){
        googleClient.fetchImages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    if (clearList)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            googleClient.imageSize = data.getExtras().getString("image_size");
            googleClient.color = data.getExtras().getString("color");
            googleClient.type = data.getExtras().getString("type");
            googleClient.site = data.getExtras().getString("site");
            fetchImages(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
