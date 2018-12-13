package teamb.cs262.calvin.edu.quest;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.fragments.ImageAdapter;

import static teamb.cs262.calvin.edu.quest.fragments.ImageAdapter.mThumbIds;
import static teamb.cs262.calvin.edu.quest.fragments.TaskListFragment.locationCodes;
import static teamb.cs262.calvin.edu.quest.fragments.TaskListFragment.locationKeys;


/**
 * This class expands the thumbnail images that are in the recyclerView in the TaskListFragment.
 * The images are opened in a new activity and displayed in a imageView that takes up the whole
 * phone screen.
 */
public class SingleViewActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {

    GestureDetector gestureDetector;
    private int position;
    ImageView imageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_images);

        // Get intent and bundle data
        Intent i = getIntent();
        position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        imageView = (ImageView) findViewById(R.id.SingleView);
        if (visited(position)) {
            imageView.setAlpha(50);
        } else { imageView.setAlpha(255); }
        gestureDetector = new GestureDetector(SingleViewActivity.this, SingleViewActivity.this);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(mThumbIds[position])
                .into(imageView);
    }

    /**
     * Create a gesture detector to allow for swiping left/right between enlarged images
     * for easier viewing.
     *
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public boolean onDown(MotionEvent e) {
        return false;
    }

    public void onShowPress(MotionEvent e) {

    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public void onLongPress(MotionEvent e) {
    }

    /**
     * This looks for left/right fling gestures and will move to the next image on a swipe.
     *
     * @param motionEvent1
     * @param motionEvent2
     * @param X
     * @param Y
     * @return
     */
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        //Swiped from right to left
        if(motionEvent1.getX() - motionEvent2.getX() > 30){
            if (position != mThumbIds.length - 1) {
                Glide.with(this)
                        .asBitmap()
                        .load(mThumbIds[++position])
                        .into(imageView);
                if (visited(position)) {
                    imageView.setAlpha(50);
                } else { imageView.setAlpha(255); }
            } else {
                position = 0;
                Glide.with(this)
                        .asBitmap()
                        .load(mThumbIds[position])
                        .into(imageView);
                if (visited(position)) {
                    imageView.setAlpha(50);
                } else { imageView.setAlpha(255); }
            }
            return true;
        }

        //Swiped left to right
        if(motionEvent2.getX() - motionEvent1.getX() > 30) {
            if (position != 0) {
                Glide.with(this)
                        .asBitmap()
                        .load(mThumbIds[--position])
                        .into(imageView);
                if (visited(position)) {
                    imageView.setAlpha(50);
                } else { imageView.setAlpha(255); }
            } else {
                position = mThumbIds.length - 1;
                Glide.with(this)
                        .asBitmap()
                        .load(mThumbIds[position])
                        .into(imageView);
                if (visited(position)) {
                    imageView.setAlpha(50);
                } else { imageView.setAlpha(255); }
            }
            return true;
        }
        else { return true ; }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if(v==imageView)
            if(gestureDetector.onTouchEvent(event))
                return true;
        return false;
    }

    private boolean visited(int position) {
        if (!locationCodes.containsKey(locationKeys[position])) {
            return true;
        }
        return false;
    }

}
