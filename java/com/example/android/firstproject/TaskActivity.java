package com.example.android.firstproject;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class TaskActivity extends AppCompatActivity {

    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        imageView2 =(ImageView)findViewById(R.id.imageView2);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.auto_loader);
//        gifImageView.setGifImageUri(uri);
        Glide.with(this)
                .load(R.drawable.travel_loader)
                .asGif()
                .into(imageView2);
    }
}
