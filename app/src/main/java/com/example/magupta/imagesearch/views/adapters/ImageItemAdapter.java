package com.example.magupta.imagesearch.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magupta.imagesearch.R;
import com.example.magupta.imagesearch.models.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by magupta on 11/1/15.
 */
public class ImageItemAdapter extends ArrayAdapter<ImageItem> {
    public ImageItemAdapter(Context context, ArrayList<ImageItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItem imageItem = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item_result, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        // clear out image
//        ivImage.setImageResource(0);
        tvTitle.setText(imageItem.getTitle());

        Picasso.with(getContext()).load(imageItem.getThumbUrl()).placeholder(R.mipmap.icon_activity_bar).into(ivImage);
        return convertView;
    }
}
