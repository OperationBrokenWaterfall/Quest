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
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import teamb.cs262.calvin.edu.quest.R;
import teamb.cs262.calvin.edu.quest.SingleViewActivity;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * TaskListFragment is a singleton
 * To get an instance of this Fragment call TaskListFragment.getInstance()
 * rather than new TaskListFragment. This ensures there are duplicated Fragments
 */

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";

    // Dictionary to check locations visited
    public static Map<String, Integer> locationCodes = new HashMap<String, Integer>() {{
        put("pencils", 0);
        put("seniors", 1);
        put("cokeMachine", 2);
        put("chair68", 3);
        put("randomDude", 4);
        put("aquarium", 5);
        put("boxesWithinBoxes", 6);
        put("clock", 7);
        put("filmSet", 8);
        put("neonDove", 9);
        put("lifeJacket", 10);
        put("maroon20", 11);
        put("maroonPrinter", 12);
        put("vanderLinden", 13);
    }};

    public static String[] locationKeys = {"pencils", "seniors", "cokeMachine", "chair68",
            "randomDude", "aquarium", "boxesWithinBoxes", "clock",
            "filmSet", "neonDove", "lifeJacket", "maroon20",
            "maroonPrinter", "vanderLinden"};

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

        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){

                // Send intent to SingleViewActivity
                Intent i = new Intent(getContext(), SingleViewActivity.class);
                i.putExtra("id", position);
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
            //Toast.makeText(getContext(), bundle.getString("QR"), Toast.LENGTH_LONG).show();

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

}