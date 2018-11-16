package teamb.cs262.calvin.edu.quest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class expandedImages extends AppCompatActivity {


    Bundle bundle;
    PhotoView myImage;
    String url = "";
    PhotoView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_images);

        image = findViewById(R.id.myImage);

        bundle = getIntent().getExtras();
        url = bundle.getString("image_url");

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(image);

    }
}
