package teamb.cs262.calvin.edu.quest.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.R;
import teamb.cs262.calvin.edu.quest.SingleViewActivity;

/**
 * TaskListFragment is a singleton
 * To get an instance of this Fragment call TaskListFragment.getInstance()
 * rather than new TaskListFragment. This ensures there are duplicated Fragments
 */

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";

    private ArrayList<String> mImageUrls = new ArrayList<>(); // store the image urls

    private static TaskListFragment instance; // singleton instance

    @SuppressLint("ValidFragment")
    private TaskListFragment() {
        super();
    }

    /**
     * This returns the singleton TaskListFragment instance
     * @return TaskListFragment instance
     */
    public static TaskListFragment getInstance() {
        if(instance == null) {
            instance = newInstance();
        }
        return instance;
    }

    /**
     * Creates a new TaskListFragment instance
     * @return fragment
     */
    private static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initImageBitmaps();
    }

    /**
     * This method creates a new TaskListRecyclerViewAdapter to put the images into the
     * recyclerView that this fragment contains.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getContext(), mImageUrls));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();

                // Send intent to SingleViewActivity
                Intent i = new Intent(getContext(), SingleViewActivity.class);
                // Pass image index
                Bundle bundle = new Bundle();

                bundle.putInt("id", position);
                bundle.putStringArrayList("urls", mImageUrls);

                i.putExtras(bundle);

                startActivity(i);
            }
        });

        return rootView;
    }

    /**
     * This method also makes haptic feedback when it is resumed.  We may later make it so that
     * the app returns to the Task List when a QR code is scanned rather than the leaderBoard
     * fragment.
     */
    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if(bundle != null) {
            System.out.println("Task List has received this deciphered QR Code: " + bundle.getString("QR"));
            Toast.makeText(getContext(), bundle.getString("QR"), Toast.LENGTH_LONG).show();

            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
            bundle.remove("QR"); // this fixes score increment and vibrate on restart
        }
    }


    /**
     * Create the arraylist of the images we are using for the scavenger hunt.
     * The images are hosted at: https://postimg.cc/gallery/fsqlmqli/
     * Our application pulls in these images using the Glide library.
     */
    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps");

        if (!mImageUrls.contains("https://i.postimg.cc/rw7J4zfv/image.jpg")) {
            mImageUrls.add("https://i.postimg.cc/rw7J4zfv/image.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/JhJQ5mgt/image-1.jpg")) {
            mImageUrls.add("https://i.postimg.cc/JhJQ5mgt/image-1.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/bJh96Wcg/image-2.jpg")) {
            mImageUrls.add("https://i.postimg.cc/bJh96Wcg/image-2.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/sgMm154Y/image-3.jpg")) {
            mImageUrls.add("https://i.postimg.cc/sgMm154Y/image-3.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/yd3CPJKc/IMG-20181101-100434246.jpg")) {
            mImageUrls.add("https://i.postimg.cc/yd3CPJKc/IMG-20181101-100434246.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/RVBx7VDJ/IMG-20181101-180824927.jpg")) {
            mImageUrls.add("https://i.postimg.cc/RVBx7VDJ/IMG-20181101-180824927.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/8ch82qjC/IMG-20181101-180954395.jpg")) {
            mImageUrls.add("https://i.postimg.cc/8ch82qjC/IMG-20181101-180954395.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/MHk2rWZ4/IMG-20181101-181328330.jpg")) {
            mImageUrls.add("https://i.postimg.cc/MHk2rWZ4/IMG-20181101-181328330.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/MHk2rWZ4/IMG-20181101-181328330.jpg")) {
            mImageUrls.add("https://i.postimg.cc/MHk2rWZ4/IMG-20181101-181328330.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/DyPkBk9j/IMG-20181101-181507293.jpg")) {
            mImageUrls.add("https://i.postimg.cc/DyPkBk9j/IMG-20181101-181507293.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/1R6ZVNJZ/IMG-20181101-181621138.jpg")) {
            mImageUrls.add("https://i.postimg.cc/1R6ZVNJZ/IMG-20181101-181621138.jpg");
        }

        if (!mImageUrls.contains("https://i.postimg.cc/mr5GCYvT/IMG-20181101-182303299.jpg")) {
            mImageUrls.add("https://i.postimg.cc/mr5GCYvT/IMG-20181101-182303299.jpg");
        }
    }

}

