package com.example.magupta.imagesearch.models;

import android.text.Html;
import android.text.Spanned;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by magupta on 11/1/15.
 */
public class ImageItem {
    /*
    width, height, tbUrl, title, url
    responseData => results => [x] => tbUrl
    responseData => results => [x] => title
    responseData => results => [x] => url
    responseData => results => [x] => width
    responseData => results => [x] => height
     */

    private String url;
    private String thumbUrl;
    private String title;
    private String width;
    private String height;
    private String thumbWidth;
    private String thumbHeight;

    // ImageItem obj from Json Obj
    public ImageItem(JSONObject jsonObject){
        try {
            this.url = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
            this.width = jsonObject.getString("width");
            this.height = jsonObject.getString("height");
            this.thumbWidth = jsonObject.getString("tbWidth");
            this.thumbHeight = jsonObject.getString("tbHeight");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageItem> fromJSONArray(JSONArray jsonArray){
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                imageItems.add(new ImageItem(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return imageItems;
    }

    public String getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(String thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public String getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(String thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Spanned getTitle() {
        return Html.fromHtml(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
