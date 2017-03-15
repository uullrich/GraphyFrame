package com.ullrich.cameratest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ullrich.config.TextboxStyle;
import com.ullrich.graphyframe.R;
import com.ullrich.graphyframe.R.id;
import com.ullrich.graphyframe.R.layout;
import com.ullrich.graphyframe.R.menu;
import com.ullrich.helper.Texture;
import com.ullrich.helper.Vector2D;
import com.ullrich.renderer.BorderedCircleRenderer;
import com.ullrich.renderer.CircleRenderer;
import com.ullrich.renderer.POIRenderer;
import com.ullrich.renderer.SquareRenderer;
import com.ullrich.renderer.TextboxRenderer;
import com.ullrich.renderer.WorldRenderer;
import com.ullrich.shape.TextBox;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.os.Build;

public class CameraActivity extends Activity implements SensorEventListener {
	private FrameLayout mainLayout;
	private CameraSurfaceView camera;
	private GLSurfaceView view;
	private WorldRenderer worldRenderer;
	private SensorManager mgr;
	private Sensor gyro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		gyro = mgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		mainLayout = new FrameLayout(this);
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		camera = new CameraSurfaceView(this);

		view = new GLSurfaceView(this);

		// Macht den GLSurface View Transpartent. Aber warum genau muss noch
		// geschaut werden.
		// Muss auf jeden Fall vor dem Renderer gesetzt werden.
		view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

		// Renderer
		
		worldRenderer = new WorldRenderer(this.getApplicationContext(), 2f);

		//worldRenderer.addRenderer(new BorderedCircleRenderer(this.getApplicationContext(), 150, 250, 0f, 0.1f));
		//worldRenderer.addRenderer(new TextboxRenderer(this.getApplicationContext(), "FPS: ", 0, 100, 0f, TextboxStyle.STANDARD_WHITE));
		//worldRenderer.addRenderer(new TextboxRenderer(this.getApplicationContext(), "FPS: ", 100, 100, 0f, TextboxStyle.STANDARD_WHITE));
		
		//worldRenderer.addRenderer(new BorderedCircleRenderer(this.getApplicationContext(), 0, 500, 0f, 0.5f));
		//worldRenderer.addRenderer(new BorderedCircleRenderer(this.getApplicationContext(), 200, 0, 0f, 0.5f));
		
		/*//Demo Renderer
		for (int i = 0; i < 5; i++) {
			worldRenderer.addRenderer(new BorderedCircleRenderer(this.getApplicationContext(), 50, (170 + i*200), 0f, 20));
			worldRenderer.addRenderer(new BorderedCircleRenderer(this.getApplicationContext(), 350, (170 + i*220), 0f, 30));
			worldRenderer.addRenderer(new TextboxRenderer(this.getApplicationContext(), "Tolle Textbox!", 50, (200 + i*200), 0, TextboxStyle.STANDARD_YELLOW));
			worldRenderer.addRenderer(new TextboxRenderer(this.getApplicationContext(), "Hallo Welt!", 350, (200 + i*250), 0, TextboxStyle.STANDARD_WHITE));
		}
		*/
		
		//Demo POIs
		/*
		worldRenderer.addRenderer(new POIRenderer(this.getApplicationContext(), "Es wird", 200, 200, 0));
		worldRenderer.addRenderer(new POIRenderer(this.getApplicationContext(), "Zeit", 400, 400, 0));
		worldRenderer.addRenderer(new POIRenderer(this.getApplicationContext(), "das sich", 200, 600, 0));
		worldRenderer.addRenderer(new POIRenderer(this.getApplicationContext(), "was dreht!", 400, 800, 0));
		worldRenderer.addRenderer(new POIRenderer(this.getApplicationContext(), "Rand", 720, 800, 0));
		*/

/*	
		for (int i = 0; i < 70; i++) {
			POIRenderer poi = new POIRenderer(this.getApplicationContext(), "POI Nr: " + i, 200, i * 200, 0);
			poi.setAngle(90f);
			worldRenderer.addRenderer(poi);
		}
*/
		
		
		view.setRenderer(worldRenderer);
		//view.setRenderer(new SquareRenderer(true));
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				synchronized (this) {
					float x1 = 0, x2 = 0, y1 = 0, y2 = 0, dx = 0, dy = 0;
					switch (event.getAction()) {
					case (MotionEvent.ACTION_DOWN):
						x1 = event.getX();
						y1 = event.getY();
						Vector2D touch = new Vector2D(x1, y1);
						worldRenderer.getCamera().touchToWorld(touch);
						Log.d("TOUCH", "X: " + x1 + " Y: " + y1 + " -> WorldX: " + touch.x + " WorldY: " + touch.y);
						if(y1 < 550){
							worldRenderer.getCamera().moveCamera(0, -40);
						}else{
							worldRenderer.getCamera().moveCamera(0, 40);
						}
						//worldRenderer.addRenderer(new POIRenderer(getApplicationContext(), "X: " + x1 + " Y: " + y1, (int) x1,(int) y1, 0));						
						break;
					case (MotionEvent.ACTION_UP):
						x2 = event.getX();
						y2 = event.getY();
						dx = x2 - x1;
						dy = y2 - y1;
						// tsr.touchX = (int) dx;
						// tsr.touchY = (int) dy;
						break;

					}
					return true;
				}
			}
		});

		// Transparent machen, warum auch noch schauen.
		view.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		// view.getHolder().setFormat(PixelFormat.RGBA_8888);
		view.setZOrderOnTop(true);

		mainLayout.addView(camera);
		mainLayout.addView(view);

		setContentView(mainLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_camera,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//String msg = "0: " + event.values[0] + "\n" + "1: " + event.values[1]
		//		+ "\n" + "2: " + event.values[2] + "\n";
		//System.out.println("Gyroskop: " + msg);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		mgr.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	protected void onPause() {
		mgr.unregisterListener(this, gyro);
		super.onPause();
	}

}
