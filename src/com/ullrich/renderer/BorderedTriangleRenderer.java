package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.TriangleStyle;
import com.ullrich.helper.Camera2D;
import com.ullrich.helper.Vector2D;
import com.ullrich.shape.BorderedTriangle;
import com.ullrich.shape.Triangle;

import android.content.Context;

public class BorderedTriangleRenderer extends AbstractRenderer {
	private float scale;
	
	public BorderedTriangleRenderer(Context context, int globalX, int globalY, float angle, float scale, TriangleStyle triangleStyle) {
		super(context, globalX, globalY, angle);
		shape = new BorderedTriangle(scale, triangleStyle);
		this.scale = scale;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0,0,0,0);  
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
    	Camera2D camera = Camera2D.getInstance(width, height);
    	int triangleGlobalWidth = camera.worldWidthToGlobalWidth(1 * scale);
    	
    	//The origin point is not the left vertice. The origin is at (0.5, 0) in world coordinates
    	Vector2D testVector = new Vector2D(globalX - (triangleGlobalWidth / 2), globalY);
    	camera.pixelToWorld(testVector);
    	worldX = testVector.x;
    	worldY = testVector.y;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
	//	gl.glMatrixMode(GL10.GL_MODELVIEW);
	//	gl.glPushMatrix(); 
	//	gl.glLoadIdentity();
		
    	gl.glTranslatef(worldX, worldY, 0f);
    	gl.glRotatef(angle, 0, 0, 1);
    	gl.glTranslatef(-worldX, -worldY, 0f);
		
		
		gl.glTranslatef(worldX, worldY, 0f);
	
		//borderedTriangle.draw(gl);
		shape.draw(gl);
    //    gl.glPopMatrix();
	}

}
