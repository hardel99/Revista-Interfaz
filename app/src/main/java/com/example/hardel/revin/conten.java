package com.example.hardel.revin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.IOException;

public class conten extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti_prin);

        Toolbar tul = (Toolbar) findViewById(R.id.atlos);

        setSupportActionBar(tul);
        tul.setTitleTextColor(Color.WHITE);

        String tite =getIntent().getStringExtra(MainActivity.TIL);
        String suv =getIntent().getStringExtra(MainActivity.SUBT);
        String conte =getIntent().getStringExtra(MainActivity.CONT);
        String dia =getIntent().getStringExtra(MainActivity.FAI);
        String re=getIntent().getStringExtra(MainActivity.IMA);
        String kt=getIntent().getStringExtra(MainActivity.CATER);

        getSupportActionBar().setTitle(tite);

        TextView ja = (TextView) findViewById(R.id.titco);
        ja.setText(tite);

        TextView jaja = (TextView) findViewById(R.id.subtit);
        jaja.setText(suv);

        TextView jajaja = (TextView) findViewById(R.id.fey);
        jajaja.setText(dia);

        TextView jajajaja = (TextView) findViewById(R.id.catio);
        jajajaja.setText(kt);

        TextView jajajajaja = (TextView) findViewById(R.id.desc);
        jajajajaja.setText(conte);

        /*Bitmap it=null;

        try {
            FileInputStream fis=openFileInput(re);
            it=BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ImageView imav = (ImageView) findViewById(R.id.imaco);
        Picasso.with(this).load(re).into(imav);
    }
}
