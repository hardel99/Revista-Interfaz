package com.example.hardel.revin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Content extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti_prin);

        Toolbar tool = (Toolbar) findViewById(R.id.notice_toolbar);

        setSupportActionBar(tool);
        tool.setTitleTextColor(Color.WHITE);

        String title = getIntent().getStringExtra(MainActivity.TITLE);
        String subtitle = getIntent().getStringExtra(MainActivity.SUBTITLE);
        String desc = getIntent().getStringExtra(MainActivity.DESCRIPTION);
        String date = getIntent().getStringExtra(MainActivity.DATE);
        String imgPath = getIntent().getStringExtra(MainActivity.IMG);
        String category = getIntent().getStringExtra(MainActivity.CATEGORY);

        getSupportActionBar().setTitle(title);

        TextView titleText = (TextView) findViewById(R.id.title_full_notice);
        titleText.setText(title);

        TextView subtitleText = (TextView) findViewById(R.id.subtitle_full_notice);
        subtitleText.setText(subtitle);

        TextView dateText = (TextView) findViewById(R.id.date_full_notice);
        dateText.setText(date);

        TextView catText = (TextView) findViewById(R.id.category_full_notice);
        catText.setText(category);

        TextView descText = (TextView) findViewById(R.id.desc_full_notice);
        descText.setText(desc);

        ImageView img = (ImageView) findViewById(R.id.full_image_preview);
        Picasso.with(this).load(imgPath).into(img);
    }
}
