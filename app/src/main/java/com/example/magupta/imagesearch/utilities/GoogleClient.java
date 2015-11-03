package com.example.magupta.imagesearch.utilities;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by magupta on 11/2/15.
 */
public class GoogleClient {
    private static final String IMAGE_API_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    private static int resultCount = 8;
    private AsyncHttpClient httpClient;
    public String query;
    public int page;
    public String imageSize;
    public String color;
    public String type;
    public String site;

    public GoogleClient(){
        httpClient = new AsyncHttpClient();
    }
    public void fetchImages(JsonHttpResponseHandler handler){
        String url = IMAGE_API_URL + "&q=" + query + "&rsz=" + Integer.toString(resultCount) + "&start=" + ((page-1) * resultCount);
        if ( imageSize != null) {
            url += "&imgsz=" + imageSize;
        }

        if ( color != null) {
            url += "&imgcolor=" + color;
        }

        if ( type != null) {
            url += "&imgtype=" + type;
        }

        if ( site != null) {
            url += "&as_sitesearch=" + site;
        }

        httpClient.get(url, handler);
    }
}
