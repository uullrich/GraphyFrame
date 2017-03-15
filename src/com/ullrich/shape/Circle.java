package com.ullrich.shape;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.ullrich.helper.Vertices;

public class Circle implements Shape{

	private Vertices vertices;
	private int numberOfVertices = 360;
	private int numberOfIndices = (numberOfVertices - 1) * 3;
	private float radius = 0.1f;
	private int offset = 0;
	
	public Circle(float radius, float r, float g, float b, float a){
		this.radius = radius;
		
		vertices = new Vertices(numberOfVertices, numberOfIndices, true, false);
		float [] rectangle = new float[numberOfVertices * 6];
		short [] indices = new short[numberOfIndices];
		
		//Center Point
		//Coordinates
		rectangle[0] = 0.0f;
		rectangle[1] = 0.0f;
		//Colors
		rectangle[2] = r;
		rectangle[3] = g;
		rectangle[4] = b;
		rectangle[5] = a;
		
		double angle = 0;
		for (int i = 6; i < rectangle.length; i+=6) {
			rectangle[i] = (float) Math.sin(Math.toRadians(angle)) * this.radius;
			rectangle[i + 1] = (float) Math.cos(Math.toRadians(angle)) * this.radius;
			//Colors
			rectangle[i + 2] = r;
			rectangle[i + 3] = g;
			rectangle[i + 4] = b;
			rectangle[i + 5] = a;	
			angle++;
		}		
        
		
		short count = 0;
		for (int i = indices.length - 1; i > 0; i-=3) {
			indices[i] = 0;
		    indices[i - 1] = (short) (count + 1);
			indices[i - 2] = (short) (count + 2);
			count++;
		}
		indices[0] = 1;
        

		vertices.setVertices(rectangle, 0, rectangle.length);
		vertices.setIndices(indices, offset, numberOfIndices);
		
		//Debug
		/*
		for (int i = 6; i < rectangle.length; i+=6) {
			Log.d("Coord", "X: " + rectangle[i] + " Y: " + rectangle[i+1]);
		}*/
		
		/*
		for (int i = 0; i < indices.length; i+=3) {
			Log.d("Indices","" + indices[i] + " " + indices[i + 1] + " " + indices[i + 2]);			
		}
		*/
	}
	
	public void draw(GL10 gl) {
		//Activating transparent RGBA
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		vertices.bind(gl);
		vertices.draw(gl, GL10.GL_TRIANGLES, 0, numberOfIndices);
		vertices.unbind(gl);
	}
	
	
	
	
	
	/*
	private Vertices vertices;
	
	public Circle(){
		float textureWidth = 1;
		float textureHeight = 1;
		int numberOfVertices = 6;
		int numberOfIndices = 12;
		int offset = 0;
		
		vertices = new Vertices(numberOfVertices, numberOfIndices, true, false);
		float [] rectangle = new float[] {  
        		0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
        		textureWidth, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
        		textureWidth, textureHeight, 1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, textureHeight, 1.0f, 1.0f, 1.0f, 1.0f,
                0.5f, 1.5f, 1.0f, 1.0f, 1.0f, 1.0f,
                0.5f, -0.5f, 1.0f, 1.0f, 1.0f, 1.0f
        };

		vertices.setVertices(rectangle, 0, rectangle.length);
		vertices.setIndices(new short[] { 0, 1, 2, 
										  2, 3, 0, 
										  2, 4, 3,
										  0, 5, 1
										  }, offset, numberOfIndices);

	}
	
	public void draw(GL10 gl) {
		//Activating transparent RGBA
		//gl.glEnable(GL10.GL_BLEND);
		//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		vertices.bind(gl);
		vertices.draw(gl, GL10.GL_TRIANGLES, 0, 12);
		vertices.unbind(gl);
	}
	*/
	
}
