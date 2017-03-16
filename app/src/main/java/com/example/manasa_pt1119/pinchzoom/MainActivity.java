package com.example.manasa_pt1119.pinchzoom;

import android.graphics.Color;

import android.support.v4.util.ArrayMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ScaleGestureDetector scaleGestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.rellay);

        relativeLayout.setBackgroundColor(Color.BLUE);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
      }
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        scaleGestureDetector.onTouchEvent(event);
        return true;
    }



    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {


        private float currentSpan = 0;


        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {


            currentSpan = scaleGestureDetector.getCurrentSpan();
            return super.onScaleBegin(detector);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {


            if (detector.getScaleFactor() < 1) {
                if (relativeLayout.getScaleX() > 1 && relativeLayout.getScaleY() > 1) {
                    onPinch(currentSpan);

                } else {
                    relativeLayout.setScaleX(1);
                    relativeLayout.setScaleY(1);
                }
            } else if (detector.getScaleFactor() > 1) {

                onZoom(currentSpan);
            }

            return super.onScale(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {


            super.onScaleEnd(detector);
        }

        private void onZoom(float span) {


            if (scaleGestureDetector.isInProgress()) {
                relativeLayout.setScaleX(relativeLayout.getScaleX() + 0.0002f * span);
                relativeLayout.setScaleY(relativeLayout.getScaleY() + 0.0002f * span);

         relativeLayout.setPivotX(scaleGestureDetector.getFocusX());
         relativeLayout.setPivotY(scaleGestureDetector.getFocusY());
            } else if (!(scaleGestureDetector.isInProgress())) {
                relativeLayout.setScaleX(relativeLayout.getScaleX());
                relativeLayout.setScaleY(relativeLayout.getScaleY());
            }

        }

        private void onPinch(float span) {

            if (scaleGestureDetector.isInProgress()) {
                relativeLayout.setScaleX(relativeLayout.getScaleX() - 0.0002f * span);
                relativeLayout.setScaleY(relativeLayout.getScaleY() - 0.0002f * span);

                relativeLayout.setPivotX(scaleGestureDetector.getFocusX());
                relativeLayout.setPivotY(scaleGestureDetector.getFocusY());

            } else if (!(scaleGestureDetector.isInProgress())) {
                relativeLayout.setScaleX(relativeLayout.getScaleX());
                relativeLayout.setScaleY(relativeLayout.getScaleY());
            }
        }
    }


}
