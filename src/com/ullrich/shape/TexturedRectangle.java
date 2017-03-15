package com.ullrich.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

import com.ullrich.helper.Texture;
import com.ullrich.helper.Vertices;

import android.content.Context;
import android.graphics.Bitmap;

public class TexturedRectangle implements Shape{
	final int VERTEX_SIZE = (2 + 2) * 4;
	private Vertices vertices;
	public static final float PICTURE_WIDTH = 4.0625f;
	public static final float PICTURE_HEIGHT = 0.40625f;
	private float textureWidth;
	private float textureHeight;
	private Texture texture;

	public TexturedRectangle(float width, float height){
		this.textureWidth = width;
		this.textureHeight = height;
		
		vertices = new Vertices(4, 6, false, true);
		float [] rectangle = new float[] {  
        		0.0f, 0.0f, 0.0f, 1.0f,
        		textureWidth, 0.0f, 1.0f, 1.0f,
        		textureWidth, textureHeight, 1.0f, 0.0f,
                0.0f, textureHeight, 0.0f, 0.0f
        };

		vertices.setVertices(rectangle, 0, rectangle.length);
		vertices.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0 ,6);
	}
	
	public TexturedRectangle() {
		this.textureHeight = 1;
		this.textureWidth = 1;
		
		vertices = new Vertices(4, 6, false, true);
		float [] rectangle = new float[] {  
        		0.0f, 0.0f, 0.0f, 1.0f,
        		textureWidth, 0.0f, 1.0f, 1.0f,
        		textureWidth, textureHeight, 1.0f, 0.0f,
                0.0f, textureHeight, 0.0f, 0.0f
        };

		vertices.setVertices(rectangle, 0, rectangle.length);
		vertices.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0 ,6);

	}

	public void draw(GL10 gl) {
		//Activating transparent RGBA
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texture.bind(gl);
		
		vertices.bind(gl);
		vertices.draw(gl, GL10.GL_TRIANGLES, 0, 6);
		vertices.unbind(gl);
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	public void createTexture(GL10 gl, Context context, String name) {
		texture = new Texture(gl, context, name);
		//Activating transparent RGBA
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texture.bind(gl);
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	public void createTextureFromBitmap(GL10 gl, Context context, Bitmap bitmap){
		texture = new Texture(gl, context, bitmap);
		
		//Activating transparent RGBA
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texture.bind(gl);
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
}
