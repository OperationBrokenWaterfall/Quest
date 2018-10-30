package teamb.cs262.calvin.edu.quest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.task_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(getActivity(), mImageUrls);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps");

        mImageUrls.add("https://i.postimg.cc/bNVNnH3J/image.png");

        mImageUrls.add("https://i.postimg.cc/593t4Mz8/image-1.png");

        mImageUrls.add("https://i.postimg.cc/L8q43YbV/image-2.png");

        mImageUrls.add("https://i.postimg.cc/kXggDhNz/image-3.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Microsoft_Word_2013_logo.svg/2000px-Microsoft_Word_2013_logo.svg.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Microsoft_PowerPoint_2013_logo.svg/2000px-Microsoft_PowerPoint_2013_logo.svg.png");

        mImageUrls.add("https://static.makeuseof.com/wp-content/uploads/2015/12/onenote-quick-access-toolbar-intro-670x335.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/8/86/Microsoft_Excel_2013_logo.svg/2000px-Microsoft_Excel_2013_logo.svg.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Microsoft_Access_2013_logo.svg/2000px-Microsoft_Access_2013_logo.svg.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Microsoft_Publisher_2013_logo.svg/2000px-Microsoft_Publisher_2013_logo.svg.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Microsoft_Office_Visio_%282013%E2%80%93present%29.svg/1200px-Microsoft_Office_Visio_%282013%E2%80%93present%29.svg.png");

        mImageUrls.add("https://bitabiz.dk/wp-content/uploads/2017/03/microsoft-exchange-logo.png");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Microsoft_Outlook_2013_logo.svg/2000px-Microsoft_Outlook_2013_logo.svg.png");

        mImageUrls.add("https://www.v3.co.uk/w-images/d2560663-ef37-4162-a3ca-24a6ef686791/0/microsoftsharepointlogo-580x358.png");

        mImageUrls.add("https://www.v3.co.uk/w-images/c20bb057-6906-4eda-997f-c1784d48b2f0/0/onedrivelogomicrosoft-580x358.png");
    }

}

