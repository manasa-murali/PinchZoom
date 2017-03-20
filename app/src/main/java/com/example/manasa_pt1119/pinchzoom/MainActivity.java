package com.example.manasa_pt1119.pinchzoom;

import android.graphics.Color;

import android.support.v4.util.ArrayMap;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ScaleGestureDetector scaleGestureDetector;
float firstX,firstY,nextX,nextY;


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

        int action=event.getAction()& MotionEvent.ACTION_MASK;

if(event.getPointerCount()==1){
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                final float x = event.getX(0);
                final float y =event.getY(0);

                setFirstVal(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                final float nX=event.getX(0);
                final float nY=event.getY(0);
                setNextVal(nX,nY);
                relativeLayout.setTranslationX(relativeLayout.getTranslationX()+(nextX-firstX)*0.08f);
                relativeLayout.setTranslationY(relativeLayout.getTranslationY()+(nextY-firstY)*0.08f);
                break;

        }}
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private void setNextVal(float nX, float nY) {
        nextX=nX;
        nextY=nY;
    }

    public void setFirstVal(float x,float y)
{

    firstX=x;
    firstY=y;

}

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {



        private float newX;
        private float newY;


        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            newX = detector.getFocusX();
             newY = detector.getFocusY();

            return super.onScaleBegin(detector);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            if ( detector.getCurrentSpan() > detector.getPreviousSpan() + 1.5 ) {
              onZoom(detector.getCurrentSpan());
            } else if ( detector.getPreviousSpan() > detector.getCurrentSpan() + 1.5 ){
               onPinch(detector.getCurrentSpan());
                if ( relativeLayout.getScaleX() < 1 ) {
                    relativeLayout.setScaleX(1);
                    relativeLayout.setScaleY(1);
                    relativeLayout.setTranslationX(1);
                    relativeLayout.setTranslationY(1);
                }

            }

            return super.onScale(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {


            super.onScaleEnd(detector);
        }

        private void onZoom(float span) {


            if (scaleGestureDetector.isInProgress()) {
                relativeLayout.setScaleX(relativeLayout.getScaleX() +0.001f*getResources().getDisplayMetrics().densityDpi/getResources().getDisplayMetrics().density);
                relativeLayout.setScaleY(relativeLayout.getScaleY() +0.001f*getResources().getDisplayMetrics().densityDpi/getResources().getDisplayMetrics().density);
                relativeLayout.setTranslationX(relativeLayout.getTranslationX() + (relativeLayout.getPivotX() - newX) * (1 - relativeLayout.getScaleX()));
                relativeLayout.setTranslationY(relativeLayout.getTranslationY() + (relativeLayout.getPivotY() - newY) * (1 - relativeLayout.getScaleY()));
                relativeLayout.setPivotX(newX);
                relativeLayout.setPivotY(newY);

            } else {
                relativeLayout.setScaleX(relativeLayout.getScaleX());
                relativeLayout.setScaleY(relativeLayout.getScaleY());
                relativeLayout.setTranslationX(relativeLayout.getTranslationX());
                relativeLayout.setTranslationY(relativeLayout.getTranslationY());
            }

        }

        private void onPinch(float span) {


                relativeLayout.setScaleX(relativeLayout.getScaleX() -0.001f*getResources().getDisplayMetrics().densityDpi/getResources().getDisplayMetrics().density);
                relativeLayout.setScaleY(relativeLayout.getScaleY() -0.001f*getResources().getDisplayMetrics().densityDpi/getResources().getDisplayMetrics().density);
                relativeLayout.setTranslationX(relativeLayout.getTranslationX() + (relativeLayout.getPivotX() - newX) * (1 - relativeLayout.getScaleX()));
                relativeLayout.setTranslationY(relativeLayout.getTranslationY() + (relativeLayout.getPivotY() - newY) * (1 - relativeLayout.getScaleY()));
                relativeLayout.setPivotX(newX);
                relativeLayout.setPivotY(newY);

        }
    }


}
