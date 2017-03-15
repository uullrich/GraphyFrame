package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.CircleStyle;
import com.ullrich.config.TextboxStyle;
import com.ullrich.helper.Camera2D;
import com.ullrich.helper.Vector2D;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;

public class POIRenderer extends AbstractRenderer{
	private TextboxRenderer textboxRenderer;
	private BorderedCircleRenderer borderedCircleRenderer;
    private GL10 gl;
    private EGLConfig config;
    private int width = 0;
    private int height = 0;
	
	public POIRenderer(Context context, String text, int globalX, int globalY, float angle){
		super(context, globalX, globalY, angle);
		this.borderedCircleRenderer = new BorderedCircleRenderer(context, globalX, globalY, angle, 30, CircleStyle.POI);
		this.textboxRenderer = new TextboxRenderer(context, text, globalX, globalY, 0, TextboxStyle.STANDARD_WHITE);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			this.gl = gl;
			this.config = config;
			
			this.textboxRenderer.onSurfaceCreated(gl, config);
			this.borderedCircleRenderer.onSurfaceCreated(gl, config);
			//Log.d("OnSurfaceCreated", "OnSurfaceCreated");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {		
			this.gl = gl;
			this.width = width;
			this.height = height;
			
			Vector2D textboxCoordinates = new Vector2D();
			float radiusC = borderedCircleRenderer.getRadiusPixel();
			textboxCoordinates.x = globalX - ((textboxRenderer.getGlobalWidthBox() / 2));
			textboxCoordinates.y = globalY + textboxRenderer.getGlobalHeightBox() + radiusC;
			
			
			this.textboxRenderer.setGlobalPosition((int)textboxCoordinates.x, (int)textboxCoordinates.y);
			this.textboxRenderer.onSurfaceChanged(gl, width, height);
			this.borderedCircleRenderer.onSurfaceChanged(gl, width, height);
			
			
			Camera2D camera = Camera2D.getInstance(width, height);
			Vector2D testVector = new Vector2D(globalX, globalY);
	    	camera.pixelToWorld(testVector);
	    	this.worldX = testVector.x;
	    	this.worldY = testVector.y;	    	
	    	//Log.d("onSurfaceChanged", "onSurfaceChanged");
	}

	@Override
	public void onDrawFrame(GL10 gl) {
	    	gl.glPushMatrix();
	    	gl.glTranslatef(worldX, worldY, 0f);
	    	gl.glRotatef(angle, 0, 0, 1); //Demo
	    	//gl.glRotatef(angle, 0, 0, 1);
	    	gl.glTranslatef(-worldX, -worldY, 0f);
	    	
	    	
			this.textboxRenderer.onDrawFrame(gl);
			this.borderedCircleRenderer.onDrawFrame(gl); 
			
	        gl.glPopMatrix();  
	        //angle = angle + 0.5f; //Demo
	}

	@Override
    public void setGlobalPosition(int x, int y){
		this.globalX = x;
		this.globalY = y;
		this.borderedCircleRenderer.setGlobalPosition(x, y);
		this.textboxRenderer.setGlobalPosition(x, y);
		if(gl != null && width != 0 && height != 0 && config != null){
			onSurfaceChanged(gl, width, height);
		}
    }
	
	
}
