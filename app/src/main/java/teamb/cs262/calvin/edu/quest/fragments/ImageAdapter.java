package teamb.cs262.calvin.edu.quest.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.R;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;


    private ArrayList<String> mImageUrls;


    // Constructor
    public ImageAdapter(Context c, ArrayList images) {
        mContext = c;
        mImageUrls = images;
    }

    public int getCount() {
        return mImageUrls.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(520, 520));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
            imageView.setId(position);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(imageView);

        return imageView;
    }
}
