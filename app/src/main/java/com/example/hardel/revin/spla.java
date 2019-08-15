package com.example.hardel.revin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class spla extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splat);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animat();
    }

    private void animat(){
        Animation anni= AnimationUtils.loadAnimation(this,R.anim.alpha);
        anni.reset();

        ImageView iv = (ImageView) findViewById(R.id.shinin);
        iv.clearAnimation();
        iv.setAnimation(anni);

        Thread tf=new Thread(){
            @Override
            public void run() {
                try{
                    int whait=0;

                    while (whait<1500){
                        sleep(100);
                        whait+=100;
                    }

                    Intent nt=new Intent().setClass(spla.this,MainActivity.class);
                    nt.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(nt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    spla.this.finish();
                }
            }
        };

        tf.start();
    }
}
