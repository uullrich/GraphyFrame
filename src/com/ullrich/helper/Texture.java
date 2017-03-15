package com.ullrich.helper;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Texture {

    String fileName;
    Bitmap bitmap;
    IOHelper fileIO;
    int textureId;
    int minFilter;
    int magFilter;    
    int width;
    int height;
    
    /**
     * 
     * @param gl
     * @param context - Needed to load Assets
     * @param fileName
     */
    public Texture(GL10 gl, Context context, String fileName) {
        this.fileName = fileName;
        this.fileIO = new IOHelper(context);
        load(gl);
    }
    
    public Texture(GL10 gl, Context context, Bitmap bitmap){
    	this.bitmap = bitmap;
    	loadFromBitmap(gl);
    }
    
    private void load(GL10 gl) {
        int[] textureIds = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        
        InputStream in = null;
        try {
            in = fileIO.readAsset(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            setFilters(gl, GL10.GL_LINEAR, GL10.GL_LINEAR);
            //setFilters(gl, GL10.GL_NEAREST, GL10.GL_NEAREST);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
            bitmap.recycle();
        } catch(IOException e) {
            throw new RuntimeException("Couldn't load texture '" + fileName +"'", e);
        } finally {
            if(in != null)
                try { in.close(); } catch (IOException e) { }
        }
    }
    
    private void loadFromBitmap(GL10 gl) {
    	int[] textureIds = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        setFilters(gl, GL10.GL_LINEAR, GL10.GL_LINEAR);            
        //setFilters(gl, GL10.GL_NEAREST, GL10.GL_NEAREST);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        bitmap.recycle();
    }
    
    
    public void reload(GL10 gl) {
        if(bitmap != null){
        	loadFromBitmap(gl);
        }else{
        	load(gl);
        }
        bind(gl);
        setFilters(gl, minFilter, magFilter);        
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }
    
    public void setFilters(GL10 gl, int minFilter, int magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
    }    
    
    public void bind(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
    }
    
    public void dispose(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        int[] textureIds = { textureId };
        gl.glDeleteTextures(1, textureIds, 0);
    }
}
