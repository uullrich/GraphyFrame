package com.ullrich.shape;

import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.TriangleStyle;

public class BorderedTriangle implements Shape{
	private Triangle innerTriangle;
	private Triangle outerTriangle;
	private float scaleTemp;
	private float scale;
	private float borderSize;
	
	public BorderedTriangle(float scale, TriangleStyle triangleStyle){
		this.innerTriangle = new Triangle(triangleStyle.getInnerR(), triangleStyle.getInnerG(), triangleStyle.getInnerB(), triangleStyle.getInnerA());
		this.outerTriangle = new Triangle(triangleStyle.getOuterR(), triangleStyle.getOuterG(), triangleStyle.getOuterB(), triangleStyle.getOuterA());
		this.borderSize = 0.2f;
		this.scaleTemp = 1;
		this.scale = scale;
	}
	
	public void draw(GL10 gl){
		gl.glScalef(scale, scale, 1);
		
		outerTriangle.draw(gl);
		
		gl.glTranslatef(borderSize * scaleTemp / 2, borderSize * scaleTemp / 2, 0);
		gl.glScalef(scaleTemp - borderSize * scaleTemp, scaleTemp - borderSize * scaleTemp, 1);
		innerTriangle.draw(gl);
        
	}
}
