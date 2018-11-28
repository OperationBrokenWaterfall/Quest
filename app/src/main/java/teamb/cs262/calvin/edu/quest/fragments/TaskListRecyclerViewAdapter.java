package teamb.cs262.calvin.edu.quest.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.R;
import teamb.cs262.calvin.edu.quest.ExpandedImages;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public TaskListRecyclerViewAdapter(Context context, ArrayList<String> images) {
        mContext = context;
        mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_list_item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i))
                .into(holder.image);


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(mContext, ExpandedImages.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("imageArrayList", mImages);
            bundle.putInt("image_url", i);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
