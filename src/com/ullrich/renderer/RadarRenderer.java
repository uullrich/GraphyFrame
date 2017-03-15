package com.ullrich.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.CircleStyle;
import com.ullrich.config.TriangleStyle;
import com.ullrich.helper.Camera2D;
import com.ullrich.helper.Vector2D;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class RadarRenderer extends AbstractRenderer {
	//Renderer
	private AbstractRenderer radar;
	private AbstractRenderer triangle;
	private AbstractRenderer radarViewport;
	private int viewportAngle;
	protected int radarRadius;	
	protected int radarWidth;
	protected int radarHeight;
	
	//RadarPoint Thread safe
	private ArrayList<AbstractRenderer> radarPoints;
	private ArrayList<AbstractRenderer> bufferAdd;
	private ArrayList<AbstractRenderer> bufferRemove;
	
	private volatile boolean lockA = false;
	private volatile boolean lockR = false;
	private volatile boolean lockF = false;
	
	//OpenGL
    private GL10 gl;
    private EGLConfig config;
    private int width = 0;
    private int height = 0;
    
	public void initRenderer(AbstractRenderer renderer) {
		if (gl != null && width != 0 && height != 0 && config != null) {
			renderer.onSurfaceChanged(gl, width, height);
			renderer.onSurfaceCreated(gl, config);
		}
	}
    
	public RadarRenderer(Context context, int radius, int viewportAngle, int globalX, int globalY, float angle) {
		super(context, globalX, globalY, angle);
		this.radarRadius = radius;
		this.viewportAngle = viewportAngle;
		this.radarWidth = radius * 2;
		this.radarHeight = radius * 2;
		radar = new BorderedCircleRenderer(context, globalX, globalY, 0, radius, CircleStyle.RADAR);
		triangle = new BorderedTriangleRenderer(context, globalX, globalY-radius, 0, 0.1f, TriangleStyle.NORTH);
	    radarViewport = new BorderedSectorCircleRenderer(context, globalX, globalY, 90f, radius, viewportAngle, CircleStyle.RADAR_VIEW_PORT);
		
		radarPoints = new ArrayList<AbstractRenderer>();
		bufferAdd = new ArrayList<AbstractRenderer>();
		bufferRemove = new ArrayList<AbstractRenderer>();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		this.gl = gl;
		this.config = config;
		
		lockF = true;
		for (AbstractRenderer r : radarPoints) {
			r.onSurfaceCreated(gl, config);
		}
		lockF = false;
		
		this.radar.onSurfaceCreated(gl, config);
		this.triangle.onSurfaceCreated(gl, config);
		this.radarViewport.onSurfaceCreated(gl, config);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.gl = gl;
		this.width = width;
		this.height = height;
		
		Vector2D textboxCoordinates = new Vector2D();
		textboxCoordinates.x = globalX;
		textboxCoordinates.y = globalY - this.radarRadius;
		
		this.triangle.setGlobalPosition((int)textboxCoordinates.x, (int)textboxCoordinates.y);
		this.triangle.onSurfaceChanged(gl, width, height);
		this.radar.onSurfaceChanged(gl, width, height);
		
		this.radarViewport.onSurfaceChanged(gl, width, height);
		
		lockF = true;
		for (AbstractRenderer r : radarPoints) {
			r.onSurfaceChanged(gl, width, height);
		}
		lockF = false;
		
		Camera2D camera = Camera2D.getInstance(width, height);
		Vector2D testVector = new Vector2D(globalX, globalY);
    	camera.pixelToWorld(testVector);
    	this.worldX = testVector.x;
    	this.worldY = testVector.y;	    	
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		/*
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPushMatrix(); 
		gl.glLoadIdentity();
		
    	gl.glTranslatef(worldX, worldY, 0f);
    	gl.glRotatef(angle, 0, 0, 1);
    	gl.glTranslatef(-worldX, -worldY, 0f);
		
		
		//gl.glTranslatef(worldX, worldY, 0f);
	
		this.radar.onDrawFrame(gl);
		this.triangle.onDrawFrame(gl); 
        gl.glPopMatrix();
        */
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		gl.glPushMatrix(); 
		gl.glLoadIdentity();		
		this.radar.onDrawFrame(gl);
		gl.glPopMatrix();
		
		
		gl.glPushMatrix(); 
		gl.glLoadIdentity();
		
    	gl.glTranslatef(worldX, worldY, 0f);
    	gl.glRotatef(angle, 0, 0, 1);
    	gl.glTranslatef(-worldX, -worldY, 0f);    	
    	
    	this.triangle.onDrawFrame(gl);
    	
//		gl.glPopMatrix();
		
		
	//	gl.glPushMatrix(); 
//		gl.glLoadIdentity();		
		
    	this.radarViewport.onDrawFrame(gl);
    	
		initBufferAdd();
		flushBufferAdd();
		flushBufferRemove();
		
		lockF = true;
		for(AbstractRenderer r: radarPoints){
			gl.glPushMatrix(); 
			gl.glLoadIdentity();	
			
	    	gl.glTranslatef(worldX, worldY, 0f);
	    	gl.glRotatef(angle, 0, 0, 1);
	    	gl.glTranslatef(-worldX, -worldY, 0f);   
	    	
			r.onDrawFrame(gl);
			gl.glPopMatrix();
		}
		lockF = false;
		
		gl.glPopMatrix();
	}

	private void initBufferAdd() {
		lockA = true;
		if (!bufferAdd.isEmpty()) {
			for (AbstractRenderer r : bufferAdd) {
				initRenderer(r);
			}
		}
		lockA = false;
	}
	
	@Override
    public void setGlobalPosition(int x, int y){
		this.globalX = x;
		this.globalY = y;
		this.radar.setGlobalPosition(x, y);
		this.triangle.setGlobalPosition(x, y - radarRadius);
		invalidate();
    }
	
	public void invalidate(){
		if(gl != null && width != 0 && height != 0 && config != null){
			onSurfaceChanged(gl, width, height);
		}
	}
	
	
	private void addRenderer(AbstractRenderer renderer) {
		if (!lockA) {
			if (!bufferAdd.contains(renderer)) {
				bufferAdd.add(renderer);
			}
		}

	}
	
	private void flushBufferAdd() {
		lockA = true;
		if (!bufferAdd.isEmpty()) {
			for (AbstractRenderer b : bufferAdd) {
				radarPoints.add(b);
			}
			bufferAdd.clear();
		}
		lockA = false;
	}
	
	private void flushBufferRemove() {
		if (!lockR) {
			if (!bufferRemove.isEmpty()) {
				if (!lockF) {
					for (AbstractRenderer b : bufferRemove) {
						radarPoints.remove(b);
					}
					bufferRemove.clear();
				}
			}
		}
	}
	
	private void removeRenderer(AbstractRenderer renderer) {
		lockR = true;
		if (!bufferRemove.contains(renderer)) {
			bufferRemove.add(renderer);
		}
		lockR = false;
	}
	
	public void setRadarPoint(int x, int y, int radiusPixel){
		addRenderer(new BorderedCircleRenderer(context, x, y, 0, radiusPixel, CircleStyle.POI_RADAR));
	}
	
	public void removeAllRadarPoints(){
		lockF = true;
		for(AbstractRenderer r: radarPoints){
			removeRenderer(r);
		}
		lockF = false;
	}
	
	public void setViewportAngle(float angle){
		this.radarViewport.setAngle(angle);
	}
}
