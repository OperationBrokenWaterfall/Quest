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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.R;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static teamb.cs262.calvin.edu.quest.fragments.QRCodeFragment.globPosition;
import static teamb.cs262.calvin.edu.quest.fragments.QRCodeFragment.qrText;
import static teamb.cs262.calvin.edu.quest.fragments.TaskListFragment.locationCodes;
import static teamb.cs262.calvin.edu.quest.fragments.TaskListFragment.locationKeys;


public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    //private ArrayList<String> mImageUrls;

    // Keep all Images in array
    public static String[] mThumbIds = {
            "https://i.postimg.cc/yd3CPJKc/IMG-20181101-100434246.jpg",
            "https://i.postimg.cc/RVBx7VDJ/IMG-20181101-180824927.jpg",
            "https://i.postimg.cc/8ch82qjC/IMG-20181101-180954395.jpg",
            "https://i.postimg.cc/MHk2rWZ4/IMG-20181101-181328330.jpg",
            "https://i.postimg.cc/DyPkBk9j/IMG-20181101-181507293.jpg",
            "https://i.postimg.cc/1R6ZVNJZ/IMG-20181101-181621138.jpg",
            "https://i.postimg.cc/mr5GCYvT/IMG-20181101-182303299.jpg",
            "https://i.postimg.cc/rw7J4zfv/image.jpg",
            "https://i.postimg.cc/JhJQ5mgt/image-1.jpg",
            "https://i.postimg.cc/bJh96Wcg/image-2.jpg",
            "https://i.postimg.cc/sgMm154Y/image-3.jpg",
            "https://i.postimg.cc/J7PVtyMc/IMG-20181120-124549582.jpg",
            "https://i.postimg.cc/HLQFmn6M/IMG-20181120-124600818.jpg",
            "https://i.postimg.cc/x1Q7c8P4/IMG-20181212-140108428.jpg"
    };

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(520, 520));
        imageView.setPadding(4, 4, 4, 4);

        try {
            if (!locationCodes.containsKey(locationKeys[position])) {
                imageView.setAlpha(50);
                imageView.setOnClickListener(null);
            }
        } catch (Exception e) { }


        Glide.with(mContext)
                .asBitmap()
                .load(mThumbIds[position])
                .into(imageView);

        return imageView;
    }

}
