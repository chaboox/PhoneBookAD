package com.example.annuairegsh.Manager;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class AnimationManager {

    public  static void SetToVisibleLeft(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(-view.getWidth(),0, 0,0);
        animate.setDuration(250);
        animate.setFillAfter(true);

        view.startAnimation(animate);
    }

    public static void setToInvisibleLeft(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(250);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    public  static void SetToVisibleRight(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(view.getWidth(),0, 0,0);
        animate.setDuration(250);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public static void setToInvisibleRight(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(250);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
}
