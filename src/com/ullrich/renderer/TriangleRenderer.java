package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.helper.Camera2D;
import com.ullrich.helper.Vector2D;
import com.ullrich.shape.Triangle;

import android.content.Context;

public class TriangleRenderer extends AbstractRenderer {
	private float scale;
	
	public TriangleRenderer(Context context, int globalX, int globalY, int angle, float scale){
		super(context, globalX, globalY, angle);
		shape = new Triangle(1, 1, 1, 1);
		this.scale = scale;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPushMatrix(); 
		gl.glLoadIdentity();
		
    	gl.glTranslatef(worldX, worldY, 0f);
    	gl.glRotatef(angle, 0, 0, 1);
    	gl.glTranslatef(-worldX, -worldY, 0f);
		
		
		gl.glTranslatef(worldX, worldY, 0f);
		gl.glScalef(scale, scale, 1);
	
        shape.draw(gl);
        gl.glPopMatrix();
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
}
