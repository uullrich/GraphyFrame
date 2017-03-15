package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.helper.Vertices;

import android.content.Context;
import android.util.Log;

public class CircleRenderer extends AbstractRenderer{
	int VERTICES=180; // more than needed  // changed variable name 
	Vertices vertices;
	float coords[] = new float[VERTICES * 2];
	short shorts[] = new short[360];
	
    public CircleRenderer(Context context, int globalX, int globalY, float angle){
    	super(context, globalX, globalY, angle);
    	
/*
    	vertices = new Vertices(coords.length, 0, false, false);

    	float theta = 0;

    	for (int i = 0; i < VERTICES * 2; i += 2) {
    	  coords[i + 0] = 1 + (float) Math.cos(theta);
    	  coords[i + 1] = 1 + (float) Math.sin(theta);
    	  theta += Math.PI / 90;
    	}
    	
    	//for (short i = 0; i < shorts.length; i++) {
			//shorts[i] = i;
		//}
    	
    	Log.d("Length", "" + coords.length);
    	vertices.setVertices(coords, 0, coords.length);
    //	vertices.setIndices(shorts, 0, 360);
    	*/
    	
		float textureHeight = 1;
		float textureWidth = 1;
		
		vertices = new Vertices(4, 6, true, false);
		float [] rectangle = new float[] {  
        		0.0f, 0.0f, 0, 1, 1, 0,
        		textureWidth, 0.0f, 0, 1, 1, 0,
        		textureWidth, textureHeight, 0, 1, 1, 0,
                0.0f, textureHeight, 0, 1, 1, 0
        };

		vertices.setVertices(rectangle, 0, rectangle.length);
		vertices.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0 ,6);
    }
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		vertices.bind(gl);
		vertices.draw(gl, GL10.GL_TRIANGLES, 0, 6);
		vertices.unbind(gl);
	}

}
