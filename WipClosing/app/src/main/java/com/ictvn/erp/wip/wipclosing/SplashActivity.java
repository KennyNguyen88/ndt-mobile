package com.ictvn.erp.wip.wipclosing;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Set text for Slogan
        TextView txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        txtSlogan.setText(getResources().getString(R.string.slogan));

        //Set text for Power
        TextView txtPower = (TextView) findViewById(R.id.txtPower);
        txtPower.setText(getResources().getString(R.string.power_by));

        //Logo animation
        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imgView, "scaleX",0.5f,1f).setDuration(1000);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imgView, "scaleY",0.5f,1f).setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX,animatorY);
        set.setDuration(2000);
        set.start();

        //Background Activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();//Don't let user comeback splash screen
            }
        }, 2000);

    }

}
