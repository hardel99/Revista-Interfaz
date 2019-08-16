package com.example.hardel.revin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animate();
    }

    private void animate(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.main_logo);
        iv.clearAnimation();
        iv.setAnimation(anim);

        Thread tf=new Thread(){
            @Override
            public void run() {
                try{
                    int delay = 0;

                    while (delay < 1500){
                        sleep(100);
                        delay += 100;
                    }

                    Intent in = new Intent().setClass(SplashScreen.this, MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(in);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };

        tf.start();
    }
}
