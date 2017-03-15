package com.ullrich.shape;

import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.CircleStyle;

public class BorderedSectorCircle implements Shape{
    private SectorCircle innerSquare;
    private SectorCircle outerSquare;
    private int angleSector;
    
    public BorderedSectorCircle(int angleSector, float radius, float borderSize, CircleStyle circleStyle){
        innerSquare = new SectorCircle(angleSector, radius, circleStyle.getInnerR(), circleStyle.getInnerG(), circleStyle.getInnerB(), circleStyle.getInnerA());
        outerSquare = new SectorCircle(angleSector, radius + borderSize, circleStyle.getOuterR(), circleStyle.getOuterG(), circleStyle.getOuterB(), circleStyle.getOuterA());
        this.angleSector = angleSector;
    }
    
    public void draw(GL10 gl){    	
    	gl.glPushMatrix();
    	gl.glRotatef((angleSector / 2), 0, 0, 1);
    	
    	outerSquare.draw(gl);
    	innerSquare.draw(gl);
    	gl.glPopMatrix();
    }
}
