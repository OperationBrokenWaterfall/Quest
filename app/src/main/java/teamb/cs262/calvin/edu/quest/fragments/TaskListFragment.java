package teamb.cs262.calvin.edu.quest.fragments;

import android.content.Context;
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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.R;


public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";

    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: started");
        initImageBitmaps();
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.task_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(getActivity(), mImageUrls);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

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
        }
    }


    //add the images for the hunt locations
    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps");

        mImageUrls.add("https://i.postimg.cc/rw7J4zfv/image.jpg");

        mImageUrls.add("https://i.postimg.cc/JhJQ5mgt/image-1.jpg");

        mImageUrls.add("https://i.postimg.cc/bJh96Wcg/image-2.jpg");

        mImageUrls.add("https://i.postimg.cc/sgMm154Y/image-3.jpg");

        mImageUrls.add("https://i.postimg.cc/yd3CPJKc/IMG-20181101-100434246.jpg");

        mImageUrls.add("https://i.postimg.cc/RVBx7VDJ/IMG-20181101-180824927.jpg");

        mImageUrls.add("https://i.postimg.cc/8ch82qjC/IMG-20181101-180954395.jpg");

        mImageUrls.add("https://i.postimg.cc/MHk2rWZ4/IMG-20181101-181328330.jpg");

        mImageUrls.add("https://i.postimg.cc/DyPkBk9j/IMG-20181101-181507293.jpg");

        mImageUrls.add("https://i.postimg.cc/1R6ZVNJZ/IMG-20181101-181621138.jpg");

        mImageUrls.add("https://i.postimg.cc/x1jrZpY0/IMG-20181101-181925713.jpg");

        mImageUrls.add("https://i.postimg.cc/mr5GCYvT/IMG-20181101-182303299.jpg");
    }

}

