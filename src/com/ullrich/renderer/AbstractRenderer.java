package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.shape.Shape;

import android.content.Context;
import android.opengl.GLSurfaceView;

public abstract class AbstractRenderer implements GLSurfaceView.Renderer{
	protected Context context;
	protected int globalX;
    protected int globalY;
    protected float worldX;
    protected float worldY;
    protected float angle;
    protected boolean renderEnabled = true;
	protected Shape shape;
    
    public AbstractRenderer(Context context, int globalX, int globalY, float angle){
    	this.context = context;
    	this.globalX = globalX;
    	this.globalY = globalY;
    	this.angle = angle;
    }
    
    /**
     * Left Top corner is the (0,0) point
     * @param x coordinate
     * @param y coordinate
     */
    public void setGlobalPosition(int x, int y){
    	this.globalX = x;
    	this.globalY = y;
    }
    
    public int getGlobalPositionX(){
    	return this.globalX;
    }
    
    public int getGlobalPositionY(){
    	return this.globalY;
    }
    
    public void setAngle(float angle){
    	this.angle = angle;
    }

	public boolean isRenderEnabled() {
		return renderEnabled;
	}

	public void setRenderEnabled(boolean renderEnabled) {
		this.renderEnabled = renderEnabled;
	}
	/*
	public void onDrawFrame(GL10 gl){
		
	}
	
	public abstract void onDrawFrame(GL10 gl);
	*/
}
