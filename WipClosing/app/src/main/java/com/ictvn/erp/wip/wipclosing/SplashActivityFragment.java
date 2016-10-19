package com.ictvn.erp.wip.wipclosing;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SplashActivityFragment extends Fragment {

    public SplashActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        //get UI components
        TextView txtSlogan = (TextView) view.findViewById(R.id.txtSlogan);
        TextView txtPower = (TextView) view.findViewById(R.id.txtPower);
        ImageView imgView = (ImageView) view.findViewById(R.id.imageView);

        //Set text for Slogan
        txtSlogan.setText(getResources().getString(R.string.slogan));

        //Set text for Power
        txtPower.setText(getResources().getString(R.string.power_by));

        //Animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgView, "scaleX",0.5f,1f).setDuration(1000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgView, "scaleY",0.5f,1f).setDuration(1000);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(txtSlogan, "translationY",200f,0f).setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX,scaleY,translateY);
        set.setDuration(2000);
        set.start();

        return view;
    }
}
