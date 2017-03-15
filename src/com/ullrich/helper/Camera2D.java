package com.ullrich.helper;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Camera2D {
    public final Vector2D position;
    public float zoom;
    private float frustumWidth;
    private float frustumHeight;
    private final int viewportWidth;
    private final int viewportHeight;
    
    private static Camera2D instance = null;
    
    private Camera2D(float frustumWidth, float frustumHeight, int viewportWidth, int viewportHeight) {
        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
        this.position = new Vector2D(frustumWidth / 2, frustumHeight / 2);
        this.zoom = 1f;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    public void setViewportAndMatrices(GL10 gl) {
        gl.glViewport(0, 0, viewportWidth, viewportHeight);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(position.x - frustumWidth * zoom / 2, 
                    position.x + frustumWidth * zoom/ 2, 
                    position.y - frustumHeight * zoom / 2, 
                    position.y + frustumHeight * zoom / 2, 
                    1, -1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /*
     * Moves the Camera about x and y Pixels.
     * */
    public void moveCamera(int movementX, int movementY){
    	Vector2D worldMovement = new Vector2D(globalWidthToWorldWidth(movementX), globalHeightToWorldHeight(movementY));    	
    	position.add(worldMovement);
    }
    
    public void pixelToWorld(Vector2D touch) {
    	Vector2D cameraCenterPoint = new Vector2D(frustumWidth / 2, frustumHeight / 2);
        touch.x = (touch.x / (float) viewportWidth) * frustumWidth * zoom;
        touch.y = (1 - touch.y / (float) viewportHeight) * frustumHeight * zoom;
        touch.add(cameraCenterPoint).sub(frustumWidth * zoom / 2, frustumHeight * zoom / 2);
    }
    
    public void touchToWorld(Vector2D touch) {
    	touch.x = (touch.x / (float) viewportWidth) * frustumWidth * zoom;
        touch.y = (1 - touch.y / (float) viewportHeight) * frustumHeight * zoom;
        touch.add(position).sub(frustumWidth * zoom / 2, frustumHeight * zoom / 2);
    }
    
    public static Camera2D getInstance(float frustumWidth, float frustumHeight, int viewportWidth, int viewportHeight) {
        if (instance == null) {
            instance = new Camera2D(frustumWidth, frustumHeight, viewportWidth, viewportHeight);
        }
        return instance;
    }
    
    public static Camera2D getInstance(int viewportWidth, int viewportHeight) {
        if (instance != null) {
        	return instance;   
        }
        return null;
    }

	public float getFrustumWidth() {
		return frustumWidth;
	}

	public float getFrustumHeight() {
		return frustumHeight;
	}
    
    public float PixelRadiusToWorldRadius(int radiusPixel){
    	return (radiusPixel * frustumWidth * zoom)/ viewportWidth;
    }
    
    public int worldWidthToGlobalWidth(float worldWidth){
    	return (int) (worldWidth * viewportWidth / (frustumWidth * zoom));
    }
    
    public int worldHeightToGlobalHeight(float worldHeight){
    	return (int)(worldHeight * viewportHeight / (frustumHeight * zoom));
    }    
    
    public float globalHeightToWorldHeight(int globalHeight){
    	return (float)(globalHeight * frustumHeight * zoom / viewportHeight);
    }
    
    public float globalWidthToWorldWidth(int globalWidth){
    	return (float)(globalWidth * frustumWidth * zoom / viewportWidth);
    }
}
