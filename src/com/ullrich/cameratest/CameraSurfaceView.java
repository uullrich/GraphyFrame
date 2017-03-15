package com.ullrich.cameratest;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurfaceView extends SurfaceView implements
SurfaceHolder.Callback {
	private Context context;
	private Camera camera;
	private SurfaceHolder holder;
	
	public CameraSurfaceView(Context context) {
		super(context);
		this.context = context;
		holder = getHolder();
		holder.addCallback(this);
	}
	
	@Override
    public void surfaceDestroyed(SurfaceHolder holder) {
          camera.stopPreview();
          camera.setPreviewCallback(null);
          camera.release();
          camera = null;
    }
	
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
          try {
        	  camera.getParameters().setRecordingHint(true);
        	  camera.setPreviewDisplay(holder);
              camera.setDisplayOrientation(90);
              camera.startPreview();
          } catch (IOException exception) {  
                camera.release();  
                camera = null;  
          }
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        camera.startPreview();
	} 
	

}
