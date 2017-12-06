package br.com.gunna.basemvvmrxproject.androidapp.utils;

import android.os.Handler;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Daniel on 06/12/17.
 */

public class AnimationUtils {

    public static void fadeOut(final View view) {
        if (view != null) {
            YoYo.with(Techniques.FadeOut)
                    .duration(400)
                    .playOn(view);
            Handler handler = new Handler();
            handler.postDelayed(() -> view.setVisibility(View.GONE), 410);
        }
    }


    public static void fadeIn(View view) {
        if (view != null) {
            YoYo.with(Techniques.FadeIn)
                    .duration(400)
                    .playOn(view);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void fadeInWithDuration(View view, int duration) {
        if (view != null) {
            YoYo.with(Techniques.FadeIn)
                    .duration(duration)
                    .playOn(view);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void crossView(final View viewShow, View viewHide) {
        try {
            fadeOut(viewHide);
            Handler handler = new Handler();
            handler.postDelayed(() -> fadeIn(viewShow), 410);
        } catch (Exception ex) {
        }
    }

    public static void shakeError(View view) {
        if (view != null) {
            YoYo.with(Techniques.Shake)
                    .duration(200)
                    .playOn(view);
            view.setSelected(true);
        }
    }

    public static void fadeOutInvisible(final View view) {
        if (view != null) {
            YoYo.with(Techniques.FadeOut)
                    .duration(400)
                    .playOn(view);
            Handler handler = new Handler();
            handler.postDelayed(() -> view.setVisibility(View.INVISIBLE), 410);
        }
    }


    public static void zoomIn(View view){
        if (view != null) {
            YoYo.with(Techniques.ZoomIn)
                    .duration(400)
                    .playOn(view);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void zoomOut(final View view){
        if (view != null) {
            YoYo.with(Techniques.ZoomOut)
                    .duration(400)
                    .playOn(view);
            Handler handler = new Handler();
            handler.postDelayed(() -> view.setVisibility(View.GONE), 410);
        }
    }
}
