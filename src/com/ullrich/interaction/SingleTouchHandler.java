package com.ullrich.interaction;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SingleTouchHandler implements OnTouchListener{
		boolean isTouched;
	    int touchX;
	    int touchY;
	    float scaleX;
	    float scaleY;

	    public SingleTouchHandler(float scaleX, float scaleY) {
	        this.scaleX = scaleX;
	        this.scaleY = scaleY;
	    }

	    public boolean onTouch(View v, MotionEvent event) {
	        synchronized(this) {
	           
	            switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                isTouched = true;
	                break;
	            case MotionEvent.ACTION_MOVE:
	                isTouched = true;
	                break;
	            case MotionEvent.ACTION_CANCEL:                
	            case MotionEvent.ACTION_UP:
	                isTouched = false;
	                break;
	            }
	            
	            touchX = (int)(event.getX() * scaleX);
	            touchY = (int)(event.getY() * scaleY);                      
	            
	            Log.d("Touch", "X: " + this.touchX + " Y: " + this.touchY);
	            
	            return true;
	        }
	    }
}
