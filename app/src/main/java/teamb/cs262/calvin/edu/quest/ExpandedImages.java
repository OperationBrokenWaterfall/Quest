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

public class ExpandedImages extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {


    private PhotoView image;
    private Bundle bundle;
    private int imgNum;
    private ArrayList<String> mImages = new ArrayList();
    GestureDetector gestureDetector;


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

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        if(motionEvent1.getY() - motionEvent2.getY() > 50){

            //Toast.makeText(ExpandedImages.this , " Swipe Up " , Toast.LENGTH_LONG).show();
            return true;
        }

        if(motionEvent2.getY() - motionEvent1.getY() > 50){

            //Toast.makeText(ExpandedImages.this , " Swipe Down " , Toast.LENGTH_LONG).show();
            return true;
        }

        if(motionEvent1.getX() - motionEvent2.getX() > 50){

            //Toast.makeText(ExpandedImages.this , " Swipe Left " , Toast.LENGTH_LONG).show();
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

        if(motionEvent2.getX() - motionEvent1.getX() > 50) {

            //Toast.makeText(ExpandedImages.this, " Swipe Right ", Toast.LENGTH_LONG).show();
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
        else {

            return true ;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==image)
            if(gestureDetector.onTouchEvent(event))
                return true;
        return false;
    }
}
