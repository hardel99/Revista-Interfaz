package com.example.hardel.revin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Content extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti_prin);

        Toolbar tool = (Toolbar) findViewById(R.id.atlos);

        setSupportActionBar(tool);
        tool.setTitleTextColor(Color.WHITE);

        String title = getIntent().getStringExtra(MainActivity.TITLE);
        String subtitle = getIntent().getStringExtra(MainActivity.SUBTITLE);
        String desc = getIntent().getStringExtra(MainActivity.DESCRIPTION);
        String date = getIntent().getStringExtra(MainActivity.DATE);
        String imgPath = getIntent().getStringExtra(MainActivity.IMG);
        String category = getIntent().getStringExtra(MainActivity.CATEGORY);

        getSupportActionBar().setTitle(title);

        TextView ja = (TextView) findViewById(R.id.titco);
        ja.setText(title);

        TextView jaja = (TextView) findViewById(R.id.subtit);
        jaja.setText(subtitle);

        TextView jajaja = (TextView) findViewById(R.id.fey);
        jajaja.setText(date);

        TextView jajajaja = (TextView) findViewById(R.id.catio);
        jajajaja.setText(category);

        TextView jajajajaja = (TextView) findViewById(R.id.desc);
        jajajajaja.setText(desc);

        ImageView img = (ImageView) findViewById(R.id.imaco);
        Picasso.with(this).load(imgPath).into(img);
    }
}
