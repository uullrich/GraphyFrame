package com.ullrich.shape;

import javax.microedition.khronos.opengles.GL10;

import com.ullrich.helper.Vertices;

public class Triangle implements Shape{
	private Vertices vertices;
	private int numberOfVertices;
	private int numberOfIndices;
	private int offset = 0;
	
	public Triangle(float r, float g, float b, float a){
		this.numberOfIndices = 3;
		this.numberOfVertices = 3;
		
		vertices = new Vertices(numberOfVertices, numberOfIndices, true, false);
		float [] triangle = new float[numberOfVertices * 6];
		short [] indices = new short[numberOfIndices];
		
		//Left Vertice
		triangle[0] = 0.0f;
		triangle[1] = 0.0f;
		//Colors
		triangle[2] = r;
		triangle[3] = g;
		triangle[4] = b;
		triangle[5] = a;
		
		//Right Vertice
		triangle[6] = 1.0f;
		triangle[7] = 0.0f;
		//Colors
		triangle[8] = r;
		triangle[9] = g;
		triangle[10] = b;
		triangle[11] = a;
		
		//Middle Vertice
		triangle[12] = 0.5f;
		triangle[13] = 1.0f;
		//Colors
		triangle[14] = r;
		triangle[15] = g;
		triangle[16] = b;
		triangle[17] = a;
		
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;
		
		vertices.setVertices(triangle, 0, triangle.length);
		vertices.setIndices(indices, offset, numberOfIndices);
	}
	
	public void draw(GL10 gl) {
		//Activating transparent RGBA
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		vertices.bind(gl);
		vertices.draw(gl, GL10.GL_TRIANGLES, 0, numberOfIndices);
		vertices.unbind(gl);
	}
}
