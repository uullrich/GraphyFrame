package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.util.Log;

import com.ullrich.config.CircleStyle;
import com.ullrich.helper.Camera2D;
import com.ullrich.helper.Vector2D;
import com.ullrich.shape.BorderedCircle;

public class BorderedCircleRenderer extends AbstractRenderer{
	
    private int radiusPixel;
    private float radiusWorld;
    
    public BorderedCircleRenderer(Context context, int globalX, int globalY, float angle, int radiusPixel, CircleStyle circleStyle){
    	super(context, globalX, globalY, angle);
    	shape = new BorderedCircle(circleStyle.getInnerSize(), circleStyle.getOuterSize(), circleStyle);
    	this.radiusPixel = radiusPixel;
    }
    
	public void onDrawFrame(GL10 gl)                                         
    {   
		//gl.glMatrixMode(GL10.GL_MODELVIEW);
		//gl.glPushMatrix(); 
		//gl.glLoadIdentity();
		gl.glTranslatef(worldX, worldY, 0f);
		gl.glScalef(radiusWorld, radiusWorld, 1);
	
        //circle.draw(gl);
		shape.draw(gl);
		
        //gl.glPopMatrix();
     
    }

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0,0,0,0);                                
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
    	Camera2D camera = Camera2D.getInstance(width, height);
    	Vector2D testVector = new Vector2D(globalX, globalY);
    	camera.pixelToWorld(testVector);
    	worldX = testVector.x;
    	worldY = testVector.y;
    	
    	
    	radiusWorld = camera.PixelRadiusToWorldRadius(radiusPixel);
    	//Log.d("BorderedCircleCoordinate", "X: " + worldX + " Y: " + worldY);	    			
	}

	public int getRadiusPixel() {
		return radiusPixel;
	}

	public float getRadiusWorld() {
		return radiusWorld;
	}
}
