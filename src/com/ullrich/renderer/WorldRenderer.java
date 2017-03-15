package com.ullrich.renderer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ullrich.config.TextboxStyle;
import com.ullrich.helper.Camera2D;
import com.ullrich.helper.FPSCounter;
import com.ullrich.helper.Vector2D;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class WorldRenderer implements GLSurfaceView.Renderer {

	private Context context;
	/*
	private ArrayList<AbstractRenderer> allRenderer;
	private ArrayList<AbstractRenderer> bufferAdd;
	private ArrayList<AbstractRenderer> bufferRemove;
	*/
	
	private CopyOnWriteArrayList<AbstractRenderer> allRenderer;
	private CopyOnWriteArrayList<AbstractRenderer> bufferAdd;
	private CopyOnWriteArrayList<AbstractRenderer> bufferRemove;
	
	private Camera2D camera;
	private FPSCounter counter;
	private int numberOfPOI; //Just for logging purpose
	private TextboxRenderer fpsRender;
	private int fpsRendererX;
	private int fpsRendererY;
	private float worldWidth;
	private float worldHeight;
	private boolean showFPS;
	private volatile boolean lockA = false;
	private volatile boolean lockR = false;
	private volatile boolean lockF = false;

	// Test
	private int width;
	private int height;
	private GL10 gl;
	private EGLConfig config;

	public void initRenderer(AbstractRenderer renderer) {
		if (gl != null && width != 0 && height != 0 && config != null) {
			renderer.onSurfaceChanged(gl, width, height);
			renderer.onSurfaceCreated(gl, config);
		}
	}

	public WorldRenderer(Context context, float worldWidth) {
		this.context = context;
		this.worldWidth = worldWidth;
		this.allRenderer = new CopyOnWriteArrayList<AbstractRenderer>();
		this.bufferAdd = new CopyOnWriteArrayList<AbstractRenderer>();
		this.bufferRemove = new CopyOnWriteArrayList<AbstractRenderer>();
		this.showFPS = true;
		
		this.counter = new FPSCounter(this, context);
	}

	public void setFPSCounter(String fps) {
		if (showFPS) {
			if(fpsRender != null){
				allRenderer.remove(fpsRender);
			}
			this.fpsRender = new TextboxRenderer(context, "FPS: " + fps, fpsRendererX, fpsRendererY, 0f, TextboxStyle.STANDARD_WHITE);			
			initRenderer(fpsRender);
			allRenderer.add(fpsRender);
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		this.gl = gl;
		this.config = config;

		lockF = true;
		for (GLSurfaceView.Renderer r : allRenderer) {
			r.onSurfaceCreated(gl, config);
		}
		lockF = false;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.gl = gl;
		this.width = width;
		this.height = height;

		this.worldHeight = height * worldWidth / width;
		camera = Camera2D.getInstance(worldWidth, worldHeight, width, height);

		lockF = true;
		for (GLSurfaceView.Renderer r : allRenderer) {
			r.onSurfaceChanged(gl, width, height);
		}
		lockF = false;
	}

	@Override
	public void onDrawFrame(GL10 gl) {

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.setViewportAndMatrices(gl);
		initBufferAdd();
		flushBufferAdd();
		flushBufferRemove();

		lockF = true;
		for (AbstractRenderer r : allRenderer) {
			if(r.isRenderEnabled()){
				r.onDrawFrame(gl);
			}
			// Log.d("Zeichnen", "Zeichnen");
		}
		lockF = false;

		counter.logFrame(); // Must always be the last command
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

	public void addRenderer(AbstractRenderer renderer) {
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
				allRenderer.add(b);
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
						allRenderer.remove(b);
					}
					bufferRemove.clear();
				}
			}
		}
	}

	public void removeRenderer(AbstractRenderer renderer) {
		lockR = true;
		if (!bufferRemove.contains(renderer)) {
			bufferRemove.add(renderer);
		}
		lockR = false;
	}

	public int sizeAllRenderer() {
		return allRenderer.size();
	}

	public GLSurfaceView.Renderer getRendererAt(int i) {
		return allRenderer.get(i);
	}

	public Camera2D getCamera() {
		return camera;
	}
	
	public void setFPSCounterPosition(int x, int y){
		fpsRendererX = x;
		fpsRendererY = y;
	}
	
	public void setNumberOfPOI(int number){
		this.numberOfPOI = number;
	}

	public int getNumberOfPOI(){
		return numberOfPOI;
	}
}
