package com.ullrich.shape;

import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.CircleStyle;

import android.util.Log;

public class BorderedCircle implements Shape{
    private Circle innerSquare;
    private Circle outerSquare;
    
    public BorderedCircle(float radius, float borderSize, CircleStyle circleStyle){
        innerSquare = new Circle(radius, circleStyle.getInnerR(), circleStyle.getInnerG(), circleStyle.getInnerB(), circleStyle.getInnerA());
        outerSquare = new Circle(radius + borderSize, circleStyle.getOuterR(), circleStyle.getOuterG(), circleStyle.getOuterB(), circleStyle.getOuterA());
    }
    
    public void draw(GL10 gl){    	
    	outerSquare.draw(gl);
    	innerSquare.draw(gl);
    }
}
