package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * This class expands the thumbnail images that are in the recyclerView in the TaskListFragment.
 * The images are opened in a new activity and displayed in a imageView that takes up the whole
 * phone screen.
 */
public class ExpandedImages extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {


    private PhotoView image;
    private Bundle bundle;
    private int imgNum;
    private ArrayList<String> mImages = new ArrayList(); // stores the image urls
    GestureDetector gestureDetector;


    /**
     * When created show the originally desired expanded image from the recyclerView in the
     * TaskListFragment.  Set up a gesture detector to look for left/right swipes.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_images);

        image = findViewById(R.id.myImage);

        bundle = getIntent().getExtras();
        imgNum = bundle.getInt("image_url");
        mImages = bundle.getStringArrayList("imageArrayList");

        gestureDetector = new GestureDetector(ExpandedImages.this, ExpandedImages.this);
        image.setOnTouchListener(this);

        Glide.with(this)
                .asBitmap()
                .load(mImages.get(imgNum))
                .into(image);
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
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
    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        //Swiped from right to left
        if(motionEvent1.getX() - motionEvent2.getX() > 30){
            if (imgNum != mImages.size() - 1) {
                Glide.with(this)
                        .asBitmap()
                        .load(mImages.get(++imgNum))
                        .into(image);
            } else {
                imgNum = 0;
                Glide.with(this)
                        .asBitmap()
                        .load(mImages.get(imgNum))
                        .into(image);
            }
            return true;
        }

        //Swiped left to right
        if(motionEvent2.getX() - motionEvent1.getX() > 30) {
            if (imgNum != 0) {
                Glide.with(this)
                        .asBitmap()
                        .load(mImages.get(--imgNum))
                        .into(image);
            } else {
                imgNum = mImages.size() - 1;
                Glide.with(this)
                        .asBitmap()
                        .load(mImages.get(imgNum))
                        .into(image);
            }
            return true;
        }
        else { return true ; }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==image)
            if(gestureDetector.onTouchEvent(event))
                return true;
        return false;
    }
}
