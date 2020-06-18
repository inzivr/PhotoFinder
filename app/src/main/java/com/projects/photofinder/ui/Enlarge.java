package com.projects.photofinder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.projects.photofinder.R;

// Zoom Feature is refered from this link :- https://medium.com/quick-code/pinch-to-zoom-with-multi-touch-gestures-in-android-d6392e4bf52d

public class Enlarge extends AppCompatActivity implements View.OnTouchListener{

    private String large;
    private ImageView showImage;
    private ProgressBar progressBar;

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);

        Intent show = getIntent();
        large = show.getStringExtra("Large");
        showImage = (ImageView) findViewById(R.id.enlarged_img_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Glide.with(getApplicationContext()).load(large).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Image Loading Failed : Try Again",Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(showImage);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        showImage.setOnTouchListener(this::onTouch);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
            mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            showImage.setScaleX(mScaleFactor);
            showImage.setScaleY(mScaleFactor);
            return true;
        }
    }

}