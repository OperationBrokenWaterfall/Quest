package teamb.cs262.calvin.edu.quest.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.R;

import static android.support.constraint.Constraints.TAG;

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

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i + 1))
                .into(holder.image2);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        CheckBox checkBox;
        ImageView image2;
        CheckBox checkBox2;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            checkBox = itemView.findViewById(R.id.checkBox);
            image2 = itemView.findViewById(R.id.imageView2);
            checkBox2 = itemView.findViewById(R.id.checkBox2);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
